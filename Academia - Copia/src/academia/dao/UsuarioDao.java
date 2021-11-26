package academia.dao;

import academia.conexao.MySql;
import academia.conexao.Sql;
import academia.entidades.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * Metodo derruba a conecxao
     *
     * @return
     * @throws Exception Caso algum erro ocorra
     */
    public boolean disconnect() throws Exception {
        return db.disconnect();
    }

    /////////////////////////////////
    //////// Metodos INSERT /////////
    /////////////////////////////////

    /**
     * Metodo responsavel pela inserção de um novo usuario
     *
     * @param usuario
     * @return
     * @throws SQLException
     */
    public int inserir(Usuario usuario) throws SQLException {
        Object[] dados = new Object[]{
            usuario.getLogin(),
            usuario.getNome(),
            usuario.getSenha(),
            usuario.isAdmin()
        };

        return db.getGenerateKey(Sql.USUARIOS_INSERT, dados);
    }

    /////////////////////////////////
    //////// Metodos UPDATE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por atualuzar os dados do Usuario
     *
     * @param usuario
     * @return
     * @throws SQLException
     */
    public boolean atualizar(Usuario usuario) throws SQLException {
        Object[] dados = new Object[]{
                usuario.getLogin(),
                usuario.getNome(),
                usuario.getSenha(),
                usuario.isAdmin(),
                usuario.getId()
        };

        return db.executeUpdate(Sql.USUARIOS_UPDATE, dados);
    }

    /////////////////////////////////
    //////// Metodos DELETE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por excluir um usuario especifico
     *
     * @param usuario
     * @return
     * @throws SQLException
     */
    public boolean excluir(Usuario usuario) throws SQLException {
        return db.executeUpdate(Sql.USUARIOS_DELETE, usuario.getId());
    }

    /////////////////////////////////
    ////////// Metodos GET //////////
    /////////////////////////////////

    /**
     * Metodo responsavel por busacar todos os usuarios
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Usuario> getUsuarios() throws SQLException{
        return getUsuarios(null);
    }

    /**
     * Metodo responsacel por buscar os usuarios a partir de uma sql complementar
     *
     * @param sqlComplementar
     * @return
     * @throws SQLException
     */
    public ArrayList<Usuario> getUsuarios(String sqlComplementar) throws SQLException{
        ResultSet resultSet = db.getResultset(Sql.USUARIOS_SELCT_ALL.concat(" ").concat(sqlComplementar == null ? "" : sqlComplementar));
        ArrayList<Usuario> listUsuarios = new ArrayList<>();

        while (resultSet.next()){
            listUsuarios.add(getUsurioPorResultSet(resultSet));
        }

        return listUsuarios;
    }

    /**
     * Metodo responsavel por buscar um usuario pelo Login e Senha
     *
     * @param login
     * @param senha
     * @return
     * @throws Exception
     */
    public Usuario getUsuarioPorLoginSenha(String login, String senha) throws Exception{
        ResultSet resultSet = db.getResultset(Sql.USUARIOS_SELCT_ALL.concat(" WHERE login = ? AND senha = ?"), login, senha);

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
}
