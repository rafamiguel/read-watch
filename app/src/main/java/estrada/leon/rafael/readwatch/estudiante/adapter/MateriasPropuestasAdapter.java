package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class MateriasPropuestasAdapter extends ArrayAdapter<MateriasPropuestas> {
    Context context;
    int layoutResourceId,votos;
    Double porcentaje;
    MateriasPropuestas[] datos;
    DatabaseReference rootReference;

    public MateriasPropuestasAdapter(@NonNull Context context, int resource, MateriasPropuestas[] objects, int votos) {
        super(context, resource, objects);
        this.context=context;
        this.layoutResourceId=resource;
        this.datos=objects;
        this.votos=votos;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        View row= convertView;
        final MateriasPropuestasHolder materiasPropuestasHolder;
        if(row==null){
            LayoutInflater inflater;
            inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            materiasPropuestasHolder=new MateriasPropuestasHolder();
            materiasPropuestasHolder.lblMateriaPropuesta=row.findViewById(R.id.lblMateriaPropuesta);
            materiasPropuestasHolder.lblPorcentajeVotos=row.findViewById(R.id.lblPorcentajeVotos);
            materiasPropuestasHolder.cbMateriaPropuesta=row.findViewById(R.id.cbTemaPropuesto);
            row.setTag(materiasPropuestasHolder);
        }else{
            materiasPropuestasHolder=(MateriasPropuestasHolder) row.getTag();
        }

        MateriasPropuestas materiasPropuestas=datos[position];
        materiasPropuestasHolder.lblMateriaPropuesta.setText(materiasPropuestas.getNombre());
        if(votos>0){
            porcentaje  =  ((double)(materiasPropuestas.getVotos()))*100;
            porcentaje= porcentaje/votos;
        }else{
            porcentaje=0.0;
        }
        materiasPropuestasHolder.lblPorcentajeVotos.setText(((float)Math.round(porcentaje * 100) / 100)+"% de los votos");
        return row;
    }



    static class MateriasPropuestasHolder{
        TextView lblMateriaPropuesta,lblPorcentajeVotos;
        CheckBox cbMateriaPropuesta;
    }
}
