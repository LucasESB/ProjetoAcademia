package academia.controle;

import academia.dao.TurmasDao;
import academia.entidades.Alunos;
import academia.entidades.Turmas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TurmaVisualizarControle implements Initializable {

    @FXML
    private TableColumn<Alunos, String> col_nome;

    @FXML
    private TableColumn<Alunos, String> col_telefone;

    @FXML
    private TableView<Alunos> tab_alunos;

    @FXML
    private TextField tex_vMensalidade;

    @FXML
    private TextField tex_descricao;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static TurmaVisualizarControle instancia;

    private Turmas turma;

    private TurmasDao turmasDao = TurmasDao.getInstance();

    private ObservableList<Alunos> observableListAlunos;

    public TurmaVisualizarControle() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_nome.setCellValueFactory(new PropertyValueFactory<Alunos, String>("nome"));
        col_telefone.setCellValueFactory(new PropertyValueFactory<Alunos, String>("telefoneFormatado"));
    }

    private void configuracoes() throws SQLException {
        setDados();
    }

    private void setDados() throws SQLException {
        tex_descricao.setText(turma.getDescricao());
        tex_vMensalidade.setText(turma.getMensalidade());
        loadAlunos();
    }

    private void loadAlunos() throws SQLException {
        tab_alunos.setItems(FXCollections.observableList(turmasDao.getAlunosByTurma(turma)));
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Turmas turma) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TurmaVisualizarControle.class.getResource("/academia/telas/TurmaVisualizar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Dados da Turma");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(TurmaVisualizarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.turma = turma;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
