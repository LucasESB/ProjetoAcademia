package academia;

import academia.controle.LoginControle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class InicializaJanela extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/academia/telas/Login.fxml")));
        Parent root = loader.load();

        LoginControle loginControle = loader.getController();
        loginControle.setJanela(primaryStage);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(getClass().getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));

        primaryStage.show();
    }
    
    /**
     * Metodo responsavel por abrir a tela
     */
    public static void abrirTela(String[] args){
        launch(args);
    }
}
