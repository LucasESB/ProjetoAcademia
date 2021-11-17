package academia.controle;

import academia.bean.FormaPagamentoBean;
import academia.dao.RecebimentosDao;
import academia.dao.TurmasDao;
import academia.entidades.Alunos;
import academia.entidades.Recebimentos;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private TextField vTroco;

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

    private void configuracoes() {
        dat_dataPagamento.setValue(LocalDate.now());
        loadFormasPagamentos();
        setMascaras();
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

    private void setEventos() {
        bot_inserirAluno.setOnAction(eventHandlerAction);
        bot_cancelar.setOnAction(eventHandlerAction);
        tex_vDesconto.setOnAction(eventHandlerAction);
        tex_aluno.setOnMouseClicked(eventHandlerMouse);
    }

    private final EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_inserirAluno)) {
                    inserirUmNovoAluno();
                } else if (event.getSource().equals(bot_cancelar)) {
                    janela.close();
                } else if (event.getSource().equals(tex_vDesconto)) {
                    aplicarDesconto();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
            return;
        }

        tex_aluno.setText(alunoSelecionado.getNome());

        getMensalidade();
    }

    private void getMensalidade() throws SQLException {
        double vMensalidade = turmasDao.getvMensalidadeByAluno(alunoSelecionado);

        if (vMensalidade <= 0.00) {
            return;
        }

        String v = "R$ " + Decimal.formatar(vMensalidade, "#,##0.00");
        tex_totalPagamento.setText(v);
        tex_TotalPagar.setText(v);
    }

    private void aplicarDesconto() {
        if ((tex_vDesconto.getText() == null || tex_vDesconto.getText().isEmpty())
                && (tex_totalPagamento.getText() == null || tex_totalPagamento.getText().isEmpty())) {
            return;
        }

        double vDesconto = Decimal.valorFormatadoParseDouble(tex_vDesconto.getText());

        if (vDesconto <= 0) {
            return;
        }

        double vMensalidade = Decimal.valorFormatadoParseDouble(tex_totalPagamento.getText());
        double vTotal = vMensalidade - vDesconto;

        tex_TotalPagar.setText("R$ "+ Decimal.formatar(vTotal, "#,##0.00"));
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Recebimentos recebimento, boolean edita) throws IOException, URISyntaxException {
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
