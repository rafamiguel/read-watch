package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.Documentos;
import estrada.leon.rafael.readwatch.R;

public class DocumentosAdapter extends RecyclerView.Adapter<DocumentosAdapter.ViewHolder>{

    Context context;
    List<Documentos> documentosList;

    public DocumentosAdapter(Context context, List<Documentos> documentosList){
        this.context=context;
        this.documentosList=documentosList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.documentos, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.lblPerfil.setText(documentosList.get(i).getPerfil());
    viewHolder.lblDescripcion.setText(documentosList.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return documentosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblDescripcion, lblPerfil;
        public ViewHolder (View itemView){
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
        }
    }
}
