package academia.utilitarios;

public class Textos {
    public static String formatarCPF(String cpf){
        if(cpf == null || cpf.isEmpty()) return "";
        return cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

    public static String formatarTelefone(String telefone) {
        if(telefone == null) return  "";

        StringBuilder sb = new StringBuilder(15);
        StringBuilder temp = new StringBuilder(telefone);

        while (temp.length() < 10)
            temp.insert(0, "0");

        char[] chars = temp.toString().toCharArray();

        sb.append("(");
        for (int i = 0; i < chars.length; i++) {

            if (i == 2) sb.append(") ");
            else if (i == 7) sb.append("-");

            sb.append(chars[i]);
        }

        return sb.toString();
    }
}
