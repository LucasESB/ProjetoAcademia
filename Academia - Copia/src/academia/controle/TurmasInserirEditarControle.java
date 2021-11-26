package academia.controle;

import academia.dao.TurmasDao;
import academia.entidades.Turmas;
import academia.utilitarios.Decimal;
import academia.utilitarios.Mascaras;
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
import java.util.Date;
import java.util.ResourceBundle;

public class TurmasInserirEditarControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_salvar;

    @FXML
    private TextField tex_descricao;

    @FXML
    private TextField tex_vMensalidade;

    /**
     * Objeto de conexão com a tabela aluno
     */
    private TurmasDao turmasDao = TurmasDao.getInstance();

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static TurmasInserirEditarControle instancia;

    /**
     * Armazena o objeto aluno que será retornado da tela
     */
    private Turmas objetoRetorno;

    private boolean editar;

    private Turmas turmaEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static Turmas showDialogInserir() throws Exception {
        TurmasInserirEditarControle.abrirTela(null, false);
        return instancia.getObjetoRetorno();
    }

    public static void showDialogEditar(Turmas turma) throws Exception {
        TurmasInserirEditarControle.abrirTela(turma, true);
    }

    public Turmas getObjetoRetorno() {
        return objetoRetorno;
    }

    public TurmasInserirEditarControle() throws Exception {
    }

    private void configuracoes() {
        setMascaras();

        if (editar) {
            setDados();
        }

        setEventos();
    }

    private void setMascaras() {
        Mascaras.mascararDinheiro(tex_vMensalidade);
        Mascaras.maxField(tex_descricao, 40);
    }

    private void setDados() {
        tex_descricao.setText(turmaEdit.getDescricao());
        tex_vMensalidade.setText(Decimal.formatar(turmaEdit.getvMensalidade(), "#,##0.00"));
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
        if (isDadosTurmaNaoValido()) {
            return;
        }

        if (editar) {
            editar();
        } else {
            inserir();
        }
    }

    private boolean isDadosTurmaNaoValido() {
        StringBuilder mensagensErro = new StringBuilder();

        if (tex_descricao.getText() == null || tex_descricao.getText().trim().isEmpty()) {
            mensagensErro.append("Descrição é de preenchimento obrigatório.\n");
        }

        if (tex_vMensalidade.getText() == null || tex_vMensalidade.getText().isEmpty()) {
            mensagensErro.append("Valor Mensalidade é de preenchimento obrigatório.\n");
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
        Turmas turma = getDadosTurma();
        boolean atualizado = turmasDao.atualizar(turma);

        if (atualizado) {
            janela.close();
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar atualizar turma");
            alertErro.show();
        }
    }

    private Turmas getDadosTurma() throws ParseException {
        Turmas turma = null;

        if (editar) {
            turma = turmaEdit;
        } else {
            turma = new Turmas();
        }

        turma.setDescricao(tex_descricao.getText().trim());
        turma.setvMensalidade(Decimal.valorFormatadoParseDouble(tex_vMensalidade.getText().trim()));

        return turma;
    }

    private void inserir() throws Exception {
        Turmas turma = getDadosTurma();
        int codigo = turmasDao.inserir(turma);

        if (codigo > 0) {
            turma.setId(codigo);
            objetoRetorno = turma;
            janela.close();
        } else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar inserir a turma");
            alertErro.show();
        }
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Turmas turma, boolean edita) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TurmasInserirEditarControle.class.getResource("/academia/telas/TurmasInserirEditar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Cadastro e atualização das Turmas");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(TurmasInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.turmaEdit = turma;
        instancia.editar = edita;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
