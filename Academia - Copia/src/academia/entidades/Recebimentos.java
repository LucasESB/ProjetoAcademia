package academia.entidades;

import academia.bean.FormaPagamentoBean;
import academia.utilitarios.DataHora;
import academia.utilitarios.Decimal;

import java.util.Date;

public class Recebimentos implements Cloneable {
    /**
     * Armazena o codigo sequencial do recebimento
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private int id;

    /**
     * Armazena o id do aluno
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Alunos aluno;

    /**
     * Armazena o id do usuario que inseriu o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Usuario usuario;

    /**
     * Armazena o id do ultimo usuario que editou o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Usuario usuarioEdicao;

    /**
     * Informa a data de vencimento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Date dataVencimento;

    /**
     * Informa a data de pagamento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Date dataPagamento;

    /**
     * Informa a data da ultima edição feita no recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     */
    private Date dataEdicao;

    /**
     * Informa a forma de pagamento utilizada sendo:
     *
     * <br> 1 - Dinheiro
     * <br> 2 - PIX
     * <br> 3 - Transferência
     * <br> 4 - Cartão de Crédito
     * <br> 5 - Cartão de Débito
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('1', '2', '3', '4', '5')
     */
    private FormaPagamentoBean formaPagamento;

    /**
     * Informa o valor do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     */
    private double vRecebimento;

    /**
     * Informa o valor do desconto aplicado no recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     */
    private double vDesconto;

    /**
     * Informa o valor total do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     */
    private double vTotal;

    /**
     * Informa se o recebimento é da maatriculo
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     */
    private boolean matricula;

    /**
     * Informa se o recebimento está marcado como excluido
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     */
    private boolean excluido;

    /**
     * Construtor
     */
    public Recebimentos() {
    }

    @Override
    public Recebimentos clone() throws CloneNotSupportedException {
        return (Recebimentos) super.clone();
    }

    /**
     * Armazena o codigo sequencial do recebimento
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
     * Armazena o codigo sequencial do recebimento
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
     * Armazena o id do aluno
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Alunos getAluno() {
        return aluno;
    }

    public String getNomeAluno() {
        return aluno.getNome();
    }

    /**
     * Armazena o id do aluno
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param aluno
     */
    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    /**
     * Armazena o id do usuario que inseriu o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    public String getNomeUsuario() {
        return usuario.getNome();
    }

    /**
     * Armazena o id do usuario que inseriu o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Armazena o id do ultimo usuario que editou o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Usuario getUsuarioEdicao() {
        return usuarioEdicao;
    }

    /**
     * Armazena o id do ultimo usuario que editou o registro
     *
     * <br> Tipo BD....: MEDIUMINT UNSIGNED
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param usuarioEdicao
     */
    public void setUsuarioEdicao(Usuario usuarioEdicao) {
        this.usuarioEdicao = usuarioEdicao;
    }

    /**
     * Informa a data de vencimento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Date getDataVencimento() {
        return dataVencimento;
    }

    public String getDataVencimentoFormatado() {
        return dataVencimento == null ? null : DataHora.formatarData(dataVencimento, "dd/MM/yyyy");
    }

    /**
     * Informa a data de vencimento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param dataVencimento
     */
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * * Informa a data de pagamento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getDataPagamentoFormatado() {
        return dataPagamento == null ? null : DataHora.formatarData(dataPagamento, "dd/MM/yyyy");
    }

    /**
     * * Informa a data de pagamento do recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: true
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param dataPagamento
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    /**
     * Informa a data da ultima edição feita no recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @return
     */
    public Date getDataEdicao() {
        return dataEdicao;
    }

    /**
     * Informa a data da ultima edição feita no recebimento
     *
     * <br> Tipo BD....: DATETIME
     * <br> Obrigatorio: false
     * <br> Tamanho....: -
     * <br> Enum.......: -
     *
     * @param dataEdicao
     */
    public void setDataEdicao(Date dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    /**
     * * Informa a forma de pagamento utilizada sendo:
     *
     * <br> 1 - Dinheiro
     * <br> 2 - PIX
     * <br> 3 - Transferência
     * <br> 4 - Cartão de Crédito
     * <br> 5 - Cartão de Débito
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('1', '2', '3', '4', '5')
     *
     * @return
     */
    public FormaPagamentoBean getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * * Informa a forma de pagamento utilizada sendo:
     *
     * <br> 1 - Dinheiro
     * <br> 2 - PIX
     * <br> 3 - Transferência
     * <br> 4 - Cartão de Crédito
     * <br> 5 - Cartão de Débito
     *
     * <br> Tipo BD....: ENUM
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: ('1', '2', '3', '4', '5')
     *
     * @param formaPagamento
     */
    public void setFormaPagamento(FormaPagamentoBean formaPagamento) {
        this.formaPagamento = formaPagamento;
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
    public double getvRecebimento() {
        return vRecebimento;
    }

    public String getVRecebimentoFormatado() {
        return Decimal.formatar(vRecebimento, "#,##0.00");
    }

    /**
     * Informa o valor do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @param vRecebimento
     */
    public void setvRecebimento(double vRecebimento) {
        this.vRecebimento = vRecebimento;
    }

    /**
     * * Informa o valor do desconto aplicado no recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @return
     */
    public double getvDesconto() {
        return vDesconto;
    }

    public String getVDescontoFormatado() {
        return Decimal.formatar(vDesconto, "#,##0.00");
    }

    /**
     * * Informa o valor do desconto aplicado no recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @param vDesconto
     */
    public void setvDesconto(double vDesconto) {
        this.vDesconto = vDesconto;
    }

    /**
     * * Informa o valor total do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @return
     */
    public double getvTotal() {
        return vTotal;
    }

    public String getVTotalFormatado() {
        return Decimal.formatar(vTotal, "#,##0.00");
    }

    /**
     * * Informa o valor total do recebimento
     *
     * <br> Tipo BD....: DECIMAL(10,2)
     * <br> Obrigatorio: true
     * <br> Tamanho....: 10
     * <br> Enum.......: -
     *
     * @param vTotal
     */
    public void setvTotal(double vTotal) {
        this.vTotal = vTotal;
    }

    /**
     * * Informa se o recebimento é da maatriculo
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @return
     */
    public boolean isMatricula() {
        return matricula;
    }

    /**
     * Informa se o recebimento é da maatriculo
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @param matricula
     */
    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }

    /**
     * Informa se o recebimento está marcado como excluido
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @return
     */
    public boolean isExcluido() {
        return excluido;
    }

    /**
     * Informa se o recebimento está marcado como excluido
     *
     * <br> Tipo BD....: TINYINT
     * <br> Obrigatorio: true
     * <br> Tamanho....: 1
     * <br> Enum.......: -
     *
     * @param excluido
     */
    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public String getSituacao() {
        return matricula ? "Matricula" : dataPagamento != null ? "Pago" : "Em Aberto";
    }
}
