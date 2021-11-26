package academia.bean;

import java.util.ArrayList;

public class FormaPagamentoBean {
    public static final String DINEHIRO = "1";
    public static final String PIX = "2";
    public static final String TRASNFERENCIA = "3";
    public static final String CARTAO_CREDITO = "4";
    public static final String CARTAO_DEBITO = "5";

    private String codigo;

    private String descricao;

    /**
     * Construtor
     *
     * @param codigo
     */
    public FormaPagamentoBean(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        if (descricao != null) return descricao;
        else if (codigo == null) return null;

        switch (codigo) {
            case DINEHIRO:
                descricao = "Dinheiro";
                break;
            case PIX:
                descricao = "PIX";
                break;
            case TRASNFERENCIA:
                descricao = "Transferência";
                break;
            case CARTAO_CREDITO:
                descricao = "Cartão de Crédito";
                break;
            case CARTAO_DEBITO:
                descricao = "Cartão de Debito";
                break;
        }

        return descricao;
    }

    public static ArrayList<FormaPagamentoBean> getListFormasPagamento() {
        ArrayList<FormaPagamentoBean> list = new ArrayList<>();
        list.add(new FormaPagamentoBean(DINEHIRO));
        list.add(new FormaPagamentoBean(PIX));
        list.add(new FormaPagamentoBean(TRASNFERENCIA));
        list.add(new FormaPagamentoBean(CARTAO_CREDITO));
        list.add(new FormaPagamentoBean(CARTAO_DEBITO));
        return list;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
