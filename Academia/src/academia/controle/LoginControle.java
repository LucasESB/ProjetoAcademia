package academia.controle;

import academia.dao.UsuarioDao;
import academia.entidades.Usuario;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginControle  implements Initializable {

    @FXML
    private Button bot_logar;

    @FXML
    private TextField tex_login;

    @FXML
    private PasswordField tex_senha;

    /**
     * Instancia da janela
     */
    private Stage janela;

    /**
     * Objeto de conexão com a tabela Usuario
     */
    private UsuarioDao usuarioDao = UsuarioDao.getInstance();

    public LoginControle() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tex_login.setText("Master");
        tex_senha.setText("Master");
        setEventos();
    }

    private void setEventos(){
        tex_login.setOnKeyPressed(eventHandlerKey);
        tex_senha.setOnKeyPressed(eventHandlerKey);
    }

    private final EventHandler<KeyEvent> eventHandlerKey = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            try {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    if(keyEvent.getSource().equals(tex_login)) {
                        tex_senha.requestFocus();
                    }
                    else if(keyEvent.getSource().equals(tex_senha)) {
                        login();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };

    @FXML
    void sair() {
        System.exit(0);
    }

    @FXML
    void login() throws Exception {
        if(isCamposInvalidos()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta de Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Os campos Login e Senha são de preenchimento obrigatorio.");
            alert.show();

            tex_login.requestFocus();
            return;
        }

        Usuario usuario = usuarioDao.getUsuarioPorLoginSenha(tex_login.getText(), tex_senha.getText());
        // TODO descriptografar senha do usuario

        if(usuario == null){
            alertaLoginOuSenhaIncorreto();
            tex_senha.requestFocus();
            return;
        }else if(!usuario.getLogin().equals(tex_login.getText()) || !tex_senha.getText().equals(usuario.getSenha())){
            alertaLoginOuSenhaIncorreto();
            tex_senha.requestFocus();
            return;
        }

        janela.close();
        PrincipalControle.abrirTela();
    }

    /**
     * Caso algum dos campos não seja preenchido retorna true
     *
     * @return
     */
    private boolean isCamposInvalidos() {
        if(tex_login.getText() == null || tex_login.getText().isEmpty()){
            return true;
        }
        else if(tex_senha.getText() == null || tex_senha.getText().isEmpty()){
            return true;
        }

        return false;
    }

    private void alertaLoginOuSenhaIncorreto(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerta de Erro");
        alert.setHeaderText("Erro");
        alert.setContentText("Login ou Senha incorreto.");
        alert.show();
    }

    public void setJanela(Stage janela) {
        this.janela = janela;
    }
}
