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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AlunosControle implements Initializable {

    @FXML
    private Button bot_editar;

    @FXML
    private Button bot_excluir;

    @FXML
    private Button bot_inserir;

    @FXML
    private Button bot_pesquisar;

    @FXML
    private Button bot_visualizar;

    @FXML
    private TableColumn<Alunos, Integer> col_codigo;

    @FXML
    private TableColumn<Alunos, String> col_nome;

    @FXML
    private TableColumn<Alunos, String> col_telefone;

    @FXML
    private TableView<Alunos> tab_alunos;

    @FXML
    private TextField tex_nome;

    public static BorderPane janela;

    /**
     * Objeto de conexão com a tabela usuario
     */
    private AlunosDao alunosDao = AlunosDao.getInstance();

    private ObservableList<Alunos> observableListAlunos;

    boolean excluir = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_codigo.setCellValueFactory(new PropertyValueFactory<Alunos, Integer>("id"));
        col_nome.setCellValueFactory(new PropertyValueFactory<Alunos, String>("nome"));
        col_telefone.setCellValueFactory(new PropertyValueFactory<Alunos, String>("telefoneFormatado"));

        setEventos();
    }

    public AlunosControle() throws Exception {
    }

    private void setEventos() {
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
        bot_visualizar.setOnAction(eventHandlerAction);
        tex_nome.setOnKeyPressed(eventHandlerKey);
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
                } else if (event.getSource().equals(bot_inserir)) {
                    inserir();
                } else if (event.getSource().equals(bot_editar)) {
                    editar();
                } else if (event.getSource().equals(bot_excluir)) {
                    excluir();
                } else if (event.getSource().equals(bot_visualizar)) {
                    visualizar();
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
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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

    private void inserir() throws Exception {
        Alunos alunos = AlunosInserirEditarControle.showDialogInserir();

        if (alunos == null) {
            return;
        }

        addObservableListAlunos(alunos);

        tab_alunos.setItems(observableListAlunos);
    }

    public void addObservableListAlunos(Alunos alunos) {
        if (observableListAlunos == null) {
            List<Alunos> list = new ArrayList<>();
            list.add(alunos);
            observableListAlunos = FXCollections.observableList(list);
        } else {
            observableListAlunos.add(alunos);
        }
    }

    private void editar() throws Exception {
        Alunos alunos = tab_alunos.getSelectionModel().getSelectedItem();

        if (alunos == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        AlunosInserirEditarControle.showDialogEditar(alunos);
        tab_alunos.refresh();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    private void excluir() throws Exception {
        Alunos alunos = tab_alunos.getSelectionModel().getSelectedItem();

        if (alunos == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        ButtonType bot_sim = new ButtonType("Sim");
        ButtonType bot_nao = new ButtonType("Não");
        excluir = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Janela de Confirmação");
        alert.setContentText("Deseja realmente excluir o aluno " + alunos.getNome() + " ?");
        alert.getButtonTypes().setAll(bot_nao, bot_sim);
        alert.showAndWait().ifPresent(b -> {
            if (b == bot_sim) {
                excluir = true;
            }
        });

        if (excluir) {
            boolean excluido = alunosDao.excluir(alunos);
            if (excluido) {
                tab_alunos.getItems().remove(alunos);
                tab_alunos.refresh();
            } else {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Janela de Erro");
                alertErro.setContentText("Erro ao tentar excluir um Aluno");
                alertErro.show();
            }
        }
    }

    private void visualizar() throws IOException, URISyntaxException {
        Alunos alunos = tab_alunos.getSelectionModel().getSelectedItem();

        if (alunos == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        AlunoVisualizarControle.abrirTela(alunos);
    }

    public static BorderPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(AlunosControle.class.getResource("/academia/telas/Alunos.fxml")));
            janela = (BorderPane) loader.load();
        }

        return janela;
    }
}
