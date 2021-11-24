package academia.utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHora {
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
}
