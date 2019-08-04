package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import estrada.leon.rafael.readwatch.R;

public class AdaptadorTemas extends RecyclerView.Adapter<AdaptadorTemas.ViewHolderDatos> {
    ArrayList<String> listDatos;
    public AdaptadorTemas(ArrayList<String> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temas, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
    viewHolderDatos.asignarDatos(listDatos.get(i));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tema;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
                tema= (TextView) itemView.findViewById(R.id.tema);
        }

        public void asignarDatos(String s) {
            if(s.substring(0,1).equals("-")) {
                tema.setBackgroundColor(Color.WHITE);
                tema.setText(s);
                tema.setGravity(Gravity.LEFT);
                tema.setTextSize(15);
            }else{
                tema.setBackgroundColor(Color.BLUE);
                tema.setText(s);
                tema.setGravity(Gravity.CENTER);
                tema.setTextSize(20);
            }

        }
    }
}
