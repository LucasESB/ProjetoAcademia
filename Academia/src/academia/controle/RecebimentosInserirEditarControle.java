package academia.controle;

import academia.dao.RecebimentosDao;
import academia.entidades.Alunos;
import academia.entidades.Recebimentos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecebimentosInserirEditarControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_salvar;

    @FXML
    private TextField tex_aluno;

    @FXML
    private Button bot_inserirAluno;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static RecebimentosInserirEditarControle instancia;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentosDao = RecebimentosDao.getInstance();

    /**
     * Armazena o objeto usuario que será retornado da tela
     */
    private Recebimentos objetoRetorno;

    private boolean editar;

    private Recebimentos recebimentoEdit;

    private Alunos alunoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static Recebimentos showDialogInserir() throws Exception {
        RecebimentosInserirEditarControle.abrirTela(null, false);
        return instancia.getObjetoRetorno();
    }

    public static void showDialogEditar(Recebimentos recebimento) throws Exception {
        RecebimentosInserirEditarControle.abrirTela(recebimento, true);
    }

    public Recebimentos getObjetoRetorno() {
        return objetoRetorno;
    }

    public RecebimentosInserirEditarControle() throws Exception {

    }

    private void configuracoes() {
        setEventos();
    }

    private void setEventos() {
        bot_inserirAluno.setOnAction(eventHandlerAction);
        tex_aluno.setOnMouseClicked(eventHandlerMouse);
    }

    private final EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_inserirAluno)) {
                    inserirUmNovoAluno();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Metodo responsaveç pelos eventos do mouse/
     */
    private final EventHandler<MouseEvent> eventHandlerMouse = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                if (mouseEvent.getSource().equals(tex_aluno) && mouseEvent.getClickCount() == 2) {
                    buscarAluno();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void inserirUmNovoAluno() throws Exception {
        alunoSelecionado = AlunosInserirEditarControle.showDialogInserir();

        if (alunoSelecionado == null) {
            tex_aluno.clear();
            return;
        }

        tex_aluno.setText(alunoSelecionado.getNome());
    }

    private void buscarAluno() throws Exception {
        alunoSelecionado = AlunoBuscaControle.showDialogBusca();

        if (alunoSelecionado == null) {
            tex_aluno.clear();
            return;
        }

        tex_aluno.setText(alunoSelecionado.getNome());
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Recebimentos recebimento, boolean edita) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RecebimentosInserirEditarControle.class.getResource("/academia/telas/RecebimentosInserirEditar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Cadastro e atualização dos Recebimentos");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(RecebimentosInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.recebimentoEdit = recebimento;
        instancia.editar = edita;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
