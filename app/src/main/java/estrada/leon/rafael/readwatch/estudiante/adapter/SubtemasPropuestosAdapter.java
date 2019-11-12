package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;

public class SubtemasPropuestosAdapter extends ArrayAdapter<Subtemas> {
    Context context;
    int layoutResourceId, votos;
    float porcentaje;
    List<Subtemas> datos;
    
        public SubtemasPropuestosAdapter(Context context, int resource, List<Subtemas> objects, int votos) {
            super(context, resource, objects);
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
            subTemasPropuestosHolder.lblSubtemaPropuesto = row.findViewById(R.id.lblSubtemaPropuesto);
            subTemasPropuestosHolder.lblPorcentajeVotos = row.findViewById(R.id.lblPorcentajeVotos);
            subTemasPropuestosHolder.cbSubtemaPropuesto = row.findViewById(R.id.cbSubtemaPropuesto);
            row.setTag(subTemasPropuestosHolder);
        } else {
            subTemasPropuestosHolder = (SubTemasPropuestosHolder) row.getTag();
        }

        Subtemas subtema = datos.get(position);
            subTemasPropuestosHolder.lblSubtemaPropuesto.setText(subtema.getNombre());
        if (votos > 0) {
            porcentaje = subtema.getVotos() * 100 / (float) votos;
        } else {
            porcentaje = 0;
        }
            subTemasPropuestosHolder.lblPorcentajeVotos.setText(((float) Math.round(porcentaje * 100) / 100) + "% de los votos");
        return row;
    }


    static class SubTemasPropuestosHolder {
        TextView lblSubtemaPropuesto, lblPorcentajeVotos;
        CheckBox cbSubtemaPropuesto;

    }
}
