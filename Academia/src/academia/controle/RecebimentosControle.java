package academia.controle;

import academia.dao.RecebimentosDao;
import academia.entidades.Recebimentos;
import academia.utilitarios.DataHora;
import academia.utilitarios.Decimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RecebimentosControle implements Initializable {

    @FXML
    private Button bot_aReceber;

    @FXML
    private Button bot_editar;

    @FXML
    private Button bot_excluir;

    @FXML
    private Button bot_inserir;

    @FXML
    private Button bot_pesquisar;

    @FXML
    private ComboBox<?> cai_mesRecebimento;

    @FXML
    private TableColumn<Recebimentos, String> col_aluno;

    @FXML
    private TableColumn<Recebimentos, String> col_datPagamento;

    @FXML
    private TableColumn<Recebimentos, String> col_datVencimento;

    @FXML
    private TableColumn<Recebimentos, String> col_usuarioRecebimento;

    @FXML
    private TableColumn<Recebimentos, String> col_vDesconto;

    @FXML
    private TableColumn<Recebimentos, String> col_vMensalidade;

    @FXML
    private TableColumn<Recebimentos, String> col_vTotal;

    @FXML
    private TableView<Recebimentos> tab_recebimentos;

    @FXML
    private TextField tex_anoRecebimento;

    @FXML
    private TextField tex_vAreceber;

    @FXML
    private TextField tex_vRecebidos;

    public static BorderPane janela;
    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentoDao = RecebimentosDao.getInstance();

    private ObservableList<Recebimentos> observableListRecebimentos;

    boolean excluir = false;

    public RecebimentosControle() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_aluno.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("nomeAluno"));
        col_datPagamento.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("dataPagamentoFormatado"));
        col_datVencimento.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("dataVencimentoFormatado"));
        col_usuarioRecebimento.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("nomeUsuario"));
        col_vDesconto.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vDescontoFormatado"));
        col_vMensalidade.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vRecebimentoFormatado"));
        col_vTotal.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vTotalFormatado"));
        setEventos();
    }

    private void setEventos() {
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
        bot_aReceber.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_pesquisar)) {
                    pesquisar();
                } else if (event.getSource().equals(bot_inserir)) {
                    inserir();
                } else if (event.getSource().equals(bot_editar)) {
                    editar();
                } else if (event.getSource().equals(bot_excluir)) {
                    excluir();
                } else if (event.getSource().equals(bot_aReceber)) {
                    aReceber();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void pesquisar() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);

        String data = DataHora.formatarData(cal.getTime(), "yyyy-MM-dd");
        String sql = "WHERE dataPagamento >= '" + data + " 00:00:00' ";

        cal.set(Calendar.DAY_OF_MONTH, DataHora.getUltimoDiaDoMes(cal.getTime()));
        data = DataHora.formatarData(cal.getTime(), "yyyy-MM-dd");

        sql += "AND dataPagamento <= '" + data + " 23:59:59' AND excluido = false";

        observableListRecebimentos = FXCollections.observableList(recebimentoDao.getRecebimentos(sql));

        tab_recebimentos.setItems(observableListRecebimentos);
        calcularTotais();
    }

    private void inserir() throws Exception {
        List<Recebimentos> listRecebimentos = RecebimentosInserirEditarControle.showDialogInserir();

        if (listRecebimentos == null || listRecebimentos.isEmpty()) {
            return;
        }

        addObservableListAlunos(listRecebimentos);

        tab_recebimentos.setItems(observableListRecebimentos);
    }

    public void addObservableListAlunos(List<Recebimentos> listRecebimentos) throws Exception {
        if (observableListRecebimentos == null) {
            observableListRecebimentos = FXCollections.observableList(listRecebimentos);
        } else {
            observableListRecebimentos.addAll(listRecebimentos);
        }

        calcularTotais();
    }

    private void calcularTotais() throws Exception {
        double totalRecebido = observableListRecebimentos.stream().mapToDouble(r -> r.getvTotal()).sum();
        tex_vRecebidos.setText("R$ " + Decimal.formatar(totalRecebido, "#,##0.00"));
        tex_vAreceber.setText("R$ " + Decimal.formatar(recebimentoDao.getvAReceberDoMes(new Date()), "#,##0.00"));
    }

    private void editar() throws Exception {
        Recebimentos recebimento = tab_recebimentos.getSelectionModel().getSelectedItem();

        if (recebimento == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        RecebimentosInserirEditarControle.showDialogEditar(recebimento);
        tab_recebimentos.refresh();
        calcularTotais();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    private void excluir() throws Exception {
        Recebimentos recebimento = tab_recebimentos.getSelectionModel().getSelectedItem();

        if (recebimento == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        ButtonType bot_sim = new ButtonType("Sim");
        ButtonType bot_nao = new ButtonType("Não");
        excluir = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Janela de Confirmação");
        alert.setContentText("Deseja realmente excluir o registro de código " + recebimento.getId() + " ?");
        alert.getButtonTypes().setAll(bot_nao, bot_sim);
        alert.showAndWait().ifPresent(b -> {
            if (b == bot_sim) {
                excluir = true;
            }
        });

        if (excluir) {
            boolean excluido = recebimentoDao.apagar(recebimento);
            if (excluido) {
                tab_recebimentos.getItems().remove(recebimento);
                tab_recebimentos.refresh();
                calcularTotais();
            } else {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Janela de Erro");
                alertErro.setContentText("Erro ao tentar excluir um recebimento");
                alertErro.show();
            }
        }
    }

    private void aReceber() throws Exception {
        RecebimentosAReceberControle.abrirTela();
    }

    public static BorderPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(TurmasControle.class.getResource("/academia/telas/Recebimentos.fxml")));
            janela = (BorderPane) loader.load();
        }

        return janela;
    }
}
