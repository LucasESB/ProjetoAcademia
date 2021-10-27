package academia.dao;

import academia.conexao.MySql;
import academia.entidades.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
    /**
     * Instancia da classe para que não seja criada várias instancias da classe
     * acarretando a sobrecarga do servidor
     */
    private static UsuarioDao instance;

    /**
     * Objeto de conexão com o banco de dados
     */
    private MySql db;

    /**
     * Construtor privado
     */
    private UsuarioDao(){ }

    /**
     * Metodo sincronizado que retorna uma instancia da classe (Singleton) nao
     * deixando ser criado mais de uma instancia do mesmo objeto
     *
     * @return Instancia da Classe
     * @throws Exception Caso algum erro ocorra
     */
    public synchronized static UsuarioDao getInstance() throws Exception {
        if (instance == null) {
            instance = new UsuarioDao();
            instance.db = MySql.getInstance();
            instance.iniciaDaosSecundarios();
        }
        else {
            instance.disconnect();
            instance.db = MySql.getInstance();
            instance.iniciaDaosSecundarios();
        }

        return instance;
    }

    /**
     * Retorna uma nova instancia: Esse método é super recomendado para ser
     * utilizado dentro de Threads que rodam em conjunto com o resto do sistema,
     * para evitar desconexoes inesperadas.
     *
     * @return
     */
    public synchronized static UsuarioDao getNewInstance() {
        UsuarioDao dao = new UsuarioDao();
        dao.db = MySql.getInstance();
        dao.iniciaDaosSecundarios();
        return dao;
    }

    /**
     * Método que inicia os daos dependentes
     */
    private void iniciaDaosSecundarios() { }

    /**
     * Metodo responsavel por buscar um usuario pelo Login e Senha
     *
     * @param login
     * @param senha
     * @return
     * @throws Exception
     */
    public Usuario getUsuarioPorLoginSenha(String login, String senha) throws Exception{
        ResultSet resultSet = db.getResultset("SELECT * FROM usuarios WHERE login = ? AND senha = ?", login, senha);

        if(resultSet.next()){
            return getUsurioPorResultSet(resultSet);
        }

        return null;
    }

    /**
     * Metodo responsavel por retornar um Usuario apartir de um ResultSet
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Usuario getUsurioPorResultSet(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setLogin(resultSet.getString("login"));
        usuario.setNome(resultSet.getString("nome"));
        usuario.setSenha(resultSet.getString("senha"));
        usuario.setAdmin(resultSet.getBoolean("admin"));

        return usuario;
    }

    /**
     * Metodo derruba a conecxao
     *
     * @return
     * @throws Exception Caso algum erro ocorra
     */
    public boolean disconnect() throws Exception {
        return db.disconnect();
    }
}
