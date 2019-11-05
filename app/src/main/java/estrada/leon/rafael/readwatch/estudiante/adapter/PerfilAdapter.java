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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class PerfilAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Item> list;
    private OnPerfilListener onPerfilListener;
    private final int documento=1;
    private final int video=2;
    int nuevo;
    int []idUsuarioVidDocFav;

    public PerfilAdapter(Context context,List<Item> list,OnPerfilListener onPerfilListener, int nuevo,  int []idUsuarioVidDocFav){
        this.context=context;
        this.list=list;
        this.onPerfilListener=onPerfilListener;
        this.nuevo=nuevo;
        this.idUsuarioVidDocFav=idUsuarioVidDocFav;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case documento:{
                view= LayoutInflater.from(context).inflate(R.layout.documento_perfil,viewGroup,false);
                viewHolder=new DocumentosViewHolder(view,onPerfilListener);
                break;
            }
            case video:{
                view=LayoutInflater.from(context).inflate(R.layout.video_perfil,viewGroup,false);
                viewHolder=new VideosViewHolder(view,onPerfilListener);
                break;
            }
            default:
                view=LayoutInflater.from(context).inflate(R.layout.video_perfil,viewGroup,false);
                viewHolder=new VideosViewHolder(view,onPerfilListener);
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
                documentosViewHolder.lblDescripcion.setText(documento.getDescripcion());
                if(idUsuarioVidDocFav!=null){
                    for (int j = 0; j < idUsuarioVidDocFav.length; j++) {
                        if (idUsuarioVidDocFav[j] == documento.getIdVidDoc()) {
                            documentosViewHolder.btnFavorito.setBackgroundResource(R.drawable.favorito);
                            break;
                        } else {
                            documentosViewHolder.btnFavorito.setBackgroundResource(R.drawable.star2);
                        }
                    }

                }
                break;
            }
            case video:{
                Videos video=(Videos)list.get(position);
                VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
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
        private Button btnFavorito, btnAdvertencia, btnOpcion;
        private TextView lblDescripcion;
        private EditText txtComentario;
        private OnPerfilListener onPerfilListener;

        public VideosViewHolder(@NonNull View itemView,OnPerfilListener onPerfilListener) {
            super(itemView);
            int idUsuarios;
            SharedPreferences preferences = context.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
            idUsuarios = preferences.getInt("idUsuario", 0);

            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            btnMiniatura.getSettings().setJavaScriptEnabled(true);
            btnMiniatura.setWebChromeClient(new WebChromeClient() {

            } );
            btnAdvertencia=itemView.findViewById(R.id.btnAdvertencia);
            btnOpcion= itemView.findViewById(R.id.btnOpcion);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            txtComentario=itemView.findViewById(R.id.txtComentario);

            if(nuevo == Sesion.getSesion().getId()){
                btnAdvertencia.setVisibility(View.GONE);
                btnFavorito.setVisibility(View.GONE);
            }
            if(nuevo != Sesion.getSesion().getId()){
                btnOpcion.setVisibility(View.GONE);}
            this.onPerfilListener=onPerfilListener;
            btnFavorito.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            txtComentario.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnMiniatura:
                    break;
                case R.id.txtComentario:
                    onPerfilListener.comentarioClickVid(getAdapterPosition(), list);
                   break;
                case R.id.btnFavorito:
                    onPerfilListener.agregarFavoritos(getAdapterPosition(),list);
                    break;
                case R.id.btnAdvertencia:
                    onPerfilListener.onPerfilClick(getAdapterPosition(),list,Toast.makeText(context,"Boton Advertencia",Toast.LENGTH_SHORT));
                    break;
                case R.id.btnOpcion:
                    onPerfilListener.onPerfilClick(getAdapterPosition(),list,Toast.makeText(context,"Boton Opcion",Toast.LENGTH_SHORT));
                    break;
            }
        }
    }

    public class DocumentosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView btnDocumento;
        Button btnFavorito, btnAdvertencia, btnOpcion;
        TextView lblDescripcion;
        EditText txtComentario;
        OnPerfilListener onPerfilListener;

        private DocumentosViewHolder(@NonNull View itemView,OnPerfilListener onPerfilListener) {
            super(itemView);
            int idUsuarios;
            SharedPreferences preferences = context.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
            idUsuarios = preferences.getInt("idUsuario", 0);


                btnDocumento = itemView.findViewById(R.id.btnDocumento);
                lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
                btnFavorito = itemView.findViewById(R.id.btnFavorito);
                btnAdvertencia = itemView.findViewById(R.id.btnAdvertencia);
                btnOpcion = itemView.findViewById(R.id.btnOpcion);
                txtComentario = itemView.findViewById(R.id.txtComentario);

                 if(nuevo == Sesion.getSesion().getId()){
                btnAdvertencia.setVisibility(View.GONE);
                btnFavorito.setVisibility(View.GONE);
                 }else{btnAdvertencia.setVisibility(View.VISIBLE);btnFavorito.setVisibility(View.VISIBLE);}
               if(nuevo != Sesion.getSesion().getId()){btnOpcion.setVisibility(View.GONE);}
               else{btnOpcion.setVisibility(View.VISIBLE);}

                this.onPerfilListener = onPerfilListener;
                btnDocumento.setOnClickListener(this);
                btnFavorito.setOnClickListener(this);
                btnAdvertencia.setOnClickListener(this);
                btnOpcion.setOnClickListener(this);
                txtComentario.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnDocumento:
                    break;
                case R.id.lblDescripcion:
                    break;
                case R.id.btnFavorito:
                    onPerfilListener.agregarFavoritosDoc(getAdapterPosition(),list);
                    break;
                case R.id.btnAdvertencia:
                    onPerfilListener.onPerfilClick(getAdapterPosition(),list,Toast.makeText(context,"Advertencia",Toast.LENGTH_SHORT));
                    break;
                case R.id.btnOpcion:
                    onPerfilListener.onPerfilClick(getAdapterPosition(),list,Toast.makeText(context,"Opcion",Toast.LENGTH_SHORT));
                    break;
                case R.id.txtComentario:
                    onPerfilListener.comentarioClickVidDoc(getAdapterPosition(), list);
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int posicion){
        return list.get(posicion).getViewType();
    }
    public interface OnPerfilListener {
        void onPerfilClick(int position, List<Item> lista, Toast toast);

        void comentarioClickVid(int adapterPosition, List<Item> list);

        void agregarFavoritos(int adapterPosition, List<Item> list);

        void agregarFavoritosDoc(int adapterPosition, List<Item> list);

        void comentarioClickVidDoc(int adapterPosition, List<Item> list);
    }

}
