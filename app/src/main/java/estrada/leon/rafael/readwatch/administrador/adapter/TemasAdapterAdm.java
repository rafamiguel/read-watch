package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.BtnOpciones;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;

public class TemasAdapterAdm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Item> temasList;
    private OnTemasListener onTemasListener;
    private final int TEMA = 1;
    private final int SUBTEMA = 2;

    int i;

    public TemasAdapterAdm(Context context, List<Item> temasList,OnTemasListener onTemasListener){
        this.context=context;
        this.temasList=temasList;
        this.onTemasListener=onTemasListener;
    }


    /*Por cada tipo de vista en el RecyclerView se necesita una clase con la estructura que se puede ver
     * en las siguientes clases*/
    public class TemasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre;
        OnTemasListener onTemasListener;
        Button  btnOpcionTema;
        private TemasViewHolder(@NonNull View itemView, OnTemasListener onTemasListener) {
            super(itemView);
            this.onTemasListener=onTemasListener;
            nombre = itemView.findViewById(R.id.nombreTema);
            btnOpcionTema=itemView.findViewById(R.id.btnOpcionTema);
            btnOpcionTema.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btnOpcionTema){
                onTemasListener.Tema(getAdapterPosition(),temasList);
            }
        }
    }
    public class SubtemasViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView nombre;
        OnTemasListener onTemasListener;
        Button btnOpcion;
        private SubtemasViewHolder(@NonNull View itemView, OnTemasListener onTemasListener) {
            super(itemView);
            this.onTemasListener=onTemasListener;
            nombre= itemView.findViewById(R.id.nombreSubtema);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            nombre.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.nombreSubtema){
                onTemasListener.onTemaClick(getAdapterPosition(),temasList);
            }
            if(v.getId()==R.id.btnOpcion){
                onTemasListener.Subtema(getAdapterPosition(),temasList);
            }

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
                view= LayoutInflater.from(context).inflate(R.layout.temas_adm,viewGroup,false);
                viewHolder=new TemasViewHolder(view,onTemasListener);
                break;
            }
            case SUBTEMA:{
                view=LayoutInflater.from(context).inflate(R.layout.subtemas_adm,viewGroup,false);
                viewHolder=new SubtemasViewHolder(view,onTemasListener);
                break;
            }
            default:
                view=LayoutInflater.from(context).inflate(R.layout.subtemas_adm,viewGroup,false);
                viewHolder=new SubtemasViewHolder(view,onTemasListener);
                return viewHolder;
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }



    /*onBind es el que va a recorrer la lista, por eso llamamos a la función getItemViewType
     * para ver que tipo de elemento está en nuestra lista.*/
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int posicion) {
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


    public interface OnTemasListener{
        void onTemaClick(int position,List<Item> lista);
        void Tema(int position, List<Item> lista);
        void Subtema(int posicion, List<Item> lista);
    }
}