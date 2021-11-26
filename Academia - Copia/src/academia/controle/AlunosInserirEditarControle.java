package academia.controle;

import academia.dao.AlunosDao;
import academia.entidades.Alunos;
import academia.utilitarios.DataHora;
import academia.utilitarios.Mascaras;
import academia.utilitarios.Textos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AlunosInserirEditarControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_salvar;

    @FXML
    private ComboBox<String> cai_sexo;

    @FXML
    private TextField tex_cpf;

    @FXML
    private DatePicker dat_dataNascimento;

    @FXML
    private TextField tex_diaPrePagamento;

    @FXML
    private TextField tex_email;

    @FXML
    private TextField tex_nome;

    @FXML
    private TextArea tex_observacoes;

    @FXML
    private TextField tex_telefone;

    @FXML
    private TextField tex_whatsapp;

    /**
     * Objeto de conexão com a tabela aluno
     */
    private AlunosDao alunosDao = AlunosDao.getInstance();

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static AlunosInserirEditarControle instancia;

    /**
     * Armazena o objeto aluno que será retornado da tela
     */
    private Alunos objetoRetorno;

    private boolean editar;

    private Alunos alunoEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static Alunos showDialogInserir() throws Exception {
        AlunosInserirEditarControle.abrirTela(null, false);
        return instancia.getObjetoRetorno();
    }

    public static void showDialogEditar(Alunos aluno) throws Exception {
        AlunosInserirEditarControle.abrirTela(aluno, true);
    }

    public Alunos getObjetoRetorno() {
        return objetoRetorno;
    }

    public AlunosInserirEditarControle() throws Exception {
    }

    private void configuracoes() {
        setMascaras();
        loadSexo();

        if (editar) {
            setDados();
        }

        setEventos();
    }

    private void setMascaras() {
        Mascaras.mascararCPF(tex_cpf);
        Mascaras.mascararTelefone(tex_telefone);
        Mascaras.mascararTelefone(tex_whatsapp);
        Mascaras.mascararData(dat_dataNascimento);
        Mascaras.maxField(tex_email, 60);
        Mascaras.maxField(tex_nome, 40);
    }

    private void loadSexo() {
        cai_sexo.setItems(FXCollections.observableList(Alunos.getListSexo()));
    }

    private void setDados() {
        tex_nome.setText(alunoEdit.getNome());
        tex_cpf.setText(Textos.formatarCPF(alunoEdit.getCpf()));
        dat_dataNascimento.setValue(LocalDate.parse(DataHora.formatarData(alunoEdit.getDataNascimento(), "yyyy-MM-dd"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        tex_diaPrePagamento.setText(Integer.toString(alunoEdit.getDiaPrePagamento()));
        cai_sexo.getSelectionModel().select(alunoEdit.getSexo().equalsIgnoreCase("F") ? 0 : 1);
        tex_telefone.setText(Textos.formatarTelefone(alunoEdit.getTelefone()));
        tex_whatsapp.setText(Textos.formatarTelefone(alunoEdit.getWhatsapp()));
        tex_email.setText(alunoEdit.getEmail());
        tex_observacoes.setText(alunoEdit.getObservacao() == null ? "" : alunoEdit.getObservacao());
    }

    private void setEventos() {
        bot_salvar.setOnAction(eventHandlerAction);
        bot_cancelar.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_salvar)) {
                    salvar();
                } else if (event.getSource().equals(bot_cancelar)) {
                    janela.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void salvar() throws Exception {
        if (isDadosAlunoNaoValido()) {
            return;
        }

        if (editar) {
            editar();
        } else {
            inserir();
        }
    }

    private boolean isDadosAlunoNaoValido() {
        StringBuilder mensagensErro = new StringBuilder();

        if (tex_nome.getText() == null || tex_nome.getText().trim().isEmpty()) {
            mensagensErro.append("Nome é de preenchimento obrigatório.\n");
        }

        if (tex_cpf.getText() == null || tex_cpf.getText().isEmpty()) {
            mensagensErro.append("CPF é de preenchimento obrigatório.\n");
        }

        if (dat_dataNascimento.getEditor().getText() == null || dat_dataNascimento.getEditor().getText().isEmpty()) {
            mensagensErro.append("Data de Nascimento é de preenchimento obrigatório.\n");
        }

        if (tex_diaPrePagamento.getText() == null || tex_diaPrePagamento.getText().isEmpty()) {
            mensagensErro.append("Dia Pre. Pagamento é de preenchimento obrigatório.\n");
        } else if (Integer.parseInt(tex_diaPrePagamento.getText()) <= 0 || Integer.parseInt(tex_diaPrePagamento.getText()) > 30) {
            mensagensErro.append("O campo Dia Pre. Pagamento está preenchido com um valor invalido, inseria um valor entre 1 e 30 .\n");
        }

        if (tex_telefone.getText() == null || tex_telefone.getText().isEmpty()) {
            mensagensErro.append("Telefone é de preenchimento obrigatório.\n");
        }

        if (cai_sexo.getSelectionModel().getSelectedItem() == null) {
            mensagensErro.append("Sexo é de preenchimento obrigatório.\n");
        }

        if (mensagensErro.length() > 0) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Alerta de Erro");
            alerta.setHeaderText("Campos Inválidos");
            alerta.setContentText(mensagensErro.toString());
            alerta.show();
            return true;
        }

        return false;
    }

    private void editar() throws Exception {
        Alunos aluno = getDadosAluno();
        boolean atualizado = alunosDao.atualizar(aluno);

        if (atualizado) {
            janela.close();
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar atualizar um Aluno");
            alertErro.show();
        }
    }

    private Alunos getDadosAluno() throws ParseException {
        Alunos aluno = null;

        if (editar) {
            aluno = alunoEdit;
        } else {
            aluno = new Alunos();
        }

        aluno.setNome(tex_nome.getText().trim());

        aluno.setCpf(tex_cpf.getText().trim()
                .replaceAll("\\.", "")
                .replace("-", ""));

        aluno.setDataNascimento(DataHora.stringParseDate(dat_dataNascimento.getEditor().getText(), "dd/MM/yyyy"));
        aluno.setDiaPrePagamento(Integer.parseInt(tex_diaPrePagamento.getText()));
        aluno.setDataCadastro(new Date());

        aluno.setTelefone(tex_telefone.getText()
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("-", ""));

        aluno.setWhatsapp(tex_whatsapp.getText()
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("-", ""));

        aluno.setSexo(cai_sexo.getSelectionModel().getSelectedItem());

        aluno.setEmail(tex_email.getText().trim());
        aluno.setObservacao(tex_observacoes.getText().trim());

        return aluno;
    }

    private void inserir() throws Exception {
        Alunos aluno = getDadosAluno();
        int codigo = alunosDao.inserir(aluno);

        if (codigo > 0) {
            aluno.setId(codigo);
            objetoRetorno = aluno;
            janela.close();
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar inserir um Aluno");
            alertErro.show();
        }
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Alunos aluno, boolean edita) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AlunosInserirEditarControle.class.getResource("/academia/telas/AlunosInserirEditar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Cadastro e atualização de Alunos");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(AlunosInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.alunoEdit = aluno;
        instancia.editar = edita;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
