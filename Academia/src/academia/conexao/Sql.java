package academia.conexao;

public class Sql {
    //Alunos
    public static final String ALUNOS_INSERT = "INSERT INTO alunos (nome, telefone, email, cpf, sexo, datanascimento, datacadastro, observacao, whatsapp, diaPrePagamento) VALUES (?,?,?,?,?,?,?,?,?,?)";
    public static final String ALUNOS_UPDATE = "UPDATE alunos SET nome=?, telefone=?, email=?, cpf=?, sexo=?, datanascimento=?, datacadastro=?, observacao=?, whatsapp=?, diaPrePagamento=? WHERE id=?";
    public static final String ALUNOS_DELETE = "DELETE FROM alunos WHERE id=?";
    public static final String ALUNOS_SELCT_ALL = "SELECT * FROM alunos";

    //Recebimentos
    public static final String RECEBIMENTOS_INSERT = "INSERT INTO recebimentos (aluno_id, aluno_nome, usuario_id, usuario_nome, dataVencimento, dataPagamento, formaPagamento, vRecebimento, vDesconto, vTotal, matricula, excluido) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String RECEBIMENTOS_UPDATE = "UPDATE recebimentos  SET aluno_id=?, aluno_nome=?, usuario_id=?, usuario_nome=?, dataVencimento=?, dataPagamento=?, formaPagamento=?, vRecebimento=?, vDesconto=?, vTotal=?, matricula=? WHERE id=?";
    public static final String RECEBIMENTOS_APAGAR = "UPDATE recebimentos  SET excluido=true WHERE id=?";
    public static final String RECEBIMENTOS_SELCT_ALL = "SELECT * FROM recebimentos";

    //Turmas
    public static final String TURMAS_INSERT = "INSERT INTO turmas (descricao, vmensalidade) VALUES (?,?)";
    public static final String TURMAS_UPDATE = "UPDATE turmas SET descricao=?, vmensalidade=? WHERE id=?";
    public static final String TURMAS_DELETE = "DELETE FROM turmas WHERE id=?";
    public static final String TURMAS_SELCT_ALL = "SELECT * FROM turmas";

    //TurmasAlunos
    public static final String TURMAS_ALUNOS_INSERT = "INSERT INTO turmasalunos (turma_id, aluno_id) VALUES (?,?)";

    //Usuarios
    public static final String USUARIOS_INSERT = "INSERT INTO usuarios (login, nome, senha, admin) VALUES (?,?,?,?)";
    public static final String USUARIOS_UPDATE = "UPDATE usuarios SET login=?, nome=?, senha=?, admin=? WHERE id=?";
    public static final String USUARIOS_DELETE = "DELETE FROM usuarios WHERE id=?";
    public static final String USUARIOS_SELCT_ALL = "SELECT * FROM usuarios";
}
