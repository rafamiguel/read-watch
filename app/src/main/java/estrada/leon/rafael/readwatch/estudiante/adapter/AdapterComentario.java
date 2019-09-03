package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.PojoComentario;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ViewHolderComentario> {

    Context context;
    List<PojoComentario> list;

    public AdapterComentario(Context context, List<PojoComentario> list){
        this.context = context;
        this.list=list;

    }


    @NonNull
    @Override
    public ViewHolderComentario onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vistadecomentario, viewGroup, false );
        ViewHolderComentario viewHolderComentario = new ViewHolderComentario(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);


        return viewHolderComentario;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComentario viewHolderComentario, int i) {
        viewHolderComentario.txtComentario.setText(list.get(i).getComentario());
        viewHolderComentario.lblPerfil.setText(list.get(i).getPerfil());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder {
        TextView lblPerfil, lblReportar, lblComentario;
        EditText txtComentario;
        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            lblReportar = itemView.findViewById(R.id.lblReportar);
            txtComentario = itemView.findViewById(R.id.txtComentario);


        }

    }
}
