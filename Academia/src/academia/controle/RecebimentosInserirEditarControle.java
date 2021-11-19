package academia.controle;

import academia.bean.FormaPagamentoBean;
import academia.dao.RecebimentosDao;
import academia.dao.TurmasDao;
import academia.entidades.Alunos;
import academia.entidades.Recebimentos;
import academia.utilitarios.DataHora;
import academia.utilitarios.Decimal;
import academia.utilitarios.InstanciaSistema;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class RecebimentosInserirEditarControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_inserirAluno;

    @FXML
    private Button bot_salvar;

    @FXML
    private ComboBox<FormaPagamentoBean> cai_formaPagamento;

    @FXML
    private CheckBox chb_matricula;

    @FXML
    private DatePicker dat_dataPagamento;

    @FXML
    private TextField tex_TotalPagar;

    @FXML
    private TextField tex_aluno;

    @FXML
    private TextField tex_qtdMensalidade;

    @FXML
    private TextField tex_totalPagamento;

    @FXML
    private TextField tex_vDesconto;

    @FXML
    private TextField tex_vPago;

    @FXML
    private TextField tex_vTroco;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static RecebimentosInserirEditarControle instancia;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentosDao = RecebimentosDao.getInstance();

    /**
     * Objeto de conexão com a tabela turmas
     */
    private TurmasDao turmasDao = TurmasDao.getInstance();

    /**
     * Armazena o objeto usuario que será retornado da tela
     */
    private Recebimentos objetoRetorno;

    private boolean editar;

    private Recebimentos recebimentoEdit;

    private Alunos alunoSelecionado;

    double vMensalidade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static Recebimentos showDialogInserir() throws Exception {
        RecebimentosInserirEditarControle.abrirTela(null, false);
        return instancia.getObjetoRetorno();
    }

    public static void showDialogEditar(Recebimentos recebimento) throws Exception {
        RecebimentosInserirEditarControle.abrirTela(recebimento, true);
    }

    public Recebimentos getObjetoRetorno() {
        return objetoRetorno;
    }

    public RecebimentosInserirEditarControle() throws Exception {
    }

    private void configuracoes() throws Exception {
        dat_dataPagamento.setValue(LocalDate.now());
        loadFormasPagamentos();
        setMascaras();

        if (editar) {
            setDados();
        }

        setEventos();
    }

    private void loadFormasPagamentos() {
        cai_formaPagamento.setItems(FXCollections.observableList(FormaPagamentoBean.getListFormasPagamento()));
        cai_formaPagamento.getSelectionModel().select(0);
    }

    private void setMascaras() {
        Mascaras.mascararDinheiro(tex_vPago);
        Mascaras.mascararDinheiro(tex_vDesconto);
    }

    private void setDados() throws Exception {
        tex_qtdMensalidade.setDisable(true);
        tex_aluno.setDisable(true);
        bot_inserirAluno.setDisable(true);

        tex_totalPagamento.setVisible(false);
        tex_vDesconto.setVisible(false);
        tex_TotalPagar.setVisible(false);
        tex_vPago.setVisible(false);
        tex_vTroco.setVisible(false);

        dat_dataPagamento.getEditor().setText(DataHora.formatarData(recebimentoEdit.getDataPagamento(), "dd/MM/yyyy"));
        chb_matricula.setSelected(recebimentoEdit.isMatricula());
        selecionarFormaPagamento(recebimentoEdit.getFormaPagamento());
    }

    /**
     * Metodo responsavel por selecionar a forma de pagamento
     */
    private void selecionarFormaPagamento(FormaPagamentoBean forma) {
        for (FormaPagamentoBean f : cai_formaPagamento.getItems()) {
            if (f.getCodigo().equals(forma.getCodigo())) {
                cai_formaPagamento.getSelectionModel().select(f);
                break;
            }
        }
    }

    private void setEventos() {
        bot_inserirAluno.setOnAction(eventHandlerAction);
        bot_cancelar.setOnAction(eventHandlerAction);
        bot_salvar.setOnAction(eventHandlerAction);
        tex_vDesconto.setOnKeyPressed(eventHandlerKey);
        tex_vPago.setOnKeyPressed(eventHandlerKey);
        tex_qtdMensalidade.setOnKeyPressed(eventHandlerKey);
        tex_aluno.setOnMouseClicked(eventHandlerMouse);
    }

    /**
     * Metodo responsavel pelos eventos do componetes
     */
    private final EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_inserirAluno)) {
                    inserirUmNovoAluno();
                } else if (event.getSource().equals(bot_cancelar)) {
                    janela.close();
                } else if (event.getSource().equals(bot_salvar)) {
                    salvar();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Metodo responsavel pelos eventos do teclado
     */
    private final EventHandler<KeyEvent> eventHandlerKey = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (keyEvent.getSource().equals(tex_vDesconto)) {
                    aplicarDesconto();
                } else if (keyEvent.getSource().equals(tex_vPago)) {
                    calcularTroco();
                } else if (keyEvent.getSource().equals(tex_qtdMensalidade)) {
                    calcularTotal();
                }
            }
        }
    };

    /**
     * Metodo responsaveç pelos eventos do mouse/
     */
    private final EventHandler<MouseEvent> eventHandlerMouse = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                if (mouseEvent.getSource().equals(tex_aluno) && mouseEvent.getClickCount() == 2) {
                    buscarAluno();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void inserirUmNovoAluno() throws Exception {
        alunoSelecionado = AlunosInserirEditarControle.showDialogInserir();

        if (alunoSelecionado == null) {
            tex_aluno.clear();
            return;
        }

        tex_aluno.setText(alunoSelecionado.getNome());
        getMensalidade();
    }

    private void buscarAluno() throws Exception {
        alunoSelecionado = AlunoBuscaControle.showDialogBusca();

        if (alunoSelecionado == null) {
            tex_aluno.clear();
            tex_totalPagamento.clear();
            tex_TotalPagar.clear();
            vMensalidade = 0.00;
            return;
        }

        tex_aluno.setText(alunoSelecionado.getNome());

        getMensalidade();
    }

    private void salvar() throws Exception {
        if (isDadosInvalidos()) {
            return;
        }

        if (editar) {
            editar();
        } else {
            inserir();
        }
    }

    private void editar() throws Exception {
        recebimentoEdit.setDataPagamento(DataHora.stringParseDate(dat_dataPagamento.getEditor().getText(), "dd/MM/yyyy"));
        recebimentoEdit.setFormaPagamento(cai_formaPagamento.getSelectionModel().getSelectedItem());
        recebimentoEdit.setMatricula(chb_matricula.isSelected());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, alunoSelecionado.getDiaPrePagamento());
        recebimentoEdit.setDataVencimento(cal.getTime());

        recebimentoEdit.setUsuarioEdicao(InstanciaSistema.getUsuarioLogado());
        recebimentoEdit.setDataEdicao(new Date());

        boolean retorno = recebimentosDao.atualizar(recebimentoEdit);

        if (!retorno) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar tentar atualizar o registro");
            alertErro.show();
            return;
        }

        janela.close();
    }

    private void inserir() throws Exception {
        Recebimentos recebimento = getRecebimento();
        int codigo = recebimentosDao.inserir(getRecebimento());

        if (codigo <= 0) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar inserir o registro");
            alertErro.show();
            return;
        }

        recebimento.setId(codigo);
        objetoRetorno = recebimento;
        janela.close();
    }

    private boolean isDadosInvalidos() {
        StringBuilder mensagensErro = new StringBuilder();

        if (dat_dataPagamento.getEditor().getText() == null || dat_dataPagamento.getEditor().getText().isEmpty()) {
            mensagensErro.append("Data Pagamento é de preenchimento obrigatório.\n");
        } else if (dat_dataPagamento.getValue().isAfter(LocalDate.now())) {
            mensagensErro.append("Data Pagamento não pode ser maior que a data atual.\n");
        }

        if (cai_formaPagamento.getSelectionModel().getSelectedItem() == null) {
            mensagensErro.append("Forma de Pagamento é de preenchimento obrigatório.\n");
        }

        if (!editar) {
            if (alunoSelecionado == null) {
                mensagensErro.append("Aluno é de preenchimento obrigatório.\n");
            }

            try {
                if (tex_qtdMensalidade.getText() == null || tex_qtdMensalidade.getText().isEmpty()) {
                    mensagensErro.append("Qtd. Mensalidade é de preenchimento obrigatório.\n");
                } else if (Integer.parseInt(tex_qtdMensalidade.getText()) <= 0) {
                    mensagensErro.append("Qtd. Mensalidade deve ser maior que 0.\n");
                }
            } catch (Exception ex) {
                mensagensErro.append("Qtd. Mensalidade está com um formato invalido.\n");
            }

            if ((tex_totalPagamento.getText() == null || tex_totalPagamento.getText().isEmpty())
                    && (tex_vPago.getText() == null || tex_vPago.getText().isEmpty())) {
                mensagensErro.append("Informe o valor pago.\n");
            } else if ((!tex_totalPagamento.getText().isEmpty() && !tex_vPago.getText().isEmpty())
                    && Decimal.valorFormatadoParseDouble(tex_vPago.getText()) < Decimal.valorFormatadoParseDouble(tex_totalPagamento.getText())) {
                mensagensErro.append("O valor pago informado está invalido.\n");
            } else if (!tex_vPago.getText().isEmpty() && Decimal.valorFormatadoParseDouble(tex_vPago.getText()) <= 0) {
                mensagensErro.append("O valor pago informado está invalido.\n");
            }
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

    private Recebimentos getRecebimento() throws ParseException {
        Recebimentos recebimento = new Recebimentos();
        recebimento.setAluno(alunoSelecionado);
        recebimento.setUsuario(InstanciaSistema.getUsuarioLogado());
        recebimento.setDataPagamento(DataHora.stringParseDate(dat_dataPagamento.getEditor().getText(), "dd/MM/yyyy"));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, alunoSelecionado.getDiaPrePagamento());
        recebimento.setDataVencimento(cal.getTime());

        recebimento.setFormaPagamento(cai_formaPagamento.getSelectionModel().getSelectedItem());
        recebimento.setMatricula(chb_matricula.isSelected());

        if (tex_totalPagamento.getText().isEmpty()) {
            recebimento.setvRecebimento(Decimal.valorFormatadoParseDouble(tex_vPago.getText()));
            recebimento.setvTotal(Decimal.valorFormatadoParseDouble(tex_vPago.getText()));
        } else {
            recebimento.setvRecebimento(Decimal.valorFormatadoParseDouble(tex_totalPagamento.getText()));
            recebimento.setvDesconto(tex_vDesconto.getText().isEmpty() ? 0.00 : Decimal.valorFormatadoParseDouble(tex_vDesconto.getText()));
            recebimento.setvTotal(recebimento.getvRecebimento() - recebimento.getvDesconto());
        }

        return recebimento;
    }

    private void getMensalidade() throws SQLException {
        vMensalidade = turmasDao.getvMensalidadeByAluno(alunoSelecionado);

        if (vMensalidade <= 0.00) {
            return;
        }

        tex_qtdMensalidade.setText("1");
        calcularTotal();
    }

    private void calcularTotal() {
        if (tex_qtdMensalidade.getText() == null || tex_qtdMensalidade.getText().isEmpty()) {
            return;
        }

        double total = vMensalidade * (Integer.parseInt(tex_qtdMensalidade.getText()));

        tex_totalPagamento.setText("R$ " + Decimal.formatar(total, "#,##0.00"));
        tex_TotalPagar.setText("R$ " + Decimal.formatar(total, "#,##0.00"));
        aplicarDesconto();
    }

    private void aplicarDesconto() {
        if ((tex_vDesconto.getText() == null || tex_vDesconto.getText().isEmpty())
                || (tex_totalPagamento.getText() == null || tex_totalPagamento.getText().isEmpty())) {
            return;
        }

        double vDesconto = Decimal.valorFormatadoParseDouble(tex_vDesconto.getText());

        if (vDesconto <= 0) {
            return;
        }

        double vTotalPagar = Decimal.valorFormatadoParseDouble(tex_totalPagamento.getText());
        double vTotal = vTotalPagar - vDesconto;

        tex_TotalPagar.setText("R$ " + Decimal.formatar(vTotal, "#,##0.00"));
    }

    private void calcularTroco() {
        if (tex_vPago.getText() == null || tex_vPago.getText().isEmpty()) {
            tex_vPago.clear();
            return;
        }

        double troco = Decimal.valorFormatadoParseDouble(tex_vPago.getText()) - Decimal.valorFormatadoParseDouble(tex_TotalPagar.getText());
        tex_vTroco.setText("R$ " + Decimal.formatar(troco, "#,##0.00"));
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Recebimentos recebimento, boolean edita) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RecebimentosInserirEditarControle.class.getResource("/academia/telas/RecebimentosInserirEditar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Cadastro e atualização dos Recebimentos");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(RecebimentosInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.recebimentoEdit = recebimento;
        instancia.editar = edita;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
