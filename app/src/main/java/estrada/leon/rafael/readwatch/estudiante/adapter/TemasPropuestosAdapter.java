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
import estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos;

public class TemasPropuestosAdapter extends ArrayAdapter<TemasPropuestos> {
    Context context;
    int layoutResourceId,votos;
    float porcentaje;
    TemasPropuestos[] datos;

    public TemasPropuestosAdapter(@NonNull Context context, int resource, TemasPropuestos[] objects,int votos) {
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

        TemasPropuestos temasPropuestos=datos[position];
        temasPropuestosHolder.lblTemaPropuesto.setText(temasPropuestos.getNombre());
        porcentaje=temasPropuestos.getVotos()*100/(float)votos;
        temasPropuestosHolder.lblPorcentajeVotos.setText(Float.toString(porcentaje)+"% de los votos");
        return row;
    }

    static class TemasPropuestosHolder{
        TextView lblTemaPropuesto,lblPorcentajeVotos;
        CheckBox cbTemaPropuesto;

    }

}