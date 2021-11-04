package academia.entidades;

import academia.utilitarios.Textos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alunos {

    /**
     * Armazena o codigo sequencial do aluno
     *
     * <br> Tipo BD....: MEDIUMINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private int id;

    /**
     * Informa o nome do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     */
    private String nome;

    /**
     * Informa o telefone do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 16
     * <br> Enum.......: -
     */
    private String telefone;

    /**
     * Informa o email do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     */
    private String email;

    /**
     * Informa o cpf do usuario
     *
     * <br> Tipo BD....: CHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 11
     * <br> Enum.......: -
     */
    private String cpf;

    /**
     * Informa o sexo do usuario M - Masculino F - Feminino
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('M', 'F')
     */
    private String sexo;

    /**
     * Informa o data de nascimento do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Date dataNascimento;

    /**
     * Informa o data de cadastro do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Date dataCadastro;

    /**
     * Informa uma observação sobre o usuario
     *
     * <br> Tipo BD....: TEXT
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private String observacao;

    public Alunos() {
    }

    /**
     * Armazena o codigo sequencial do aluno
     *
     * <br> Tipo BD....: MEDIUMINT
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
     * Armazena o codigo sequencial do aluno
     *
     * <br> Tipo BD....: MEDIUMINT
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
     * Informa o nome do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
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
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Informa o telefone do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 16
     * <br> Enum.......: -
     *
     * @return
     */
    public String getTelefone() {
        return telefone;
    }

    public String getTelefoneFormatado() {
        return Textos.formatarTelefone(telefone);
    }

    /**
     * Informa o telefone do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 16
     * <br> Enum.......: -
     *
     * @param telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Informa o email do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Informa o email do usuario
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Informa o cpf do usuario
     *
     * <br> Tipo BD....: CHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 11
     * <br> Enum.......: -
     *
     * @return
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Informa o cpf do usuario
     *
     * <br> Tipo BD....: CHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 11
     * <br> Enum.......: -
     *
     * @param cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Informa o sexo do usuario M - Masculino F - Feminino
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('M', 'F')
     *
     * @return
     */
    public String getSexo() {
        return sexo;
    }

    public String getSexoDescricao() {
        if(sexo.equalsIgnoreCase("F")) return "Feminino";
        else return "Masculino";
    }

    public static List<String> getListSexo(){
        List<String> listSexo = new ArrayList<>();
        listSexo.add("Feminino");
        listSexo.add("Masculino");
        return  listSexo;
    }

    /**
     * Informa o sexo do usuario M - Masculino F - Feminino
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('M', 'F')
     *
     * @param sexo
     */
    public void setSexo(String sexo) {
        if (sexo.equalsIgnoreCase("masculino")) this.sexo = "M";
        else if (sexo.equalsIgnoreCase("feminino")) this.sexo = "F";
        else this.sexo = sexo;
    }

    /**
     * Informa o data de nascimento do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Informa o data de nascimento do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param dataNascimento
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * * Informa o data de cadastro do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * * Informa o data de cadastro do usuario
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param dataCadastro
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * * Informa uma observação sobre o usuario
     *
     * <br> Tipo BD....: TEXT
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * * Informa uma observação sobre o usuario
     *
     * <br> Tipo BD....: TEXT
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param observacao
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
