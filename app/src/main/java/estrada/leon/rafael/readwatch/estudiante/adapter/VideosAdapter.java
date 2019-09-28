package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Videos> list;
    int[] idUsuarioVidDoc;
    Intent entrar;
    private OnVideoListener mOnVideoListener;
    boolean reportar=true;
    boolean favorito=true;
    boolean opcion=true;
    public void setVisibility(boolean reportar,boolean favorito, boolean opcion){
       this.reportar=reportar;
       this.favorito=favorito;
       this.opcion=opcion;
    }
    public VideosAdapter(Context context, List<Videos> list, OnVideoListener onVideoListener, int[] idUsuarioVidDoc) {
        this.context=context;
        this.list=list;
        this.mOnVideoListener = onVideoListener;
        this.idUsuarioVidDoc=idUsuarioVidDoc;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion,lblPerfil;
        OnVideoListener onVideoListener;
        Button btnAdvertencia,btnFavorito,btnOpcion;
        ImageView btnMiniatura;
        EditText txtComentario;
        private VideosViewHolder(@NonNull View itemView, OnVideoListener onVideoListener) {
            super(itemView);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            lblPerfil=itemView.findViewById(R.id.lblPerfil);
            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            btnAdvertencia=itemView.findViewById(R.id.btnAdvertencia);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            txtComentario=itemView.findViewById(R.id.txtComentario);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnFavorito.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            txtComentario.setOnClickListener(this);
            this.onVideoListener = onVideoListener;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.lblDescripcion:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onVideoListener.perfilClick(getAdapterPosition(),list);
                    break;
                case R.id.txtComentario:
                    onVideoListener.comentarioClick(getAdapterPosition(), list);
                    break;
                case R.id.lblReportar:
                    onVideoListener.reportarClick();
                    break;
                case R.id.btnMiniatura:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnAdvertencia:
                    onVideoListener.reportarClick();
                    break;
                case R.id.btnFavorito:
                    onVideoListener.onVideoClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el botón de favoritos", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnOpcion:
                    onVideoListener.opcionClick(getAdapterPosition(),list);
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
        videosViewHolder.lblPerfil.setText(video.getPerfil());
        if (idUsuarioVidDoc!=null) {
            for (int j = 0; j < idUsuarioVidDoc.length; j++) {
                if (idUsuarioVidDoc[j] == video.getIdVidDoc()) {
                    videosViewHolder.btnOpcion.setVisibility(View.VISIBLE);
                    break;
                } else {
                    videosViewHolder.btnOpcion.setVisibility(View.GONE);
                }
            }
        }else{
            videosViewHolder.btnOpcion.setVisibility(View.GONE);
        }

        if(reportar==false)
            videosViewHolder.btnAdvertencia.setVisibility(View.GONE);
        if(favorito==false)
            videosViewHolder.btnFavorito.setVisibility(View.GONE);
        if(opcion==false)
            videosViewHolder.btnOpcion.setVisibility(View.GONE);
        String uri = video.getRutaImagen();
        int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        videosViewHolder.btnMiniatura.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnVideoListener{
        void onVideoClick(int position,List<Videos> list, Toast toast);
        void reportarClick();
        void perfilClick(int position,List<Videos> list);
        void comentarioClick(int position, List<Videos> list);
        void opcionClick(int position, List<Videos> list);
    }

}