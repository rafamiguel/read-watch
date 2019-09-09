package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.fragment.MainComentarios;
import estrada.leon.rafael.readwatch.administrador.pojo.VideosAdm;
import estrada.leon.rafael.readwatch.estudiante.adapter.VideosAdapter;

public class VideosAdapterAdm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<VideosAdm> list;
    private OnVideoAdmListener onVideoAdmListener;


    public VideosAdapterAdm(Context context, List<VideosAdm> list, OnVideoAdmListener onVideoAdmListener) {
        this.context = context;
        this.list = list;
        this.onVideoAdmListener = onVideoAdmListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos_adm,viewGroup,false);
        viewHolder=new VideosAdmViewHolder(view, onVideoAdmListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        VideosAdm video = list.get(position);
        VideosAdmViewHolder videosViewHolder = (VideosAdmViewHolder) viewHolder;
        videosViewHolder.lblDescripcion.setText(video.getDescripcion());
        videosViewHolder.lblPerfil.setText(video.getPerfil());

        String uri = video.getRutaImagen();
        int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        videosViewHolder.btnMiniatura.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VideosAdmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion, lblPerfil, txtComentario;
        OnVideoAdmListener onVideoAdmListener;
        ImageView btnMiniatura;

        private VideosAdmViewHolder(@NonNull View itemView, OnVideoAdmListener onVideoAdmListener) {
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            btnMiniatura = itemView.findViewById(R.id.btnMiniatura);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            txtComentario.setOnClickListener(this);
            this.onVideoAdmListener = onVideoAdmListener;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.lblDescripcion:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onVideoAdmListener.perfilClick(getAdapterPosition(),list);
                    break;
                case R.id.txtComentario:

                    break;
                case R.id.btnMiniatura:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                 default:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Este es el Video" + list.get(getAdapterPosition()), Toast.LENGTH_SHORT));
            }
        }
    }
    public interface OnVideoAdmListener {
        void onVideoClick(int position, List<VideosAdm> list, Toast toast);
        void perfilClick(int position, List<VideosAdm> list);
        void comentarioClick(int position, List<VideosAdm> list);
    }
}
