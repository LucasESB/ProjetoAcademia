package academia.controle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
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

    /**
     * Instancia da janela
     */
    private static Stage janela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventos();
        abrirTelaHome();
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
        addRemoveBordaArrendodaBotao(bot_menuItemHome);
    }

    private void addRemoveBordaArrendodaBotao(Button botao){
        bot_menuItemHome.setStyle(null);
        bot_menuItemUsuarios.setStyle(null);

        botao.setStyle("-fx-background-color:  #54B1F3; -fx-background-radius: 20px 0px 0px 20px;");
    }

    private void abrirTelaUsuario() throws IOException {
        addRemoveBordaArrendodaBotao(bot_menuItemUsuarios);
        BorderPane a = (BorderPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Usuario.fxml")));
        areaVisualizacao.getChildren().setAll(a);
    }


    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela() throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PrincipalControle.class.getResource("/academia/telas/Principal.fxml"));
        BorderPane page = (BorderPane) loader.load();

        janela = new Stage();
        janela.setTitle("UpFit - Sistema");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(PrincipalControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));

        janela.show();
    }

}
