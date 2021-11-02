package academia;

import academia.controle.LoginControle;
import javafx.application.Application;
import javafx.stage.Stage;

public class InicializaJanela extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginControle.abrirTela();
    }
    
    /**
     * Metodo responsavel por abrir a tela
     */
    public static void abrirTela(String[] args){
        launch(args);
    }
}
