package academia.controle;

import academia.dao.TurmasDao;
import academia.entidades.Alunos;
import academia.entidades.Turmas;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TurmasControle implements Initializable {

    @FXML
    private Button bot_addAlunos;

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
    private TableColumn<Turmas, Integer> col_codigo;

    @FXML
    private TableColumn<Turmas, String> col_descricao;

    @FXML
    private TableColumn<Turmas, String> col_mensalidade;

    @FXML
    private TableView<Turmas> tab_turmas;

    @FXML
    private TextField tex_descricao;

    public static BorderPane janela;

    /**
     * Objeto de conexão com a tabela usuario
     */
    private TurmasDao turmasDao = TurmasDao.getInstance();

    private ObservableList<Turmas> observableListTurmas;

    boolean excluir = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_codigo.setCellValueFactory(new PropertyValueFactory<Turmas, Integer>("id"));
        col_descricao.setCellValueFactory(new PropertyValueFactory<Turmas, String>("descricao"));
        col_mensalidade.setCellValueFactory(new PropertyValueFactory<Turmas, String>("mensalidade"));

        setEventos();
    }

    public TurmasControle() throws Exception {
    }

    private void setEventos() {
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
        bot_visualizar.setOnAction(eventHandlerAction);
        bot_addAlunos.setOnAction(eventHandlerAction);
        tex_descricao.setOnKeyPressed(eventHandlerKey);
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
                } else if (event.getSource().equals(bot_addAlunos)) {
                    addAluno();
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
                if (keyEvent.getSource().equals(tex_descricao) && keyEvent.getCode() == KeyCode.ENTER) {
                    filtrar();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void filtrar() throws SQLException {
        String sqlComplementar = "WHERE ";

        if (tex_descricao.getText() != null) {
            sqlComplementar += "descricao LIKE '" + tex_descricao.getText() + "%'";
        }

        observableListTurmas = FXCollections.observableList(turmasDao.getTurmas(sqlComplementar));
        tab_turmas.setItems(observableListTurmas);
    }

    private void inserir() throws Exception {
        Turmas turma = TurmasInserirEditarControle.showDialogInserir();

        if (turma == null) {
            return;
        }

        addObservableListAlunos(turma);

        tab_turmas.setItems(observableListTurmas);
    }

    public void addObservableListAlunos(Turmas turma) {
        if (observableListTurmas == null) {
            List<Turmas> list = new ArrayList<>();
            list.add(turma);
            observableListTurmas = FXCollections.observableList(list);
        } else {
            observableListTurmas.add(turma);
        }
    }

    private void editar() throws Exception {
        Turmas turma = tab_turmas.getSelectionModel().getSelectedItem();

        if (turma == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        TurmasInserirEditarControle.showDialogEditar(turma);
        tab_turmas.refresh();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    private void excluir() throws Exception {
        Turmas turma = tab_turmas.getSelectionModel().getSelectedItem();

        if (turma == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        ButtonType bot_sim = new ButtonType("Sim");
        ButtonType bot_nao = new ButtonType("Não");
        excluir = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Janela de Confirmação");
        alert.setContentText("Deseja realmente excluir a turma de " + turma.getDescricao() + " ?");
        alert.getButtonTypes().setAll(bot_nao, bot_sim);
        alert.showAndWait().ifPresent(b -> {
            if (b == bot_sim) {
                excluir = true;
            }
        });

        if (excluir) {
            boolean excluido = turmasDao.excluir(turma);
            if (excluido) {
                tab_turmas.getItems().remove(turma);
                tab_turmas.refresh();
            } else {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Janela de Erro");
                alertErro.setContentText("Erro ao tentar excluir um Aluno");
                alertErro.show();
            }
        }
    }

    private void visualizar() throws Exception {
        Turmas turma = tab_turmas.getSelectionModel().getSelectedItem();

        if (turma == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        TurmaVisualizarControle.abrirTela(turma);
    }

    private void addAluno() throws Exception {
        Turmas turma = tab_turmas.getSelectionModel().getSelectedItem();

        if (turma == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        List<Alunos> list = AlunoBuscaControle.showDialogBuscaListAlunos();

        if (list == null || list.isEmpty()) {
            return;
        }

        boolean retorno = turmasDao.inserirAlunos(turma, list);

        if (!retorno) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar Inserir alunos na turma, verifique se todos os alunos foram inseridos corretamente.");
            alertErro.show();
            return;
        }

        TurmaVisualizarControle.abrirTela(turma);
    }

    public static BorderPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(TurmasControle.class.getResource("/academia/telas/Turmas.fxml")));
            janela = (BorderPane) loader.load();
        }

        return janela;
    }
}
