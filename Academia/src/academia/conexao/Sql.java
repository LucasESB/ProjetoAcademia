package academia.conexao;

public class Sql {
    //Alunos
    public static final String ALUNOS_INSERT = "INSERT INTO alunos (nome, telefone, email, cpf, sexo, datanascimento, datacadastro, observacao) VALUES (?,?,?,?,?,?,?,?)";
    public static final String ALUNOS_UPDATE = "UPDATE alunos SET nome=?, telefone=?, email=?, cpf=?, sexo=?, datanascimento=?, datacadastro=?, observacao=? WHERE id=?";
    public static final String ALUNOS_DELETE = "DELETE FROM alunos WHERE id=?";
    public static final String ALUNOS_SELCT_ALL = "SELECT * FROM alunos";

    //Usuarios
    public static final String USUARIOS_INSERT = "INSERT INTO usuarios (login, nome, senha, admin) VALUES (?,?,?,?)";
    public static final String USUARIOS_UPDATE = "UPDATE usuarios SET login=?, nome=?, senha=?, admin=? WHERE id=?";
    public static final String USUARIOS_DELETE = "DELETE FROM usuarios WHERE id=?";
    public static final String USUARIOS_SELCT_ALL = "SELECT * FROM usuarios";
}
