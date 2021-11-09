package academia.controle;

import academia.dao.RecebimentosDao;
import academia.entidades.Recebimentos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecebimentosControle implements Initializable {

    @FXML
    private Button bot_editar;

    @FXML
    private Button bot_excluir;

    @FXML
    private Button bot_inserir;

    @FXML
    private TableView<Recebimentos> tab_recebimentos;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentoDao = RecebimentosDao.getInstance();

    private ObservableList<Recebimentos> observableListRecebimentos;

    boolean excluir = false;

    public RecebimentosControle() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventos();
    }

    private void setEventos() {
        bot_inserir.setOnAction(eventHandlerAction);
        bot_editar.setOnAction(eventHandlerAction);
        bot_excluir.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (event.getSource().equals(bot_inserir)) {
                    inserir();
                } else if (event.getSource().equals(bot_editar)) {
                    editar();
                } else if (event.getSource().equals(bot_excluir)) {
                    excluir();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private void inserir() throws Exception {
        Recebimentos recebimento = RecebimentosInserirEditarControle.showDialogInserir();

        if (recebimento == null) {
            return;
        }

        addObservableListAlunos(recebimento);

        tab_recebimentos.setItems(observableListRecebimentos);
    }

    public void addObservableListAlunos(Recebimentos recebimento) {
        if (observableListRecebimentos == null) {
            List<Recebimentos> list = new ArrayList<>();
            list.add(recebimento);
            observableListRecebimentos = FXCollections.observableList(list);
        } else {
            observableListRecebimentos.add(recebimento);
        }
    }

    private void editar() throws Exception {
        Recebimentos recebimento = tab_recebimentos.getSelectionModel().getSelectedItem();

        if (recebimento == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        RecebimentosInserirEditarControle.showDialogEditar(recebimento);
        tab_recebimentos.refresh();
    }

    private void alertaSelecioneUmRegistro() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Janela de Aviso");
        alert.setContentText("Selecione um registro para esta operação");
        alert.show();
    }

    private void excluir() throws Exception {
        Recebimentos recebimento = tab_recebimentos.getSelectionModel().getSelectedItem();

        if (recebimento == null) {
            alertaSelecioneUmRegistro();
            return;
        }

        ButtonType bot_sim = new ButtonType("Sim");
        ButtonType bot_nao = new ButtonType("Não");
        excluir = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Janela de Confirmação");
        alert.setContentText("Deseja realmente excluir o registro de código " + recebimento.getId() + " ?");
        alert.getButtonTypes().setAll(bot_nao, bot_sim);
        alert.showAndWait().ifPresent(b -> {
            if (b == bot_sim) {
                excluir = true;
            }
        });

        if (excluir) {
            boolean excluido = recebimentoDao.apagar(recebimento);
            if (excluido) {
                tab_recebimentos.getItems().remove(recebimento);
                tab_recebimentos.refresh();
            } else {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText("Janela de Erro");
                alertErro.setContentText("Erro ao tentar excluir um recebimento");
                alertErro.show();
            }
        }
    }
}
