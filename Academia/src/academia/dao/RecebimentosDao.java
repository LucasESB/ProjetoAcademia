package academia.dao;

import academia.bean.FormaPagamentoBean;
import academia.conexao.MySql;
import academia.conexao.Sql;
import academia.entidades.Alunos;
import academia.entidades.Recebimentos;
import academia.entidades.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecebimentosDao {
    /**
     * Instancia da classe para que não seja criada várias instancias da classe
     * acarretando a sobrecarga do servidor
     */
    private static RecebimentosDao instance;

    /**
     * Objeto de conexão com o banco de dados
     */
    private MySql db;

    /**
     * Construtor privado
     */
    private RecebimentosDao() {
    }

    /**
     * Metodo sincronizado que retorna uma instancia da classe (Singleton) nao
     * deixando ser criado mais de uma instancia do mesmo objeto
     *
     * @return Instancia da Classe
     * @throws Exception Caso algum erro ocorra
     */
    public synchronized static RecebimentosDao getInstance() throws Exception {
        if (instance == null) {
            instance = new RecebimentosDao();
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
    public synchronized static RecebimentosDao getNewInstance() {
        RecebimentosDao dao = new RecebimentosDao();
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
     * @param recebimento
     * @return
     * @throws SQLException
     */
    public int inserir(Recebimentos recebimento) throws SQLException {
        Object[] dados = new Object[]{
                recebimento.getAluno().getId(),
                recebimento.getAluno().getNome(),
                recebimento.getUsuario().getId(),
                recebimento.getUsuario().getNome(),
                recebimento.getDataVencimento(),
                recebimento.getDataPagamento(),
                recebimento.getFormaPagamento().getCodigo(),
                recebimento.getvRecebimento(),
                recebimento.getvDesconto(),
                recebimento.getvTotal(),
                recebimento.isMatricula(),
                recebimento.isExcluido()
        };

        return db.getGenerateKey(Sql.RECEBIMENTOS_INSERT, dados);
    }

    /////////////////////////////////
    //////// Metodos UPDATE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por atualizar um registro
     *
     * @param recebimento
     * @return
     * @throws SQLException
     */
    public boolean atualizar(Recebimentos recebimento) throws SQLException {
        Object[] dados = new Object[]{
                recebimento.getUsuarioEdicao().getId(),
                recebimento.getUsuarioEdicao().getNome(),
                recebimento.getDataVencimento(),
                recebimento.getDataPagamento(),
                recebimento.getDataEdicao(),
                recebimento.getFormaPagamento().getCodigo(),
                recebimento.isMatricula(),
                recebimento.getId()
        };

        return db.executeUpdate(Sql.RECEBIMENTOS_UPDATE, dados);
    }

    /////////////////////////////////
    //////// Metodos DELETE /////////
    /////////////////////////////////

    /**
     * Metodo responsavel por marcar um registro especifico como excluido
     *
     * @param recebimento
     * @return
     * @throws SQLException
     */
    public boolean apagar(Recebimentos recebimento) throws SQLException {
        return db.executeUpdate(Sql.RECEBIMENTOS_APAGAR, recebimento.getId());
    }

    /////////////////////////////////
    ////////// Metodos GET //////////
    /////////////////////////////////

    /**
     * Metodo responsavel por buscar os recebimentos a partir de uma sql complementar
     *
     * @param sqlComplementar
     * @return
     * @throws SQLException
     */
    public ArrayList<Recebimentos> getRecebimentos(String sqlComplementar) throws SQLException {
        ResultSet resultSet = db.getResultset(Sql.RECEBIMENTOS_SELCT_ALL.concat(" ").concat(sqlComplementar == null ? "" : sqlComplementar));
        ArrayList<Recebimentos> listRecebimentos = new ArrayList<>();

        while (resultSet.next()) {
            listRecebimentos.add(getRecebimentoPorResultSet(resultSet));
        }

        return listRecebimentos;
    }

    /**
     * Metodo responsavel por retornar um Recebimento a partir de um resultSet
     *
     * @param resultSet
     * @return
     */
    private Recebimentos getRecebimentoPorResultSet(ResultSet resultSet) throws SQLException {
        Recebimentos recebimento = new Recebimentos();
        recebimento.setId(resultSet.getInt("id"));
        recebimento.setAluno(new Alunos(resultSet.getInt("aluno_id"), resultSet.getString("aluno_nome")));
        recebimento.setUsuario(new Usuario(resultSet.getInt("usuario_id"), resultSet.getString("usuario_nome")));

        if (resultSet.getInt("usuarioEdicao_id") > 0) {
            recebimento.setUsuarioEdicao(new Usuario(resultSet.getInt("usuarioEdicao_id"), resultSet.getString("usuarioEdicao_nome")));
        }

        recebimento.setDataVencimento(resultSet.getDate("dataVencimento"));
        recebimento.setDataPagamento(resultSet.getDate("dataPagamento"));
        recebimento.setDataEdicao(resultSet.getDate("dataEdicao"));
        recebimento.setFormaPagamento(new FormaPagamentoBean(resultSet.getString("formaPagamento")));
        recebimento.setvRecebimento(resultSet.getDouble("vRecebimento"));
        recebimento.setvDesconto(resultSet.getDouble("vDesconto"));
        recebimento.setvTotal(resultSet.getDouble("vTotal"));
        recebimento.setMatricula(resultSet.getBoolean("matricula"));
        recebimento.setExcluido(resultSet.getBoolean("excluido"));

        return recebimento;
    }
}
