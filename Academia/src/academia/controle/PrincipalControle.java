package academia.controle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrincipalControle implements Initializable {

    @FXML
    private AnchorPane areaVisualizacao;

    @FXML
    private Button bot_menuItemHome;

    @FXML
    private Button bot_menuItemUsuarios;

    private static PrincipalControle instancia;

    private static Stage janela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventos();
    }

    /**
     * Construtor
     */
    public PrincipalControle(){ }

    private void setEventos(){
        bot_menuItemHome.setOnAction(eventHandlerAction);
        bot_menuItemUsuarios.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel por controlar os eventos dos componentes
     */
    private final EventHandler<ActionEvent> eventHandlerAction = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_menuItemHome)) {
                    abrirTelaHome();
                } else if (event.getSource().equals(bot_menuItemUsuarios)) {
                    abrirTelaUsuario();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void abrirTelaHome(){

    }

    private void abrirTelaUsuario() throws IOException {
        BorderPane a = (BorderPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Usuario.fxml")));
        areaVisualizacao.getChildren().setAll(a);
    }


    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PrincipalControle.class.getResource("/academia/telas/Principal.fxml"));
        BorderPane page = (BorderPane) loader.load();

        janela = new Stage();
        janela.setTitle("Tela Principal");
        Scene scene = new Scene(page);
        janela.setScene(scene);

        instancia = loader.getController();

        //Transforma uma janela em modal e não deixar arrastar
        //janela.initModality(Modality.APPLICATION_MODAL);
        janela.show();
    }

}
