package academia.controle;

import academia.dao.UsuarioDao;
import academia.entidades.Usuario;
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

public class UsuarioControle implements Initializable {
    @FXML
    private Button bot_editar;

    @FXML
    private Button bot_excluir;

    @FXML
    private Button bot_inserir;

    @FXML
    private Button bot_pesquisar;

    @FXML
    private TableView<Usuario> tab_usuarios;

    @FXML
    private TableColumn<Usuario, String> col_adm;

    @FXML
    private TableColumn<Usuario, Integer> col_codigo;

    @FXML
    private TableColumn<Usuario, String> col_nome;

    @FXML
    private TextField tex_nome;

    public static BorderPane janela;
    /**
     * Objeto de conexão com a tabela usuario
     */
    private UsuarioDao usuarioDao = UsuarioDao.getInstance();

    private ObservableList<Usuario> observableListUsuario;

    boolean excluir = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_adm.setCellValueFactory(new PropertyValueFactory<Usuario, String>("admDescricao"));
        col_codigo.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        col_nome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));

        setEventos();
    }

    /**
     * Construtor
     */
    public UsuarioControle() throws Exception {
    }

    private void setEventos() {
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
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

        observableListUsuario = FXCollections.observableList(usuarioDao.getUsuarios(sqlComplementar));
        tab_usuarios.setItems(observableListUsuario);
    }

    private void inserir() throws Exception {
        Usuario usuario = UsuarioInserirEditarControle.showDialogInserir();

        if (usuario == null) {
            return;
        }

        addObservableListUsuario(usuario);

        tab_usuarios.setItems(observableListUsuario);
    }

    public void addObservableListUsuario(Usuario usuario) {
        if (observableListUsuario == null) {
            List<Usuario> list = new ArrayList<>();
            list.add(usuario);
            observableListUsuario = FXCollections.observableList(list);
        } else {
            observableListUsuario.add(usuario);
        }
    }

    private void editar() throws Exception {
        Usuario usuario = tab_usuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        UsuarioInserirEditarControle.showDialogEditar(usuario);
        tab_usuarios.refresh();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    private void excluir() throws Exception {
        Usuario usuario = tab_usuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        ButtonType bot_sim = new ButtonType("Sim");
        ButtonType bot_nao = new ButtonType("Não");
        excluir = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Janela de Confirmação");
        alert.setContentText("Deseja realmente excluir o usuário " + usuario.getNome() + " ?");
        alert.getButtonTypes().setAll(bot_nao, bot_sim);
        alert.showAndWait().ifPresent(b -> {
            if (b == bot_sim) {
                excluir = true;
            }
        });

        if (excluir) {
            boolean excluido = usuarioDao.excluir(usuario);
            if (excluido) {
                tab_usuarios.getItems().remove(usuario);
                tab_usuarios.refresh();
            } else {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Janela de Erro");
                alertErro.setContentText("Erro ao tentar excluir um Usuário");
                alertErro.show();
            }
        }
    }

    public static BorderPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(UsuarioControle.class.getResource("/academia/telas/Usuario.fxml")));
            janela = (BorderPane) loader.load();
        }

        return janela;
    }
}
