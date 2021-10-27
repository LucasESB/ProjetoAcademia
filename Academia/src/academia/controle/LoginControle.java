package academia.controle;

import academia.dao.UsuarioDao;
import academia.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginControle  implements Initializable {

    /**
     * Instancia da janela
     */
    public static Stage janela;

    /**
     * Objeto de conexão com a tabela Usuario
     */
    private UsuarioDao usuarioDao;

    @FXML
    private TextField tex_login;

    @FXML
    private PasswordField tex_senha;

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
            return;
        }

        Usuario usuario = usuarioDao.getUsuarioPorLoginSenha(tex_login.getText(), tex_senha.getText());
        // TODO descriptografar senha do usuario

        if(usuario == null){
            alertaLoginOuSenhaIncorreto();
            return;
        }else if(!usuario.getLogin().equals(tex_login.getText()) || !tex_senha.getText().equals(usuario.getSenha())){
            alertaLoginOuSenhaIncorreto();
            return;
        }

        LoginControle.janela.close();
        PrincipalControle.abrirTela();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            usuarioDao = UsuarioDao.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
