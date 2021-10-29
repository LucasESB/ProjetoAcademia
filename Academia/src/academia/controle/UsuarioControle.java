package academia.controle;

import academia.dao.UsuarioDao;
import academia.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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

    /**
     * Objeto de conexão com a tabela usuario
     */
    UsuarioDao usuarioDao = UsuarioDao.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_adm.setCellValueFactory(new PropertyValueFactory<Usuario, String>("adm"));
        col_codigo.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("código"));
        col_nome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));

        setEventos();
    }

    /**
     * Construtor
     */
    public UsuarioControle() throws Exception{ }

    private void setEventos(){
        bot_pesquisar.setOnAction(eventHandlerAction);
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if(event.getSource().equals(bot_pesquisar)){

                }
                else if(event.getSource().equals(bot_inserir)){

                }
                else if(event.getSource().equals(bot_editar)){

                }
                else if(event.getSource().equals(bot_excluir)){

                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };


}
