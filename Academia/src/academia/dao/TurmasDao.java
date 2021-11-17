package academia.dao;

import academia.conexao.MySql;
import academia.conexao.Sql;
import academia.entidades.Alunos;
import academia.entidades.Turmas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class TurmasDao {

    /**
     * Instancia da classe para que não seja criada várias instancias da classe
     * acarretando a sobrecarga do servidor
     */
    private static TurmasDao instance;

    /**
     * Objeto de conexão com o banco de dados
     */
    private MySql db;

    /**
     * Construtor privado
     */
    private TurmasDao() {
    }

    /**
     * Metodo sincronizado que retorna uma instancia da classe (Singleton) nao
     * deixando ser criado mais de uma instancia do mesmo objeto
     *
     * @return Instancia da Classe
     * @throws Exception Caso algum erro ocorra
     */
    public synchronized static TurmasDao getInstance() throws Exception {
        if (instance == null) {
            instance = new TurmasDao();
            instance.db = MySql.getInstance();
            instance.iniciaDaosSecundarios();
        } else {
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
    public synchronized static TurmasDao getNewInstance() {
        TurmasDao dao = new TurmasDao();
        dao.db = MySql.getInstance();
        dao.iniciaDaosSecundarios();
        return dao;
    }

    /**
     * Método que inicia os daos dependentes
     */
    private void iniciaDaosSecundarios() {
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

    /////////////////////////////////
    //////// Metodos INSERT /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por inseri um novo registro
     *
     * @param turma
     * @return
     * @throws SQLException
     */
    public int inserir(Turmas turma) throws SQLException {
        Object[] dados = new Object[]{
                turma.getDescricao(),
                turma.getvMensalidade()
        };

        return db.getGenerateKey(Sql.TURMAS_INSERT, dados);
    }

    /**
     * Metodo responsavel por inserir os alunos na turma
     *
     * @param turma
     * @param listAlunos
     * @return
     * @throws SQLException
     */
    public boolean inserirAlunos(Turmas turma, List<Alunos> listAlunos) throws SQLException {
        boolean retorno = true;

        for (Alunos aluno : listAlunos) {
            try {
                if (!db.executeUpdate(Sql.TURMAS_ALUNOS_INSERT, turma.getId(), aluno.getId())) {
                    retorno = false;
                }
            } catch (SQLIntegrityConstraintViolationException ex) {

            }
        }

        return retorno;
    }

    /////////////////////////////////
    //////// Metodos UPDATE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por atualizar um registro especifico
     *
     * @param turma
     * @return
     * @throws SQLException
     */
    public boolean atualizar(Turmas turma) throws SQLException {
        Object[] dados = new Object[]{
                turma.getDescricao(),
                turma.getvMensalidade(),
                turma.getId()
        };

        return db.executeUpdate(Sql.TURMAS_UPDATE, dados);
    }

    /////////////////////////////////
    //////// Metodos DELETE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por excluir um registro especifico
     *
     * @param turma
     * @return
     * @throws SQLException
     */
    public boolean excluir(Turmas turma) throws SQLException {
        return db.executeUpdate(Sql.TURMAS_DELETE, turma.getId());
    }

    /////////////////////////////////
    ////////// Metodos GET //////////
    /////////////////////////////////

    /**
     * Metodo responsavel por retornor as turmar a partir de uma sql complementar
     *
     * @param sqlComplementar
     * @return
     * @throws SQLException
     */
    public ArrayList<Turmas> getTurmas(String sqlComplementar) throws SQLException {
        ResultSet resultSet = db.getResultset(Sql.TURMAS_SELCT_ALL.concat(" ").concat(sqlComplementar == null ? "" : sqlComplementar));
        ArrayList<Turmas> listTurmas = new ArrayList<>();

        while (resultSet.next()) {
            listTurmas.add(getTurmaPorResultSet(resultSet));
        }

        return listTurmas;
    }

    private Turmas getTurmaPorResultSet(ResultSet resultSet) throws SQLException {
        Turmas turma = new Turmas();
        turma.setId(resultSet.getInt("id"));
        turma.setDescricao(resultSet.getString("descricao"));
        turma.setvMensalidade(resultSet.getDouble("vMensalidade"));
        return turma;
    }

    public ArrayList<Alunos> getAlunosByTurma(Turmas turma) throws SQLException {
        String sql = "SELECT alunos.id, alunos.nome, alunos.telefone FROM turmasalunos INNER JOIN alunos ON alunos.id = turmasalunos.aluno_id WHERE turma_id = ?";
        ArrayList<Alunos> listAlunos = new ArrayList<>();

        ResultSet resultSet = db.getResultset(sql, turma.getId());

        while (resultSet.next()) {
            Alunos aluno = new Alunos();
            aluno.setId(resultSet.getInt("alunos.id"));
            aluno.setNome(resultSet.getString("alunos.nome"));
            aluno.setTelefone(resultSet.getString("alunos.telefone"));
            listAlunos.add(aluno);
        }

        return listAlunos;
    }

    /**
     * Metodo responsavel por retornar o valor da mensalidade de um aluno especifico
     *
     * @param aluno
     * @return
     * @throws Exception
     */
    public double getvMensalidadeByAluno(Alunos aluno) throws SQLException {
        String sql = "SELECT SUM(vMensalidade) AS 'vMensalidade'  FROM turmasalunos INNER JOIN turmas ON turmas.id = turmasalunos.turma_id WHERE turmasalunos.aluno_id = ?";

        ResultSet resultSet = db.getResultset(sql, aluno.getId());

        if (resultSet.next()) {
            return resultSet.getDouble("vMensalidade");
        }

        return 0.00;
    }
}
