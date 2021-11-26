package academia.entidades;

import academia.utilitarios.Decimal;

public class Turmas {

    /**
     * Armazena o codigo sequencial da turma
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private int id;

    /**
     * Informa a descrição da turma
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     */
    private String descricao;

    /**
     * Informa o valor do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     */
    private double vMensalidade;

    public Turmas() {
    }

    /**
     * Armazena o codigo sequencial da turma
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
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
     * Armazena o codigo sequencial da turma
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
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
     * Informa a descrição da turma
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     *
     * @return
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Informa a descrição da turma
     *
     * <br> Tipo BD....: VARCHAR
     * <br> Obrigatorio: true
     * <br> Tamanho....: 60
     * <br> Enum.......: -
     *
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Informa o valor do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @return
     */
    public double getvMensalidade() {
        return vMensalidade;
    }

    public String getMensalidade() {
        return Decimal.formatar(vMensalidade, "#,##0.00");
    }

    /**
     * Informa o valor do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @param vMensalidade
     */
    public void setvMensalidade(double vMensalidade) {
        this.vMensalidade = vMensalidade;
    }
}
