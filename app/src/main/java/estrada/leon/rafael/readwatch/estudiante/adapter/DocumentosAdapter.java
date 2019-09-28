package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.R;

public class DocumentosAdapter extends RecyclerView.Adapter<DocumentosAdapter.ViewHolder>{

    private Context context;
    private List<Documentos> documentosList;
    private OnDocumentosListener MonDocumentosListener;
    int[] idUsuarioVidDoc;
    Intent entrar;

    public DocumentosAdapter(Context context, List<Documentos> documentosList, OnDocumentosListener MonDocumentosListener, int[] idUsuarioVidDoc){
        this.context=context;
        this.documentosList=documentosList;
        this.MonDocumentosListener= MonDocumentosListener;
        this.idUsuarioVidDoc=idUsuarioVidDoc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.documentos, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView, MonDocumentosListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Documentos documento=documentosList.get(i);
        viewHolder.lblPerfil.setText(documento.getPerfil());
        viewHolder.lblDescripcion.setText(documento.getDescripcion());
        String uri = documento.getRutaImagen();
        int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        viewHolder.btnDocumento.setImageResource(imageResource);
            if (idUsuarioVidDoc!=null) {
                for (int j = 0; j < idUsuarioVidDoc.length; j++) {
                    if (idUsuarioVidDoc[j] == documento.getIdVidDoc()) {
                        viewHolder.btnOpcion.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        viewHolder.btnOpcion.setVisibility(View.GONE);
                    }
                }
            }else{
                viewHolder.btnOpcion.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return documentosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView lblDescripcion,lblPerfil;
        private Button btnAdvertencia,btnFavorito,btnOpcion;
        private EditText txtComentario;
        private ImageView btnDocumento;
        private OnDocumentosListener onDocumentosListener;
        private ViewHolder (View itemView, OnDocumentosListener onDocumentosListener){
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
            this.onDocumentosListener = onDocumentosListener;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.lblDescripcion:
                    onDocumentosListener.onDocumentosClick(getAdapterPosition(),documentosList,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.txtComentario:
                    onDocumentosListener.comentarioClick(getAdapterPosition(), documentosList);
                    break;
                case R.id.lblPerfil:
                    onDocumentosListener.perfilClick(getAdapterPosition(),documentosList);
                    break;
                case R.id.lblReportar:
                    onDocumentosListener.reportarClick();
                    break;
                case R.id.btnDocumento:
                    onDocumentosListener.onDocumentosClick(getAdapterPosition(),documentosList,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnAdvertencia:
                    onDocumentosListener.reportarClick();
                    break;
                case R.id.btnFavorito:
                    onDocumentosListener.onDocumentosClick(getAdapterPosition(),documentosList,
                            Toast.makeText(context, "Este es el botón de favoritos", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnOpcion:
                    onDocumentosListener.opcionClick(getAdapterPosition(),documentosList);
                    break;
                default:
                    onDocumentosListener.onDocumentosClick(getAdapterPosition(),documentosList,
                            Toast.makeText(context, "Este es el Video"+documentosList.get(getAdapterPosition()), Toast.LENGTH_SHORT));

            }
        }
    }

    public interface OnDocumentosListener{
        void onDocumentosClick(int position, List<Documentos> documentosList, Toast toast);
        void reportarClick();
        void perfilClick(int position, List<Documentos> documentosList);
        void comentarioClick(int position, List<Documentos> list);
        void opcionClick(int position, List<Documentos> list);
    }
}
