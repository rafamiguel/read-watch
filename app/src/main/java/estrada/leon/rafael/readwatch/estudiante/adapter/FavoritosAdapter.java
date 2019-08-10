package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class FavoritosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Item> list;
    private OnFavoritosListener onFavoritosListener;
    private final int documento=1;
    private final int video=2;

    public FavoritosAdapter(Context context, List<Item> list,OnFavoritosListener onFavoritosListener) {
        this.context = context;
        this.list = list;
        this.onFavoritosListener=onFavoritosListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case documento:{
                view=LayoutInflater.from(context).inflate(R.layout.documento_fav,viewGroup,false);
                viewHolder=new DocumentosViewHolder(view,onFavoritosListener);
                break;
            }
            case video:{
                view=LayoutInflater.from(context).inflate(R.layout.video_fav,viewGroup,false);
                viewHolder=new VideosViewHolder(view,onFavoritosListener);
                break;
            }
            default:
                view=LayoutInflater.from(context).inflate(R.layout.video_fav,viewGroup,false);
                viewHolder=new VideosViewHolder(view,onFavoritosListener);
                break;
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch(getItemViewType(position)){
            case documento: {
                Documentos documento = (Documentos) list.get(position);
                DocumentosViewHolder documentosViewHolder = (DocumentosViewHolder) viewHolder;
                documentosViewHolder.lblPerfil.setText(documento.getPerfil());
                documentosViewHolder.lblDescripcion.setText(documento.getDescripcion());
                break;
            }
            case video:{
                Videos video=(Videos)list.get(position);
                VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
                videosViewHolder.lblPerfil.setText(video.getPerfil());
                videosViewHolder.lblDescripcion.setText(video.getDescripcion());
                String uri = video.getRutaImagen();
                int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
                videosViewHolder.btnMiniatura.setImageResource(imageResource);
                break;
            }
            default:
                Videos video=(Videos)list.get(position);
                VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
                videosViewHolder.lblPerfil.setText(video.getPerfil());
                videosViewHolder.lblDescripcion.setText(video.getDescripcion());
                String uri = video.getRutaImagen();
                int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
                videosViewHolder.btnMiniatura.setImageResource(imageResource);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView btnMiniatura;
        private Button btnFavorito;
        private TextView lblPerfil,lblDescripcion;
        private OnFavoritosListener onFavoritosListener;

        public VideosViewHolder(@NonNull View itemView,OnFavoritosListener onFavoritosListener) {
            super(itemView);
            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            lblPerfil=itemView.findViewById(R.id.lblPerfil);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            this.onFavoritosListener=onFavoritosListener;
            btnFavorito.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnMiniatura:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Miniatura",Toast.LENGTH_SHORT));
                break;
                case R.id.lblPerfil:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Perfil",Toast.LENGTH_SHORT));
                    break;
                case R.id.btnFavorito:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Favorito",Toast.LENGTH_SHORT));
                    break;
            }
        }
    }
    public class DocumentosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView btnDocumento;
        Button btnFavorito;
        TextView lblPerfil,lblDescripcion;
        OnFavoritosListener onFavoritosListener;

        private DocumentosViewHolder(@NonNull View itemView,OnFavoritosListener onFavoritosListener) {
            super(itemView);
            btnDocumento= itemView.findViewById(R.id.btnDocumento);
            lblPerfil=itemView.findViewById(R.id.lblPerfil);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            this.onFavoritosListener=onFavoritosListener;
            btnDocumento.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnFavorito.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnDocumento:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Miniatura documento",Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Perfil",Toast.LENGTH_SHORT));
                    break;
                case R.id.btnFavorito:
                    onFavoritosListener.onFavoritoClick(getAdapterPosition(),list,Toast.makeText(context,"Favorito",Toast.LENGTH_SHORT));
                    break;
            }
        }
    }
    @Override
    public int getItemViewType(int posicion){
        return list.get(posicion).getViewType();
    }
    public interface OnFavoritosListener {
        void onFavoritoClick(int position, List<Item> lista, Toast toast);
    }
}
