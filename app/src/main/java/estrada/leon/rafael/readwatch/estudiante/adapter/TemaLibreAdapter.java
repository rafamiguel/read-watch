package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.pojo.TemaLibre;
import estrada.leon.rafael.readwatch.R;

public class TemaLibreAdapter extends RecyclerView.Adapter<TemaLibreAdapter.ViewHolder>{

    private Context context;
    private List<TemaLibre> temaLibreList;
    private OnTemaListener onTemaListener;

    public TemaLibreAdapter(Context context, List<TemaLibre> temaLibreList, OnTemaListener onTemaListener){
        this.context = context;
        this.temaLibreList = temaLibreList;
        this.onTemaListener = onTemaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temaslibres, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView, onTemaListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblPregunta, lblDescripcion, lblReportar;
        EditText txtComentario;
        Button btnAgregarPregunta, btnAdvertencia, btnEditar, btnSubirDocumento, btnInsertarLink;
        OnTemaListener onTemaListener;
        private ViewHolder(View item, OnTemaListener onTemaListener){
            super(item);
            this.onTemaListener = onTemaListener;
            lblPregunta = item.findViewById(R.id.lblPregunta);
            lblDescripcion = item.findViewById(R.id.lblDescripcion);
            lblReportar = item.findViewById(R.id.lblReportar);
            txtComentario = item.findViewById(R.id.txtComentario);
            btnAdvertencia = item.findViewById(R.id.btnAdvertencia);
            btnEditar= item.findViewById(R.id.btnEditar);
            btnSubirDocumento = item.findViewById(R.id.btnSubirDocumento);
            btnInsertarLink = item.findViewById(R.id.btnInsertarLink);

            lblPregunta.setOnClickListener(this);
            lblDescripcion.setOnClickListener(this);
            lblReportar.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnEditar.setOnClickListener(this);
            btnSubirDocumento.setOnClickListener(this);
            btnInsertarLink.setOnClickListener(this);

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.lblPregunta:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Esta es la pregunta", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblDescripcion:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblReportar:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Esta es la etiqueta para reportar comentarios.", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnSubirDocumento:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Boton para subir documento", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnAdvertencia:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Este es el boton de reportar", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnEditar:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Este es el botón para editar", Toast.LENGTH_SHORT));
                    break;
                default:
                    onTemaListener.onTemaClick(getAdapterPosition(),temaLibreList,
                            Toast.makeText(context, "Este es el boton para insertar Link", Toast.LENGTH_SHORT));

            }
        }
    }
    public interface OnTemaListener{
        void onTemaClick(int position, List<TemaLibre> temaLibreList, Toast toast);
    }
}
