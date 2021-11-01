package academia.controle;

import academia.dao.UsuarioDao;
import academia.entidades.Usuario;
import academia.utilitarios.Criptografia;
import academia.utilitarios.InstanciaSistema;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioInserirEditarControle implements Initializable {

    @FXML
    private Button bot_cancelar;

    @FXML
    private Button bot_salvar;

    @FXML
    private CheckBox chb_adm;

    @FXML
    private TextField tex_login;

    @FXML
    private TextField tex_nome;

    @FXML
    private PasswordField tex_senha;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static UsuarioInserirEditarControle instancia;

    /**
     * Objeto de conexão com a tabela usuario
     */
    private UsuarioDao usuarioDao = UsuarioDao.getInstance();

    /**
     * Armazena o objeto usuario que será retornado da tela
     */
    private Usuario objetoRetorno;

    private boolean editar;

    private Usuario usuarioEdit;

    public static Usuario showDialogInserir() throws Exception {
        UsuarioInserirEditarControle.abrirTela(null, false);
        return instancia.getObjetoRetorno();
    }

    public static void showDialogEditar(Usuario usuario) throws Exception {
        UsuarioInserirEditarControle.abrirTela(usuario, true);
    }

    public Usuario getObjetoRetorno() {
        return objetoRetorno;
    }

    public UsuarioInserirEditarControle() throws Exception { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    private void configuracoes(){
        chb_adm.setDisable(!InstanciaSistema.getUsuarioLogado().isAdmin());

        if(editar){
            setDadosUsuario();
        }

        setEventos();
    }

    private void setDadosUsuario(){
        tex_nome.setText(instancia.usuarioEdit.getNome());
        tex_senha.setText(Criptografia.decifrar(instancia.usuarioEdit.getSenha()));
        tex_login.setText(instancia.usuarioEdit.getLogin());
        chb_adm.setSelected(instancia.usuarioEdit.isAdmin());
    }

    private void setEventos(){
        bot_salvar.setOnAction(eventHandlerAction);
        bot_cancelar.setOnAction(eventHandlerAction);
    }

    /**
     * Metodo responsavel pelos eventos do componentes
     */
    EventHandler<ActionEvent> eventHandlerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if(event.getSource().equals(bot_salvar)){
                    salvar();
                } else if(event.getSource().equals(bot_cancelar)){
                    janela.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };

    private void salvar() throws Exception{
        if(isDadosUsuarioNaoValido()){
            return;
        }

        if(editar){
            editar();
        }else {
            inserir();
        }
    }

    private boolean isDadosUsuarioNaoValido(){
        StringBuilder mensagensErro = new StringBuilder();

        if(tex_nome.getText() == null || tex_nome.getText().isEmpty()){
            mensagensErro.append("Nome é de preenchimento obrigatório.\n");
        }

        if(tex_senha.getText() == null || tex_senha.getText().isEmpty()){
            mensagensErro.append("Senha é de preenchimento obrigatório.\n");
        }

        if(tex_login.getText() == null || tex_login.getText().isEmpty()){
            mensagensErro.append("Login é de preenchimento obrigatório.\n");
        }

        if(mensagensErro.length() > 0){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Alerta de Erro");
            alerta.setHeaderText("Campos Inválidos");
            alerta.setContentText(mensagensErro.toString());
            alerta.show();
            return  true;
        }

        return false;
    }

    private void editar() throws Exception {
        Usuario usuario = getDadosUsuario();
        boolean atualizado = usuarioDao.atualizar(usuario);

        if(atualizado){
            janela.close();
        }else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar atualizar um Usuário");
            alertErro.show();
        }
    }

    private Usuario getDadosUsuario(){
        Usuario usuario = null;

        if(editar){
            usuario = usuarioEdit;
        }else {
            usuario = new Usuario();
        }

        usuario.setNome(tex_nome.getText());
        usuario.setSenha(Criptografia.cifrar(tex_senha.getText()));
        usuario.setLogin(tex_login.getText());
        usuario.setAdmin(chb_adm.isSelected());
        return usuario;
    }

    private void inserir() throws Exception{
        Usuario usuario = getDadosUsuario();
        int codigo = usuarioDao.inserir(usuario);

        if(codigo > 0){
            usuario.setId(codigo);
            objetoRetorno = usuario;
            janela.close();
        }else {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro");
            alertErro.setHeaderText("Janela de Erro");
            alertErro.setContentText("Erro ao tentar inserir um Usuário");
            alertErro.show();
        }
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Usuario usuario, boolean edita) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UsuarioInserirEditarControle.class.getResource("/academia/telas/UsuarioInserirEditar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Cadastro e atualização de Usuarios");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(UsuarioInserirEditarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.usuarioEdit = usuario;
        instancia.editar = edita;
        instancia.configuracoes();

        janela.showAndWait();
    }

}
