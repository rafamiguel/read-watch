package estrada.leon.rafael.readwatch.general.funciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import estrada.leon.rafael.readwatch.general.pojo.Fecha;

public class ObtenerTiempo {

    public ObtenerTiempo() {
    }

    public static boolean reiniciarVotaciones(Fecha fecha){
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        String datetime = dateformat.format(c.getTime());

        try {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateformat.parse(fecha.getFecha()));// all done
        cal.add(Calendar.WEEK_OF_MONTH, 1);
        if (cal.before(c)) {
            return true;
        }

        return false;
        } catch (ParseException e) {
            return false;
        }
    }
    public String obtenerAntiguedadPregunta(){ return "";}
}
