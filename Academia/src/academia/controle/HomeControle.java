package academia.controle;

import academia.dao.AlunosDao;
import academia.dao.RecebimentosDao;
import academia.utilitarios.Decimal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeControle implements Initializable {

    @FXML
    private Label tex_aReceber;

    @FXML
    private Label tex_clientesAtivos;

    @FXML
    private Label tex_clientesNovos;

    public static StackPane janela;

    private AlunosDao alunosDao = AlunosDao.getInstance();

    private RecebimentosDao recebimentosDao = RecebimentosDao.getInstance();

    private static HomeControle instancia;

    public HomeControle() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void configuracoes() {
        try {
            loadDados();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadDados() throws Exception {
        loadNovosClientes();
        loadAlunosAtivos();
        loadRecebimentosAReceber();
    }

    private void loadNovosClientes() throws Exception {
        tex_clientesNovos.setText(Integer.toString(alunosDao.getQtdNovosAlunos(new Date())));
    }

    private void loadAlunosAtivos() throws Exception {
        tex_clientesAtivos.setText(Integer.toString(alunosDao.getQtdAlunosAtivos()));
    }

    private void loadRecebimentosAReceber() throws Exception {
        tex_aReceber.setText("R$ " + Decimal.formatar(recebimentosDao.getvAReceberDoDia(new Date()), "#,##0.00"));
    }

    public static StackPane getInstancia() throws IOException {
        if (janela == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(HomeControle.class.getResource("/academia/telas/Home.fxml")));
            janela = (StackPane) loader.load();

            instancia = loader.getController();
            instancia.configuracoes();
        }

        return janela;
    }
}
