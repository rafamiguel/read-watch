package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;

    public class SubtemasPropuestosAdapter extends ArrayAdapter<SubtemasPropuestosAdapter> {
    Context context;
    int layoutResourceId, votos;
    float porcentaje;
    Subtemas[] datos;
    
        public SubtemasPropuestosAdapter(@NonNull Context context, int resource, Subtemas[] objects, int votos) {
            super(context, resource);
            this.datos = objects;
            this.votos = votos;
            this.layoutResourceId = resource;
            this.context = context;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SubTemasPropuestosHolder subTemasPropuestosHolder;
        if (row == null) {
            LayoutInflater inflater;
            inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            subTemasPropuestosHolder = new SubTemasPropuestosHolder();
            subTemasPropuestosHolder.lblTemaPropuesto = row.findViewById(R.id.lblTemaPropuesto);
            subTemasPropuestosHolder.lblPorcentajeVotos = row.findViewById(R.id.lblPorcentajeVotos);
            subTemasPropuestosHolder.cbTemaPropuesto = row.findViewById(R.id.cbTemaPropuesto);
            row.setTag(subTemasPropuestosHolder);
        } else {
            subTemasPropuestosHolder = (SubTemasPropuestosHolder) row.getTag();
        }

        Subtemas subtema = datos[position];
            subTemasPropuestosHolder.lblTemaPropuesto.setText(subtema.getNombre());
        if (votos > 0) {
            porcentaje = subtema.getVotos() * 100 / (float) votos;
        } else {
            porcentaje = 0;
        }
            subTemasPropuestosHolder.lblPorcentajeVotos.setText(((float) Math.round(porcentaje * 100) / 100) + "% de los votos");
        return row;
    }


    static class SubTemasPropuestosHolder {
        TextView lblTemaPropuesto, lblPorcentajeVotos;
        CheckBox cbTemaPropuesto;

    }
}
