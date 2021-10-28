package academia.conexao;

import academia.utilitarios.Criptografia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.*;

public class MySql {
    /**
     * Driver de MySQL com o Banco de dados
     */
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Informa o nome da base de dados a ser conectada
     */
    private String dataBase;

    /**
     * Informa o endereco do servidor de dados a ser conectado
     */
    private String host;

    /**
     * porta de conexao com o servidor
     */
    private String port;

    /**
     * informa o usuario do banco de dados
     */
    private String user;

    /**
     * informa senha do usuario do banco de dados
     */
    private String password;

    /**
     * URL do banco de dados
     */
    private String URL;

    /**
     * Instancia da classe para que não seja criada várias instancias da classe
     * acarretando a sobrecarga do servidor
     */
    private static MySql instancia;

    /**
     * Objeto que se encarrega da conn com o banco de dados
     */
    private Connection conn;

    /**
     * Preparador e executador das instruções SQL
     */
    private PreparedStatement preparedStatement;

    /**
     * Objeto que armazena os resultados de um comando select no Banco de dados
     */
    public ResultSet resultset;

    /**
     * Construtor privado
     */
    private MySql(){ setConexao();}

    /**
     * Metodo sincronizado que retorna uma instancia da classe MySQL (Singleton)
     * nao deixando ser criado mais de uma instancia do mesmo objeto
     *
     * @return Instancia da Classe MySQL
     * @throws Exception
     */
    public synchronized static MySql getInstance() {
        if (instancia == null) {
            instancia = new MySql();
        }
        else {
            instancia.disconnect();
            instancia = new MySql();
        }

        return instancia;
    }

