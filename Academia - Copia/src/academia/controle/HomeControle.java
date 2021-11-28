package academia.controle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeControle implements Initializable {

    public static StackPane janela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static StackPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(HomeControle.class.getResource("/academia/telas/Home.fxml")));
            janela = (StackPane) loader.load();
        }

        return janela;
    }
}
