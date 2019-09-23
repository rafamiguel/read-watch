package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
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

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ViewHolderComentario> {

    Context context;
    List<Item> list;
    private final int DOCUMENTO=1;
    private final int VIDEO=2;
    private final int COMENTARIO=3;

    public AdapterComentario(Context context, List<Item> list){
        this.context = context;
        this.list=list;

    }


    @NonNull
    @Override
    public ViewHolderComentario onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vistadecomentario, viewGroup, false );
        ViewHolderComentario viewHolderComentario = new ViewHolderComentario(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);


        return viewHolderComentario;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComentario viewHolder, int position) {

        switch(getItemViewType(position)){
            case DOCUMENTO: {
                Documentos documento = (Documentos) list.get(position);
               // DocumentosViewHolder documentosViewHolder = (DocumentosViewHolder) viewHolder;
                //documentosViewHolder.lblPerfil.setText(documento.getPerfil());
               // documentosViewHolder.lblDescripcion.setText(documento.getDescripcion());
                break;
            }
            case VIDEO:{
                Videos video=(Videos)list.get(position);
                //VideosViewHolder videosViewHolder=(VideosViewHolder) viewHolder;
               // videosViewHolder.lblPerfil.setText(video.getPerfil());
               // videosViewHolder.lblDescripcion.setText(video.getDescripcion());
                String uri = video.getRutaImagen();
                int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
               // videosViewHolder.btnMiniatura.setImageResource(imageResource);
                break;
            }
            case COMENTARIO:{

            }
            break;
            default:
               // viewHolder.txtComentario.setText((Comentarios)list.get(position).getComentario());
                //viewHolder.lblPerfil.setText((Comentarios)list.get(position).getPerfil());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderComentario extends RecyclerView.ViewHolder {
        TextView lblPerfil, lblReportar, lblComentario, txtComentario;

        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            lblReportar = itemView.findViewById(R.id.lblReportar);
            txtComentario = itemView.findViewById(R.id.txtComentario);


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
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            txtComentario=itemView.findViewById(R.id.txtComentario);
            txtComentario.setVisibility(View.GONE);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnFavorito.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.lblDescripcion:

                    break;

                case R.id.lblPerfil:
                    break;
                case R.id.txtComentario:

                    break;
                case R.id.lblReportar:

                    break;
                case R.id.btnMiniatura:

                    break;
                case R.id.btnAdvertencia:

                    break;
                case R.id.btnFavorito:

                    break;
                case R.id.btnOpcion:

                    break;
                case R.id.btnEditar:

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
            btnOpcion = itemView.findViewById(R.id.btnOpcion);
            txtComentario = itemView.findViewById(R.id.txtComentario);

            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnDocumento.setOnClickListener(this);
            btnAdvertencia.setOnClickListener(this);
            btnFavorito.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            txtComentario.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.lblDescripcion:
                    break;
                case R.id.txtComentario:
                    break;
                case R.id.lblPerfil:
                    break;
                case R.id.lblReportar:
                    break;
                case R.id.btnDocumento:
                    break;
                case R.id.btnAdvertencia:
                    break;
                case R.id.btnFavorito:
                    break;
                case R.id.btnOpcion:
                    break;
                case R.id.btnEditar:
                    break;
                default:
            }
        }
    }
}
