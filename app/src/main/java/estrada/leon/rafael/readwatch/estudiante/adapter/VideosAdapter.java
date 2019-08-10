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

import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Videos> list;
    OnVideoListener mOnVideoListener;

    public VideosAdapter(Context context, List<Videos> list, OnVideoListener onVideoListener) {
        this.context=context;
        this.list=list;
        this.mOnVideoListener = onVideoListener;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion,lblPerfil,lblReportar;
        OnVideoListener onVideoListener;
        Button btnMiniatura,btnAdvertencia,btnFavorito,btnOpcion,btnEditar;
        EditText txtComentario;
        public VideosViewHolder(@NonNull View itemView, OnVideoListener onVideoListener) {
            super(itemView);
            lblDescripcion=itemView.findViewById(R.id.txtDescripcion);
            lblPerfil=itemView.findViewById(R.id.lblPerfil);
            lblReportar=itemView.findViewById(R.id.lblReportar);
            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            btnAdvertencia=itemView.findViewById(R.id.btnAdvertencia);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            btnEditar=itemView.findViewById(R.id.btnEditar);
            txtComentario=itemView.findViewById(R.id.txtComentario);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            lblReportar.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnFavorito.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            btnEditar.setOnClickListener(this);
            this.onVideoListener = onVideoListener;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.txtDescripcion:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el perfil", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblReportar:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la etiqueta para reportar comentarios.", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnMiniatura:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnAdvertencia:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el boton de reportar", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnFavorito:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el botón de favoritos", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnOpcion:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el botón de opciones (editar y eliminar).", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnEditar:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el botón de editar comentario", Toast.LENGTH_SHORT));
                    break;
                default:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el Video"+list.get(getAdapterPosition()), Toast.LENGTH_SHORT));
            }

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos,viewGroup,false);
        viewHolder=new VideosViewHolder(view, mOnVideoListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Videos video = list.get(i);
        VideosViewHolder videosViewHolder = (VideosViewHolder) viewHolder;
        videosViewHolder.lblDescripcion.setText(video.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnVideoListener{
        void onVideoClick(int position,List<Videos> list, Toast toast);
    }

}