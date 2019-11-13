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
import estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos;

public class TemasPropuestosAdapter extends ArrayAdapter<TemasPropuestos> {
    Context context;
    int layoutResourceId,votos;
    float porcentaje;
    List<TemasPropuestos> datos;

    public TemasPropuestosAdapter(Context context, int resource, List<TemasPropuestos> objects,int votos) {
        super(context, resource, objects);
            this.context=context;
            this.layoutResourceId=resource;
            this.datos=objects;
            this.votos=votos;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        View row= convertView;
        TemasPropuestosHolder temasPropuestosHolder;
        if(row==null){
            LayoutInflater inflater;
            inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            temasPropuestosHolder=new TemasPropuestosHolder();
            temasPropuestosHolder.lblTemaPropuesto=row.findViewById(R.id.lblTemaPropuesto);
            temasPropuestosHolder.lblPorcentajeVotos=row.findViewById(R.id.lblPorcentajeVotos);
            temasPropuestosHolder.cbTemaPropuesto=row.findViewById(R.id.cbTemaPropuesto);
            row.setTag(temasPropuestosHolder);
        }else{
            temasPropuestosHolder=(TemasPropuestosHolder) row.getTag();
        }

        TemasPropuestos temasPropuestos=datos.get(position);
        temasPropuestosHolder.lblTemaPropuesto.setText(temasPropuestos.getNombre());
        if(votos>0){
        porcentaje=temasPropuestos.getVotos()*100/(float)votos;
        }else{
            porcentaje=0;
        }
        temasPropuestosHolder.lblPorcentajeVotos.setText(((float)Math.round(porcentaje * 100) / 100)+"% de los votos");
        return row;
    }

    static class TemasPropuestosHolder{
        TextView lblTemaPropuesto,lblPorcentajeVotos;
        CheckBox cbTemaPropuesto;

    }

}
