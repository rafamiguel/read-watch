package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;

public class ListaMateriasAdapter extends ArrayAdapter<Materias> {
    Context context;
    int layoutResourceId;
    Materias[] datos;

    public ListaMateriasAdapter(Context context, int resource, @NonNull Materias[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutResourceId=resource;
        this.datos=objects;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View row= convertView;
        ListaMateriasHolder listaMateriasHolder;
        if(row==null){
            LayoutInflater inflater;
            inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            listaMateriasHolder=new ListaMateriasHolder();
            listaMateriasHolder.lblmateria=row.findViewById(R.id.lblMateria);
            row.setTag(listaMateriasHolder);
        }else{
            listaMateriasHolder=(ListaMateriasHolder) row.getTag();
        }

        Materias materias=datos[position];
        listaMateriasHolder.lblmateria.setText(materias.getNombre());
        return row;
    }
    static class ListaMateriasHolder{
        TextView lblmateria;
    }
}
