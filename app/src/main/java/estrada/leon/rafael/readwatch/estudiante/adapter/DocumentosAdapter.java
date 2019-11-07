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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.R;

public class DocumentosAdapter extends RecyclerView.Adapter<DocumentosAdapter.ViewHolder> {

    private Context context;
    private List<Documentos> documentosList;
    private OnDocumentosListener MonDocumentosListener;
    private int[] idUsuarioVidDoc;
    private int[] idUsuarioVidDocFav;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    String nombre;



    public DocumentosAdapter(Context context, List<Documentos> documentosList, OnDocumentosListener MonDocumentosListener, int[] idUsuarioVidDoc, int[] idUsuarioVidDocFav) {
        this.context = context;
        this.documentosList = documentosList;
        this.MonDocumentosListener = MonDocumentosListener;
        this.idUsuarioVidDoc = idUsuarioVidDoc;
        this.idUsuarioVidDocFav = idUsuarioVidDocFav;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.documentos, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView, MonDocumentosListener);
        request = Volley.newRequestQueue(context);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Documentos documento = documentosList.get(i);
        obtenerNombre(documento.getPerfil(), viewHolder);
        viewHolder.lblDescripcion.setText(documento.getDescripcion());
        if (idUsuarioVidDocFav != null) {
            for (int j = 0; j < idUsuarioVidDocFav.length; j++) {
                if (idUsuarioVidDocFav[j] == documento.getIdVidDoc()) {
                    viewHolder.btnFavorito.setBackgroundResource(R.drawable.favorito);
                    break;
                } else {
                    viewHolder.btnFavorito.setBackgroundResource(R.drawable.star2);
                }
            }

        }
        if (idUsuarioVidDoc != null) {
            for (int j = 0; j < idUsuarioVidDoc.length; j++) {
                if (idUsuarioVidDoc[j] == documento.getIdVidDoc()) {
                    viewHolder.btnOpcion.setVisibility(View.VISIBLE);
                    break;
                } else {
                    viewHolder.btnOpcion.setVisibility(View.GONE);
                }
            }
        } else {
            viewHolder.btnOpcion.setVisibility(View.GONE);
        }
        viewHolder.btnDocumento.setImageBitmap(documento.getImagen());
    }


    private void obtenerNombre(String idUsuario, final ViewHolder viewHolder) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/obtenerNombre.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");


                try {
                    jsonObject = json.getJSONObject(0);
                    nombre = jsonObject.getString("nombre");
                    viewHolder.lblPerfil.setText(nombre);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);

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
                    onDocumentosListener.reportarClick(getAdapterPosition(),documentosList);
                    break;
                case R.id.btnDocumento:
                    onDocumentosListener.onDocumentosClick(getAdapterPosition(),documentosList,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    onDocumentosListener.leerDocumento(documentosList.get(getAdapterPosition()).getIdVidDoc());
                    break;
                case R.id.btnAdvertencia:
                    onDocumentosListener.reportarClick(getAdapterPosition(),documentosList);
                    break;
                case R.id.btnFavorito:
                    onDocumentosListener.agregarFavoritos(getAdapterPosition(),documentosList);
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
        void reportarClick(int position, List<Documentos> documentosList);
        void perfilClick(int position, List<Documentos> documentosList);
        void comentarioClick(int position, List<Documentos> list);
        void opcionClick(int position, List<Documentos> list);
        void agregarFavoritos(int adapterPosition, List<Documentos> documentosList);
        void leerDocumento(int idVidDoc);
    }
}