package academia.entidades;

public class Usuario {

    /**
     * Armazena o codigo sequencial do usuario
     *
     * <br> Tipo BD....: SMALLINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private int id;

    /**
     * Informa o login de acesso do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 120
     * <br> Enum.......: -
     */
    private String login;

    /**
     * Informa o nome do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 30
     * <br> Enum.......: -
     */
    private String nome;

    /**
     * Armazena a senha criptografada do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 32
     * <br> Enum.......: -
     */
    private String senha;

    /**
     * Informa se o usuario é um administrador
     *
     * <br> Tipo BD....: BIT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     */
    private boolean admin;

    /**
     * Construtor
     */
    public Usuario() {
    }

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Armazena o codigo sequencial do usuario
     *
     * <br> Tipo BD....: SMALLINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Armazena o codigo sequencial do usuario
     *
     * <br> Tipo BD....: SMALLINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Informa o login de acesso do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 120
     * <br> Enum.......: -
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     * Informa o login de acesso do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 120
     * <br> Enum.......: -
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Informa o nome do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 30
     * <br> Enum.......: -
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Informa o nome do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 30
     * <br> Enum.......: -
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Armazena a senha criptografada do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 32
     * <br> Enum.......: -
     *
     * @return
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Armazena a senha criptografada do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 32
     * <br> Enum.......: -
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Informa se o usuario é um administrador
     *
     * <br> Tipo BD....: BIT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @return
     */
    public boolean isAdmin() {
        return admin;
    }

    public String getAdmDescricao() {
        return admin ? "Sim" : "Não";
    }

    /**
     * Informa se o usuario é um administrador
     *
     * <br> Tipo BD....: BIT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
