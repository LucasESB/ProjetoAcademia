package academia.conexao;

public class Sql {
    //Usuarios
    public static final String USUARIOS_INSERT = "INSERT INTO usuarios (login, nome, senha, admin) VALUES (?,?,?,?)";
    public static final String USUARIOS_UPDATE = "UPDATE usuarios SET login=?, nome=?, senha=?, admin=? WHERE id=?";
    public static final String USUARIOS_DELETE = "DELETE FROM usuarios WHERE id=?";
    public static final String USUARIOS_SELCT_ALL = "SELECT * FROM usuarios";
}
