package academia.utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataHora {
    public static final int DIA = Calendar.DAY_OF_MONTH;
    public static final int MES = Calendar.MONTH;
    public static final int ANO = Calendar.YEAR;

    public static Date stringParseDate(String dataFormatada, String formato) throws ParseException {
        SimpleDateFormat dataFormat = new SimpleDateFormat(formato);
        return dataFormat.parse(dataFormatada);
    }

    public static String formatarData(Date data, String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(data);
    }

    public static int getUltimoDiaDoMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getData(Date data, int campo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        switch (campo) {
            case DIA:
                return cal.get(Calendar.DAY_OF_MONTH);
            case MES:
                return cal.get(Calendar.MONTH);
            case ANO:
                return cal.get(Calendar.YEAR);
            default:
                return 0;
        }
    }

    public static long getDiferencaEntreData(Date data1, Date data2, int campo) {
        long dias = 0;

        if (data1.getTime() > data2.getTime()) {
            dias = TimeUnit.MILLISECONDS.toDays(data1.getTime() - data2.getTime());
        } else {
            dias = TimeUnit.MILLISECONDS.toDays(data2.getTime() - data1.getTime());
        }

        switch (campo) {
            case DIA:
                return dias;
            case MES:
                return dias / 30;
            case ANO:
                return dias / 365;
            default:
                return 0;
        }
    }

    public static ArrayList<String> getListMesesExtenso() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Janeiro");
        list.add("Fevereiro");
        list.add("Março");
        list.add("Abril");
        list.add("Maio");
        list.add("Junho");
        list.add("Julho");
        list.add("Agosto");
        list.add("Setembro");
        list.add("Outubro");
        list.add("Novembro");
        list.add("Dezembro");
        return list;
    }
}
