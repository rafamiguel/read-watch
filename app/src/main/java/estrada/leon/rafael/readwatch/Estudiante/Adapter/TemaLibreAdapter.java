package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.TemaLibre;
import estrada.leon.rafael.readwatch.R;

public class TemaLibreAdapter extends RecyclerView.Adapter<TemaLibreAdapter.ViewHolder>{

    Context context;
    List<TemaLibre> temaLibreList;

    public TemaLibreAdapter(Context context, List<TemaLibre> temaLibreList){
        this.context = context;
        this.temaLibreList = temaLibreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temaslibres, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.lblPregunta.setText(temaLibreList.get(i).getPregunta());
        viewHolder.lblDescripcion.setText(temaLibreList.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return temaLibreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblPregunta, lblDescripcion, lblReportar;
        EditText txtComentario;
        Button btnAgregarPregunta, btnAdvertencia, btnEditar, btnSubirDocumento, btnInsertarLink;
        public ViewHolder(View item){
            super(item);
            lblPregunta = item.findViewById(R.id.lblPregunta);
            lblDescripcion = item.findViewById(R.id.lblDescripcion);
            lblReportar = item.findViewById(R.id.lblReportar);
            txtComentario = item.findViewById(R.id.txtComentario);
            btnAdvertencia = item.findViewById(R.id.btnAdvertencia);
            btnEditar= item.findViewById(R.id.btnEditar);
            btnSubirDocumento = item.findViewById(R.id.btnSubirDocumento);
            btnInsertarLink = item.findViewById(R.id.btnInsertarLink);

        }
    }
}
