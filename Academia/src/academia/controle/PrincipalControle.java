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
    private Button bot_menuItemAlunos;

    @FXML
    private Button bot_menuItemFinanceiro;

    @FXML
    private Button bot_menuItemHome;

    @FXML
    private Button bot_menuItemRecebimentos;

    @FXML
    private Button bot_menuItemSair;

    @FXML
    private Button bot_menuItemUsuarios;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void configuracoes() throws IOException {
        setEventos();
        abrirTelaHome();
    }

    /**
     * Construtor
     */
    public PrincipalControle() {
    }

    private void setEventos() {
        bot_menuItemHome.setOnAction(eventHandlerAction);
        bot_menuItemAlunos.setOnAction(eventHandlerAction);
        bot_menuItemRecebimentos.setOnAction(eventHandlerAction);
        bot_menuItemFinanceiro.setOnAction(eventHandlerAction);
        bot_menuItemUsuarios.setOnAction(eventHandlerAction);
        bot_menuItemSair.setOnAction(eventHandlerAction);
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
                } else if (event.getSource().equals(bot_menuItemAlunos)) {
                    abrirTelaAlunos();
                } else if (event.getSource().equals(bot_menuItemRecebimentos)) {
                    abrirTelaRecebimentos();
                } else if (event.getSource().equals(bot_menuItemFinanceiro)) {
                    abrirTelaFinanceiro();
                } else if (event.getSource().equals(bot_menuItemUsuarios)) {
                    abrirTelaUsuario();
                } else if (event.getSource().equals(bot_menuItemSair)) {
                    sairSistema();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void abrirTelaHome() throws IOException {
        addERemoveBordaArrendodaBotao(bot_menuItemHome);
        AnchorPane a = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Home.fxml")));
        areaVisualizacao.getChildren().setAll(a);
    }

    private void addERemoveBordaArrendodaBotao(Button botao) {
        bot_menuItemHome.setStyle(null);
        bot_menuItemAlunos.setStyle(null);
        bot_menuItemRecebimentos.setStyle(null);
        bot_menuItemFinanceiro.setStyle(null);
        bot_menuItemUsuarios.setStyle(null);
        bot_menuItemSair.setStyle(null);

        botao.setStyle("-fx-background-color:  #54B1F3; -fx-background-radius: 20px 0px 0px 20px;");
    }

    private void abrirTelaAlunos() throws IOException {
        addERemoveBordaArrendodaBotao(bot_menuItemAlunos);
        BorderPane a = (BorderPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Alunos.fxml")));
        areaVisualizacao.getChildren().setAll(a);
    }

    private void abrirTelaRecebimentos() throws IOException {
        addERemoveBordaArrendodaBotao(bot_menuItemRecebimentos);
        //TODO Inplementar modulo de recebimentos
//        AnchorPane a = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Alunos.fxml")));
//        areaVisualizacao.getChildren().setAll(a);
    }

    private void abrirTelaFinanceiro() throws IOException {
        addERemoveBordaArrendodaBotao(bot_menuItemFinanceiro);
        //TODO inplmentar modulo fincanceiro
//        AnchorPane a = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Alunos.fxml")));
//        areaVisualizacao.getChildren().setAll(a);
    }

    private void abrirTelaUsuario() throws IOException {
        addERemoveBordaArrendodaBotao(bot_menuItemUsuarios);
        BorderPane a = (BorderPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/academia/telas/Usuario.fxml")));
        areaVisualizacao.getChildren().setAll(a);
    }

    private void sairSistema() throws Exception {
        LoginControle.abrirTela();
        janela.close();
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

        PrincipalControle instancia = loader.getController();
        instancia.configuracoes();

        janela.show();
    }

}
