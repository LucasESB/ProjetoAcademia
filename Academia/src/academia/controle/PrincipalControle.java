package academia.controle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalControle implements Initializable {

    private static PrincipalControle instancia;

    private static Stage janela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Construtor privatdo
     */
    public PrincipalControle(){ }

    public static void abrirTela() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PrincipalControle.class.getResource("/academia/telas/Principal.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

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
