package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.Subtemas;
import estrada.leon.rafael.readwatch.Estudiante.POJO.Temas;
import estrada.leon.rafael.readwatch.Interfaces.Item;
import estrada.leon.rafael.readwatch.R;

public class TemasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    /* Item tiene que ser una interfaz implementada en todos los objetos que se quieren
    * usar en el RecyclerView.*/
    List<Item> temasList;

    /*Estas constantes solo son para identificar el valor que nos devuelve la función getViewType*/
    private final int TEMA = 1;
    private final int SUBTEMA = 2;
    public TemasAdapter(Context context, List<Item> temasList){
        this.context=context;
        this.temasList=temasList;
    }

    /*Por cada tipo de vista en el RecyclerView se necesita una clase con la estructura que se puede ver
    * en las siguientes clases*/
    public class TemasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;

        public TemasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTema);
        }

    }
    public class SubtemasViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        public SubtemasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombreSubtema);
        }

    }

    /*Aquí hay que destacar que se ejecuta primero el método onBindViewHolder, quien sabe por que se pone primero
    * onCreateViewHolder*/
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case TEMA:{
                view=LayoutInflater.from(context).inflate(R.layout.temas,viewGroup,false);
                viewHolder=new TemasViewHolder(view);
                break;
            }
            case SUBTEMA:{
                view=LayoutInflater.from(context).inflate(R.layout.subtemas,viewGroup,false);
                viewHolder=new SubtemasViewHolder(view);
                break;
            }
            default:
                view=LayoutInflater.from(context).inflate(R.layout.subtemas,viewGroup,false);
                viewHolder=new SubtemasViewHolder(view);
                return viewHolder;
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    /*onBind es el que va a recorrer la lista, por eso llamamos a la función getItemViewType
    * para ver que tipo de elemento está en nuestra lista.*/
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int posicion) {
    switch(getItemViewType(posicion)){
        case TEMA:{
            Temas tema = (Temas) temasList.get(posicion);
            TemasViewHolder temasViewHolder= (TemasViewHolder) viewHolder;
            temasViewHolder.nombre.setText(tema.getNombre());
            break;
        }
        case SUBTEMA:{
            Subtemas subtema = (Subtemas) temasList.get(posicion);
            SubtemasViewHolder subtemasViewHolder = (SubtemasViewHolder) viewHolder;
            subtemasViewHolder.nombre.setText(subtema.getNombre());
            break;
        }
        default:
            Subtemas subtema = (Subtemas) temasList.get(posicion);
            SubtemasViewHolder subtemasViewHolder = (SubtemasViewHolder) viewHolder;
            subtemasViewHolder.nombre.setText(subtema.getNombre());
    }
    }

    @Override
    public int getItemCount() {
        return temasList.size();
    }
    /*Es muy importante escribir esta función manualmente, junto con su override.*/
    @Override
    public int getItemViewType(int posicion){
        return temasList.get(posicion).getViewType();
    }
}