package academia.dao;

import academia.conexao.MySql;
import academia.conexao.Sql;
import academia.entidades.Alunos;
import academia.utilitarios.DataHora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlunosDao {

    /**
     * Instancia da classe para que não seja criada várias instancias da classe
     * acarretando a sobrecarga do servidor
     */
    private static AlunosDao instance;

    /**
     * Objeto de conexão com o banco de dados
     */
    private MySql db;

    /**
     * Construtor privado
     */
    private AlunosDao() {
    }

    /**
     * Metodo sincronizado que retorna uma instancia da classe (Singleton) nao
     * deixando ser criado mais de uma instancia do mesmo objeto
     *
     * @return Instancia da Classe
     * @throws Exception Caso algum erro ocorra
     */
    public synchronized static AlunosDao getInstance() throws Exception {
        if (instance == null) {
            instance = new AlunosDao();
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
    public synchronized static AlunosDao getNewInstance() {
        AlunosDao dao = new AlunosDao();
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
     * Metodo responsavel pela inserção de um novo Aluno
     *
     * @param alunos
     * @return
     * @throws SQLException
     */
    public int inserir(Alunos alunos) throws SQLException {
        Object[] dados = new Object[]{
                alunos.getNome(),
                alunos.getTelefone(),
                alunos.getEmail(),
                alunos.getCpf(),
                alunos.getSexo(),
                alunos.getDataNascimento(),
                alunos.getDataCadastro(),
                alunos.getObservacao(),
                alunos.getWhatsapp(),
                alunos.getDiaPrePagamento()
        };

        return db.getGenerateKey(Sql.ALUNOS_INSERT, dados);
    }

    /////////////////////////////////
    //////// Metodos UPDATE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por atualizar os dados do Aluno
     *
     * @param alunos
     * @return
     * @throws SQLException
     */
    public boolean atualizar(Alunos alunos) throws SQLException {
        Object[] dados = new Object[]{
                alunos.getNome(),
                alunos.getTelefone(),
                alunos.getEmail(),
                alunos.getCpf(),
                alunos.getSexo(),
                alunos.getDataNascimento(),
                alunos.getDataCadastro(),
                alunos.getObservacao(),
                alunos.getWhatsapp(),
                alunos.getDiaPrePagamento(),
                alunos.getId()
        };

        return db.executeUpdate(Sql.ALUNOS_UPDATE, dados);
    }

    /////////////////////////////////
    //////// Metodos DELETE /////////
    /////////////////////////////////

    public boolean excluir(Alunos alunos) throws SQLException {
        return db.executeUpdate(Sql.ALUNOS_DELETE, alunos.getId());
    }

    /////////////////////////////////
    ////////// Metodos GET //////////
    /////////////////////////////////

    public ArrayList<Alunos> getAlunos(String sqlComplementar) throws SQLException {
        ResultSet resultSet = db.getResultset(Sql.ALUNOS_SELCT_ALL.concat(" ").concat(sqlComplementar == null ? "" : sqlComplementar));
        ArrayList<Alunos> listAlunos = new ArrayList<>();

        while (resultSet.next()) {
            listAlunos.add(getAlunoPorResultSet(resultSet));
        }

        return listAlunos;
    }

    private Alunos getAlunoPorResultSet(ResultSet resultSet) throws SQLException {
        Alunos aluno = new Alunos();
        aluno.setId(resultSet.getInt("id"));
        aluno.setNome(resultSet.getString("nome"));
        aluno.setTelefone(resultSet.getString("telefone"));
        aluno.setEmail(resultSet.getString("email"));
        aluno.setCpf(resultSet.getString("cpf"));
        aluno.setSexo(resultSet.getString("sexo"));
        aluno.setDataNascimento(resultSet.getDate("datanascimento"));
        aluno.setDataCadastro(resultSet.getDate("datacadastro"));
        aluno.setObservacao(resultSet.getString("observacao"));
        aluno.setWhatsapp(resultSet.getString("whatsapp"));
        aluno.setDiaPrePagamento(resultSet.getInt("diaPrePagamento"));

        return aluno;
    }

    public double getvMensalidade(Alunos aluno) throws SQLException {
        String sql = "SELECT SUM(vMensalidade) as 'vMensalidade' FROM turmas INNER JOIN turmasalunos ON (turmasalunos.turma_id, turmasalunos.aluno_id) = (turmas.id, ?)";

        ResultSet resultSet = db.getResultset(sql, aluno.getId());

        if (resultSet.next()) return resultSet.getDouble("vMensalidade");

        return 0.00;
    }

    public int getQtdNovosAlunos(Date periodo) throws SQLException {
        String sql = "SELECT COUNT(*) AS 'qtd' FROM alunos WHERE ";
        Calendar cal = Calendar.getInstance();
        cal.setTime(periodo);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        String data = DataHora.formatarData(cal.getTime(), "yyyy-MM-dd");
        sql += "datacadastro >= '" + data + " 00:00:00' ";


        cal.set(Calendar.DAY_OF_MONTH, DataHora.getUltimoDiaDoMes(cal.getTime()));
        data = DataHora.formatarData(cal.getTime(), "yyyy-MM-dd");
        sql += "AND datacadastro <= '" + data + " 23:59:59'";

        ResultSet resultSet = db.getResultset(sql);

        if (resultSet.next()) return resultSet.getInt("qtd");

        return 0;
    }

    public int getQtdAlunosAtivos() throws SQLException {
        ResultSet resultSet = db.getResultset("SELECT COUNT(DISTINCT aluno_id) AS 'qtd' FROM turmasalunos");

        if(resultSet.next()) return resultSet.getInt("qtd");

        return 0;
    }
}
