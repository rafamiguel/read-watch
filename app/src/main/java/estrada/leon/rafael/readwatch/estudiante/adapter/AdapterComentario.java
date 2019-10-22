package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.media.Image;
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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class AdapterComentario extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Item> list;
    private final int DOCUMENTO=1;
    private final int VIDEO=2;
    private final int COMENTARIO=3;
    private OnComentariosListener onComentariosListener;
    boolean reportar=true;
    boolean favorito=true;
    boolean opcion=true;
    private int []idComentarioUsuario;
    private int []idVideoEnComentarioUsuario;
    public AdapterComentario(Context context, List<Item> list,OnComentariosListener onComentariosListener,int []idComentarioUsuario){
        this.context = context;
        this.list=list;
        this.onComentariosListener=onComentariosListener;
        this.idComentarioUsuario=idComentarioUsuario;
    }
    public AdapterComentario(Context context, List<Item> list,OnComentariosListener onComentariosListener,int []idComentarioUsuario, int []idVideoEnComentarioUsuario){
        this.context = context;
        this.list=list;
        this.onComentariosListener=onComentariosListener;
        this.idComentarioUsuario=idComentarioUsuario;
        this.idVideoEnComentarioUsuario=idVideoEnComentarioUsuario;
    }
    public void refresh(List<Item> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case DOCUMENTO:{
                view=LayoutInflater.from(context).inflate(R.layout.documentos,viewGroup,false);
                viewHolder=new DocumentosViewHolder(view);
                break;
            }
            case VIDEO:{
                view=LayoutInflater.from(context).inflate(R.layout.videos,viewGroup,false);
                viewHolder=new VideosViewHolder(view);
                break;
            }
            case COMENTARIO:{
                view=LayoutInflater.from(context).inflate(R.layout.vistadecomentario,viewGroup,false);
                viewHolder=new ViewHolderComentario(view);
                break;
            }
            default:
                view=LayoutInflater.from(context).inflate(R.layout.vistadecomentario,viewGroup,false);
                viewHolder=new ViewHolderComentario(view);
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
                if (idVideoEnComentarioUsuario!=null) {
                    for (int j = 0; j < idVideoEnComentarioUsuario.length; j++) {
                        if (idVideoEnComentarioUsuario[j] == documento.getIdVidDoc()) {
                            ((DocumentosViewHolder) viewHolder).btnOpcion.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            ((DocumentosViewHolder) viewHolder).btnOpcion.setVisibility(View.GONE);
                        }
                    }
                }else{
                    ((DocumentosViewHolder) viewHolder).btnOpcion.setVisibility(View.GONE);
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
                videosViewHolder.btnMiniatura.setImageResource(imageResource);
                if (idVideoEnComentarioUsuario!=null) {
                    for (int j = 0; j < idVideoEnComentarioUsuario.length; j++) {
                        if (idVideoEnComentarioUsuario[j] == video.getIdVidDoc()) {
                            ((VideosViewHolder) viewHolder).btnOpcion.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            ((VideosViewHolder) viewHolder).btnOpcion.setVisibility(View.GONE);
                        }
                    }
                }else{
                    ((VideosViewHolder) viewHolder).btnOpcion.setVisibility(View.GONE);
                }
                break;
            }
            case COMENTARIO:{
                Comentarios comentario = (Comentarios) list.get(position);
                ViewHolderComentario viewHolderComentario=(ViewHolderComentario) viewHolder;
                viewHolderComentario.txtComentario.setText(comentario.getComentario());
                viewHolderComentario.lblPerfil.setText(comentario.getPerfil());
                if (idComentarioUsuario!=null) {
                    for (int j = 0; j < idComentarioUsuario.length; j++) {
                        if (idComentarioUsuario[j] == comentario.getIdComentario()) {
                            ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.GONE);
                        }
                    }
                }else{
                    ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.GONE);
                }
            }
            break;
            default:
                Comentarios comentario = (Comentarios) list.get(position);
                ViewHolderComentario viewHolderComentario=(ViewHolderComentario) viewHolder;
                viewHolderComentario.txtComentario.setText(comentario.getComentario());
                viewHolderComentario.lblPerfil.setText(comentario.getPerfil());
                if (idComentarioUsuario!=null) {
                    for (int j = 0; j < idComentarioUsuario.length; j++) {
                        if (idComentarioUsuario[j] == comentario.getIdComentario()) {
                            ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.GONE);
                        }
                    }
                }else{
                    ((ViewHolderComentario) viewHolder).btnEditar.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblPerfil, lblReportar, lblComentario, txtComentario;
        ImageView btnEditar;
        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            lblReportar = itemView.findViewById(R.id.lblReportar);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            btnEditar=itemView.findViewById(R.id.btnEditar);
            btnEditar.setVisibility(View.GONE);
            lblReportar.setOnClickListener(this);
            btnEditar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnEditar:{
                    onComentariosListener.opcionClick(getAdapterPosition(),list);
                }
                break;
                case R.id.lblReportar:{
                    onComentariosListener.reportarComentario(getAdapterPosition(),list);
                }
                break;
            }
        }
    }
    @Override
    public int getItemViewType(int posicion){
        return list.get(posicion).getViewType();
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion,lblPerfil;
        Button btnAdvertencia,btnFavorito,btnOpcion;
        ImageView btnMiniatura;
        EditText txtComentario;
        private VideosViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            lblPerfil=itemView.findViewById(R.id.lblPerfil);
            btnMiniatura=itemView.findViewById(R.id.btnMiniatura);
            btnAdvertencia=itemView.findViewById(R.id.btnAdvertencia);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            btnFavorito.setVisibility(View.GONE);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            btnOpcion.setVisibility(View.GONE);
            txtComentario=itemView.findViewById(R.id.txtComentario);
            txtComentario.setVisibility(View.GONE);
            btnAdvertencia.setOnClickListener(this);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnOpcion:
                    onComentariosListener.opcionVideoClick(getAdapterPosition(),list);
                    break;
                case R.id.btnAdvertencia:
                    onComentariosListener.reportarVideo(getAdapterPosition(),list);
                    break;
                default:

            }

        }
    }
    public class DocumentosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView lblDescripcion,lblPerfil;
        private Button btnAdvertencia,btnFavorito,btnOpcion;
        private EditText txtComentario;
        private ImageView btnDocumento;
        private DocumentosViewHolder (View itemView){
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            btnDocumento = itemView.findViewById(R.id.btnDocumento);
            btnAdvertencia = itemView.findViewById(R.id.btnAdvertencia);
            btnFavorito = itemView.findViewById(R.id.btnFavorito);
            btnFavorito.setVisibility(View.GONE);
            btnOpcion = itemView.findViewById(R.id.btnOpcion);
            btnOpcion.setVisibility(View.GONE);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            txtComentario.setVisibility(View.GONE);
            btnAdvertencia.setOnClickListener(this);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnDocumento.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnOpcion:
                    onComentariosListener.opcionDocClick(getAdapterPosition(),list);
                    break;
                case R.id.btnAdvertencia:
                    onComentariosListener.reportarDoc(((Documentos)list.get(getAdapterPosition())).getIdVidDoc());
                    break;
                default:
            }
        }
    }

    public interface OnComentariosListener{
        void opcionClick(int position, List<Item> list);
        void opcionVideoClick(int position, List<Item> list);
        void opcionDocClick(int position, List<Item> list);
        void reportarComentario(int position, List<Item> list);
        void reportarVideo(int position, List<Item> list);
        void reportarDoc(int idVidDoc);
    }
}
