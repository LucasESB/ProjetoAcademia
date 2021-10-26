package academia.controle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class LoginControle extends Application {

    @FXML
    void sair() {
        System.exit(0);
    }

    @FXML
    void login(){
        System.out.println("Entrou");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Login.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    /**
     * Metodo responsavel por abrir a tela
     */
    public static void abrirTela(String[] args){
        launch(args);
    }
}
