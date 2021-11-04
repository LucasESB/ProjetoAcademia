package academia.controle;

import academia.entidades.Alunos;
import academia.utilitarios.DataHora;
import academia.utilitarios.Textos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlunoVisualizarControle implements Initializable {

    @FXML
    private TextField tex_cpf;

    @FXML
    private TextField tex_dataCadastro;

    @FXML
    private TextField tex_dataNascimento;

    @FXML
    private TextField tex_email;

    @FXML
    private TextField tex_nome;

    @FXML
    private TextArea tex_observacoes;

    @FXML
    private TextField tex_sexo;

    @FXML
    private TextField tex_telefone;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static AlunoVisualizarControle instancia;

    private Alunos aluno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void configuracoes() {
        setDados();
    }

    private void setDados() {
        tex_nome.setText(aluno.getNome());
        tex_dataCadastro.setText(DataHora.formatarData(aluno.getDataCadastro(), "dd/MM/yyyy"));
        tex_cpf.setText(Textos.formatarCPF(aluno.getCpf()));
        tex_dataNascimento.setText(DataHora.formatarData(aluno.getDataNascimento(), "dd/MM/yyyy"));
        tex_telefone.setText(Textos.formatarTelefone(aluno.getTelefone()));
        tex_sexo.setText(aluno.getSexoDescricao());
        tex_email.setText(aluno.getEmail());
        tex_observacoes.setText(aluno.getObservacao());
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Alunos aluno) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AlunoVisualizarControle.class.getResource("/academia/telas/AlunoVisualizar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Dados do Aluno");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(AlunoVisualizarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.aluno = aluno;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
