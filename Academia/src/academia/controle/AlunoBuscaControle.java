package academia.controle;

import academia.dao.AlunosDao;
import academia.entidades.Alunos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AlunoBuscaControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_pesquisar;

    @FXML
    private Button bot_salvar;

    @FXML
    private TableColumn<Alunos, String> col_cpf;

    @FXML
    private TableColumn<Alunos, String> col_nome;

    @FXML
    private TableView<Alunos> tab_alunos;

    @FXML
    private TextField tex_nome;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static AlunoBuscaControle instancia;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private AlunosDao alunosDao = AlunosDao.getInstance();

    /**
     * Armazena o objeto usuario que será retornado da tela
     */
    private List<Alunos> objetoRetorno;

    private ObservableList<Alunos> observableListAlunos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_nome.setCellValueFactory(new PropertyValueFactory<Alunos, String>("nome"));
        col_cpf.setCellValueFactory(new PropertyValueFactory<Alunos, String>("cpfFormatado"));
        tab_alunos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public static Alunos showDialogBusca() throws Exception {
        AlunoBuscaControle.abrirTela();
        return instancia.getObjetoRetorno() == null || instancia.getObjetoRetorno().isEmpty()
                ? null : instancia.getObjetoRetorno().get(0);
    }

    public static List<Alunos> showDialogBuscaListAlunos() throws Exception {
        AlunoBuscaControle.abrirTela();
        return instancia.getObjetoRetorno();
    }

    public List<Alunos> getObjetoRetorno() {
        return objetoRetorno;
    }

    public AlunoBuscaControle() throws Exception {

    }

    private void configuracoes() {
        setEventos();
    }

    private void setEventos() {
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_cancelar.setOnAction(eventHandlerAction);
        bot_salvar.setOnAction(eventHandlerAction);
        tex_nome.setOnKeyPressed(eventHandlerKey);
        tab_alunos.setOnKeyPressed(eventHandlerKey);
        tab_alunos.setOnMouseClicked(eventHandlerMouse);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_pesquisar)) {
                    filtrar();
                } else if (event.getSource().equals(bot_cancelar)) {
                    janela.close();
                } else if (event.getSource().equals(bot_salvar)) {
                    selecionarAluno();
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
            try {
                if (keyEvent.getSource().equals(tex_nome) && keyEvent.getCode() == KeyCode.ENTER) {
                    filtrar();
                } else if (keyEvent.getSource().equals(tex_nome) && keyEvent.getCode() == KeyCode.DOWN) {
                    tab_alunos.requestFocus();
                    tab_alunos.getSelectionModel().select(0);
                } else if (keyEvent.getSource().equals(tab_alunos) && keyEvent.getCode() == KeyCode.ENTER) {
                    selecionarAluno();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private final EventHandler<MouseEvent> eventHandlerMouse = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(tab_alunos) && mouseEvent.getClickCount() == 2) {
                selecionarAluno();
            }
        }
    };

    private void filtrar() throws SQLException {
        String sqlComplementar = "WHERE ";

        if (tex_nome.getText() != null) {
            sqlComplementar += "nome LIKE '" + tex_nome.getText() + "%'";
        }

        observableListAlunos = FXCollections.observableList(alunosDao.getAlunos(sqlComplementar));
        tab_alunos.setItems(observableListAlunos);
    }

    private void selecionarAluno() {
        ObservableList<Alunos> observableList = tab_alunos.getSelectionModel().getSelectedItems();

        if (observableList == null || observableList.isEmpty()) {
            alertaSelecioneUmRegistro();
            return;
        }

        List<Alunos> list = new ArrayList<>();
        for (Alunos alunos : observableList) {
            list.add(alunos);
        }
        objetoRetorno = list;

        janela.close();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela() throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AlunoBuscaControle.class.getResource("/academia/telas/AlunoBusca.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Busca Aluno");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(RecebimentosInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.configuracoes();

        janela.showAndWait();
    }
}
