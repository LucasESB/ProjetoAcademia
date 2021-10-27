package academia;

import academia.controle.LoginControle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class InicializaJanela extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Login.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        LoginControle.janela = primaryStage;
        primaryStage.show();
    }
    
    /**
     * Metodo responsavel por abrir a tela
     */
    public static void abrirTela(String[] args){
        launch(args);
    }
}