    /**
     * Metodo que retorna os dados do servidor dentro do arquivo db.ini sendo
     * que os dados serão referentes a primeira linha
     *
     */
    public void setConexao() {
        try {
            String path = MySql.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring (0, path.lastIndexOf ("/") + 1);
            String caminhoArquivoConfig = URLDecoder.decode(path, "UTF-8");
            caminhoArquivoConfig = caminhoArquivoConfig.concat("../config/db.ini");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoArquivoConfig), "UTF-8"));
            String linha = reader.readLine();

            if (linha != null) {
                String dados[] = linha.split(";");

                this.setDataBase(dados[0]);
                this.setHost(dados[1]);
                this.setPort(dados[2]);
                this.setUser(dados[3]);
                this.setPassword(Criptografia.decifrar(dados[4]));
            }

            reader.close();
        }
        catch (IOException ex) {
//            TODO adicionar codigo para gravar log nos souts
            System.out.println(ex);
        }
    }

    /**
     * Metodo que conecta ao banco de dados
     *
     * @return boolean
     */
    public boolean connect() {
        try {
            if (conn != null && conn.isValid(3)) {
                return true;
            }

            Class.forName(DRIVER);

            try {
                conn = DriverManager.getConnection("jdbc:mysql://".concat(host).concat(":").concat(port).concat("/").concat(dataBase)
                        .concat("?characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=America/Sao_Paulo&user=").concat(user)
                        .concat("&password=").concat(password));

                conn.setAutoCommit(true);

                return true;
            }
            catch (SQLException ex) {
                return false;
            }
        }
        catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
    }

    /**
     * Retorna PreparedStatment com o comando SQL
     *
     * @param SQL String contendo o comando SQL
     *
     * @return PreparedStatment Declaracao da SQL preparada para execucao
     *
     * @throws Exception Caso ocorra um erro
     */
    public PreparedStatement getPrepareStatement(String SQL) throws SQLException {
        return conn.prepareStatement(SQL, java.sql.Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Executa querys (SELECT)
     *
     * @param SQL String com o comando SQL
     *
     * @return boolean <blockquote>true = Se executar a SQL</blockquote>
     * <blockquote>false = Se a execusao falhar</blockquote>
     * @throws java.sql.SQLException
     */
    public boolean executeQuery(String SQL) throws SQLException {
        if (connect()) {
            preparedStatement = getPrepareStatement(SQL);

            //Executa query e armazena o resultador no resultser
            resultset = preparedStatement.executeQuery();

            return true;
        }

        return false;
    }

    /**
     * Executa querys (SELECT) adicionando valores complementares
     *
     * @param SQL String com o comando SQL
     *
     * @param valores Object[] Array com os valores a serem adicionados
     *
     * @return boolean <blockquote>true = Se executar a SQL</blockquote>
     * <blockquote>false = Se a execusao falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public boolean executeQuery(String SQL, Object... valores) throws SQLException {
        if (connect()) {
            preparedStatement = getPrepareStatement(SQL);

            //Adiciona os valores no PreparedStatement
            if (valores != null) {
                for (int i = 0; i < valores.length; i++) {
                    preparedStatement.setObject((i + 1), valores[i]);
                }
            }

            //Executa a query
            resultset = preparedStatement.executeQuery();

            return true;
        }

        return false;
    }

    /**
     * Executa comandos SQL como INSERT, UPDATE, DELETE e outros
     *
     * @param SQL String com o comando SQL
     *
     * @return boolean <blockquote>true = Se executar a SQL</blockquote>
     * <blockquote>false = Se a execusao falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public boolean executeUpdate(String SQL) throws SQLException {
        try {
            if (connect()) {
                preparedStatement = getPrepareStatement(SQL);

                //Executa
                preparedStatement.executeUpdate();

                return true;
            }

            return false;
        }
        catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Executa comandos SQL como INSERT, UPDATE, DELETE e outros adicionando
     * valores complementares no comando SQL
     *
     * @param SQL String com o comando SQL
     *
     * @param valores Object[] Array de valores a serem adicionados
     *
     * @return boolean <blockquote>true = Se executar a SQL</blockquote>
     * <blockquote>false = Se a execusao falhar</blockquote>
     * @throws java.sql.SQLException
     */
    public boolean executeUpdate(String SQL, Object... valores) throws SQLException {
        try {
            if (connect()) {
                preparedStatement = getPrepareStatement(SQL);

                //Adiciona valores no PreparedStatement
                if (valores != null) {
                    for (int i = 0; i < valores.length; i++) {
                        preparedStatement.setObject((i + 1), valores[i]);
                    }
                }

                //Executa
                preparedStatement.executeUpdate();

                return true;
            }

            return false;
        }
        catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Executa comando SQL tipicamente como INSERT e retorna a chave gerada (id
     * auto_increment) pela operacao
     *
     * @param SQL String com o comando SQL de insercao
     *
     * @return int <blockquote>chave gerado na insercao</blockquote>
     * <blockquote>-1 caso a insercao falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public int getGenerateKey(String SQL) throws SQLException {
        try {
            if (connect()) {
                preparedStatement = getPrepareStatement(SQL);

                //Executa
                preparedStatement.executeUpdate();

                //Retorna Chave gerada
                java.sql.ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    return rs.getInt(1);
                }
                else {
                    return -1;
                }
            }

            return -1;
        }
        catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Executa comando SQL tipicamente como INSERT adicionando valores
     * complementares e retorna a chave gerada (id) pela operacao
     *
     * @param SQL String com o comando SQL de insercao
     *
     * @param valores Object[] array com os valores a serem adicionados
     *
     * @return int <blockquote>chave gerado na insercao</blockquote>
     * <blockquote>-1 caso a insercao falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public int getGenerateKey(String SQL, Object... valores) throws SQLException {
        try {
            if (connect()) {
                preparedStatement = getPrepareStatement(SQL);

                //Adiciona valores no PreparedStatement
                if (valores != null) {
                    for (int i = 0; i < valores.length; i++) {
                        preparedStatement.setObject((i + 1), valores[i]);
                    }
                }

                //Executa
                preparedStatement.executeUpdate();

                //Retorna Chave gerada
                java.sql.ResultSet rs = preparedStatement.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    return rs.getInt(1);
                }
                else {
                    return -1;
                }
            }
            return -1;
        }
        catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Executa SQL tipicamente um comando SELECT e retorna o resultset produzido
     *
     * @param SQL String com o comando SQL
     *
     * @return ResultSet <blockquote>Retorna resultset se executar
     * SQL</blockquote>
     * <blockquote>Retorna null a execucao SQL falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public ResultSet getResultset(String SQL) throws SQLException {
        if (connect()) {
            preparedStatement = getPrepareStatement(SQL);

            //Executa query
            return preparedStatement.executeQuery();
        }
        return null;
    }

    /**
     * Executa query SQL tipicamente um comando SELECT adicionando valores
     * complementares na query e retorna o resultset produzido
     *
     * @param SQL String com o comando SQL
     *
     * @param valores Object[] Array de valores a serem adicionados
     *
     * @return ResultSet <blockquote>Retorna resultset se executar
     * SQL</blockquote>
     * <blockquote>Retorna null a execucao SQL falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public ResultSet getResultset(String SQL, Object... valores) throws SQLException {
        if (connect()) {
            preparedStatement = getPrepareStatement(SQL);

            //Adiciona valores no PreparedStatement
            if (valores != null) {
                for (int i = 0; i < valores.length; i++) {
                    preparedStatement.setObject((i + 1), valores[i]);
                }
            }

            return preparedStatement.executeQuery();
        }
        return null;
    }

    /**
     * Desconecta ao banco de dados
     *
     * @return boolean <blockquote>true = Se disconnect normalmente</blockquote>
     * <blockquote>false = Se o comando falhar</blockquote>
     *
     * @throws Exception Caso ocorra um erro
     */
    public boolean disconnect() {
        try {
            if (resultset != null) {
                if (!resultset.isClosed()) {
                    resultset.close();
                }

                resultset = null;
            }

            if (preparedStatement != null) {
                preparedStatement.clearParameters();

                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }

                preparedStatement = null;
            }

            if (conn != null) {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            }

            conn = null;

            return true;
        }
        catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Informa o nome da base de dados a ser conectada
     *
     * @return
     */
    public String getDataBase() {
        return dataBase;
    }

    /**
     * Informa o nome da base de dados a ser conectada
     *
     * @param dataBase
     */
    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Informa o endereco do servidor de dados a ser conectado
     *
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * Informa o endereco do servidor de dados a ser conectado
     *
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * porta de conexao com o servidor
     *
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * porta de conexao com o servidor
     *
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * informa o usuario do banco de dados
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * informa o usuario do banco de dados
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * informa senha do usuario do banco de dados
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * informa senha do usuario do banco de dados
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
