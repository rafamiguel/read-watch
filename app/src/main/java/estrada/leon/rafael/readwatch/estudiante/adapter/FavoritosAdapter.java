package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
    private final int DOCUMENTO=1;
    private final int VIDEO=2;
    int []idUsuarioVidDocFav;

    public FavoritosAdapter(Context context, List<Item> list,OnFavoritosListener onFavoritosListener,  int []idUsuarioVidDocFav) {
        this.context = context;
        this.list = list;
        this.onFavoritosListener=onFavoritosListener;
        this.idUsuarioVidDocFav = idUsuarioVidDocFav;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case DOCUMENTO:{
                view=LayoutInflater.from(context).inflate(R.layout.documento_fav,viewGroup,false);
                viewHolder=new DocumentosViewHolder(view,onFavoritosListener);
                break;
            }
            case VIDEO:{
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
            case DOCUMENTO: {
                Documentos documento = (Documentos) list.get(position);
                DocumentosViewHolder documentosViewHolder = (DocumentosViewHolder) viewHolder;
                documentosViewHolder.lblPerfil.setText(documento.getPerfil());
                documentosViewHolder.lblDescripcion.setText(documento.getDescripcion());
                if(idUsuarioVidDocFav!=null){
                    for (int j = 0; j < idUsuarioVidDocFav.length; j++) {
                        if (idUsuarioVidDocFav[j] == documento.getIdVidDoc()) {
                            ((DocumentosViewHolder) viewHolder).btnFavorito.setBackgroundResource(R.drawable.favorito);
                            break;
                        } else {
                            ((DocumentosViewHolder) viewHolder).btnFavorito.setBackgroundResource(R.drawable.star2);
                        }
                    }

                }
                break;
            }
            case VIDEO:{
                Videos video=(Videos)list.get(position);
                VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
                videosViewHolder.lblPerfil.setText(video.getPerfil());
                videosViewHolder.lblDescripcion.setText(video.getDescripcion());
                String uri = video.getRutaImagen();
                int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
                String url= video.getVideoUrl();
                videosViewHolder.btnMiniatura.loadData(url, "text/html" , "utf-8" );
                if(idUsuarioVidDocFav!=null){
                    for (int j = 0; j < idUsuarioVidDocFav.length; j++) {
                        if (idUsuarioVidDocFav[j] == video.getIdVidDoc()) {
                            videosViewHolder.btnFavorito.setBackgroundResource(R.drawable.favorito);
                            break;
                        } else {
                            videosViewHolder.btnFavorito.setBackgroundResource(R.drawable.star2);
                        }
                    }

                }
                break;
            }
            default:
                Videos video=(Videos)list.get(position);
                VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
                videosViewHolder.lblPerfil.setText(video.getPerfil());
                videosViewHolder.lblDescripcion.setText(video.getDescripcion());
                String uri = video.getRutaImagen();
                int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
                String url= video.getVideoUrl();
                videosViewHolder.btnMiniatura.loadData(url, "text/html" , "utf-8" );
                if(idUsuarioVidDocFav!=null){
                    for (int j = 0; j < idUsuarioVidDocFav.length; j++) {
                        if (idUsuarioVidDocFav[j] == video.getIdVidDoc()) {
                            videosViewHolder.btnFavorito.setBackgroundResource(R.drawable.favorito);
                            break;
                        } else {
                            videosViewHolder.btnFavorito.setBackgroundResource(R.drawable.star2);
                        }
                    }

                }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WebView btnMiniatura;
        private Button btnFavorito;
        private TextView lblPerfil,lblDescripcion;
        private OnFavoritosListener onFavoritosListener;

        public VideosViewHolder(@NonNull View itemView,OnFavoritosListener onFavoritosListener) {
            super(itemView);
            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            btnMiniatura.getSettings().setJavaScriptEnabled(true);
            btnMiniatura.setWebChromeClient(new WebChromeClient() {

            } );
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

                break;
                case R.id.lblPerfil:

                    break;
                case R.id.btnFavorito:
                    onFavoritosListener.agregarFavoritosVid(getAdapterPosition(), list);
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

                    break;
                case R.id.lblPerfil:

                    break;
                case R.id.btnFavorito:
                    onFavoritosListener.agregarFavoritosDoc(getAdapterPosition(), list);
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
        void agregarFavoritosVid(int position, List<Item> list);
        void agregarFavoritosDoc(int position, List<Item> list);
    }
}
