package academia.utilitarios;

import java.text.DecimalFormat;

public class Decimal {

    public static String formatar(double valor, String formato) {
        DecimalFormat formatacao = new DecimalFormat(formato);
        return formatacao.format(valor);
    }

    public static double getDoubleAredondado(double valor) {
        DecimalFormat formatacao = new DecimalFormat("#.##");
        return valorFormatadoParseDouble(formatacao.format(valor));
    }

    public static double valorFormatadoParseDouble(String valor) {
        String v =  valor
                .replaceAll("\\.", "")
                .replaceAll(",", ".")
                .replaceAll(" ", "")
                .replace(" ", "")
                .replace("R$", "");

        return Double.parseDouble(v);
    }
}
