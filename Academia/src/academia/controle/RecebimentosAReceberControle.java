package academia.controle;

import academia.dao.RecebimentosDao;
import academia.entidades.RecebimentosAReceber;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RecebimentosAReceberControle implements Initializable {

    @FXML
    private TableColumn<RecebimentosAReceber, String> col_vMensalidade;

    @FXML
    private TableColumn<RecebimentosAReceber, String> col_dataVencimento;

    @FXML
    private TableColumn<RecebimentosAReceber, String> col_nome;

    @FXML
    private TableColumn<RecebimentosAReceber, String> col_telefone;

    @FXML
    private TableColumn<RecebimentosAReceber, String> col_whatsapp;

    @FXML
    private TableView<RecebimentosAReceber> tab_recebimentoAReceber;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static RecebimentosAReceberControle instancia;

    private Date periodo;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentosDao = RecebimentosDao.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_vMensalidade.setCellValueFactory(new PropertyValueFactory<RecebimentosAReceber, String>("vMensalidade"));
        col_dataVencimento.setCellValueFactory(new PropertyValueFactory<RecebimentosAReceber, String>("dataVencimento"));
        col_nome.setCellValueFactory(new PropertyValueFactory<RecebimentosAReceber, String>("nomeAluno"));
        col_telefone.setCellValueFactory(new PropertyValueFactory<RecebimentosAReceber, String>("telefoneAluno"));
        col_whatsapp.setCellValueFactory(new PropertyValueFactory<RecebimentosAReceber, String>("whatsAppAluno"));
    }

    public RecebimentosAReceberControle() throws Exception {
    }

    private void configuracoes() throws Exception {
        loadRecebimentosAReceber();
    }

    private void loadRecebimentosAReceber() throws Exception {
        tab_recebimentoAReceber.setItems(FXCollections.observableList(recebimentosDao.getRecebimentosAReceber(periodo)));
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Date periodo) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RecebimentosAReceberControle.class.getResource("/academia/telas/RecebimentosAReceber.fxml"));
        StackPane page = (StackPane) loader.load();

        janela = new Stage();
        janela.setTitle("Recebimentos a receber do mês");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(AlunoVisualizarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        try {
            instancia = loader.getController();
            instancia.periodo = periodo;
            instancia.configuracoes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        janela.showAndWait();
    }
}
