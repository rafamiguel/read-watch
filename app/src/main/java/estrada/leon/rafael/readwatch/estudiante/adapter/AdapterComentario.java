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
    boolean reportar=true;
    boolean favorito=true;
    boolean opcion=true;

    public AdapterComentario(Context context, List<Item> list){
        this.context = context;
        this.list=list;
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
                break;
            }
            case COMENTARIO:{
                Comentarios comentario = (Comentarios) list.get(position);
                ViewHolderComentario viewHolderComentario=(ViewHolderComentario) viewHolder;
                viewHolderComentario.txtComentario.setText(comentario.getComentario());
                viewHolderComentario.lblPerfil.setText(comentario.getPerfil());
            }
            break;
            default:
                Comentarios comentario = (Comentarios) list.get(position);
                ViewHolderComentario viewHolderComentario=(ViewHolderComentario) viewHolder;
                viewHolderComentario.txtComentario.setText(comentario.getComentario());
                viewHolderComentario.lblPerfil.setText(comentario.getPerfil());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder {
        TextView lblPerfil, lblReportar, lblComentario, txtComentario;
        ImageView btnEditar;
        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            lblReportar = itemView.findViewById(R.id.lblReportar);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            btnEditar=itemView.findViewById(R.id.btnEditar);
            btnEditar.setVisibility(View.GONE);

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
            btnAdvertencia.setVisibility(View.GONE);
            btnFavorito=itemView.findViewById(R.id.btnFavorito);
            btnFavorito.setVisibility(View.GONE);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            btnOpcion.setVisibility(View.GONE);
            txtComentario=itemView.findViewById(R.id.txtComentario);
            txtComentario.setVisibility(View.GONE);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){

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
            btnAdvertencia.setVisibility(View.GONE);
            btnFavorito = itemView.findViewById(R.id.btnFavorito);
            btnFavorito.setVisibility(View.GONE);
            btnOpcion = itemView.findViewById(R.id.btnOpcion);
            btnOpcion.setVisibility(View.GONE);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            txtComentario.setVisibility(View.GONE);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnDocumento.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){

                default:
            }
        }
    }
}
