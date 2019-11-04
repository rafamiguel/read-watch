package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.fragment.MainComentarios;
import estrada.leon.rafael.readwatch.administrador.pojo.DocumentosAdm;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;

public class DocumentosAdapterAdm extends RecyclerView.Adapter<DocumentosAdapterAdm.ViewHolder> {

    private Context context;
    private List<DocumentosAdm> list;
    private OnDocumentosAdmListener onDocumentosAdmListener;
    Intent entrar;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;;
    String nombre;

    public DocumentosAdapterAdm(Context context, List<DocumentosAdm> list, OnDocumentosAdmListener onDocumentosAdmListener) {
        this.context = context;
        this.list = list;
        this.onDocumentosAdmListener = onDocumentosAdmListener;
    }

    @NonNull
    @Override
    public DocumentosAdapterAdm.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.documentos_adm, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView, onDocumentosAdmListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
        request = Volley.newRequestQueue(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DocumentosAdm documento= list.get(i);
        obtenerNombre(documento.getPerfil(), viewHolder);
        // viewHolder.lblPerfil.setText(list.get(i).getPerfil());
        viewHolder.lblDescripcion.setText(list.get(i).getDescripcion());
        String uri = list.get(i).getRutaImagen();
        int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        viewHolder.btnDocumento.setImageResource(imageResource);
        viewHolder.btnOpcion.setVisibility(View.VISIBLE);
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
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView lblDescripcion,lblPerfil, txtComentario;
        private ImageView btnDocumento;
        private OnDocumentosAdmListener onDocumentosAdmListener;
        private Button btnOpcion;
        private ViewHolder (View itemView, OnDocumentosAdmListener onDocumentosAdmListener){
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            btnDocumento = itemView.findViewById(R.id.btnDocumento);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            btnOpcion = itemView.findViewById(R.id.btnOpcion);

            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnDocumento.setOnClickListener(this);
            txtComentario.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
            this.onDocumentosAdmListener = onDocumentosAdmListener;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.lblDescripcion:
                    onDocumentosAdmListener.onDocumentosClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.txtComentario:
                    onDocumentosAdmListener.comentarioClick(getAdapterPosition(),list);
                    break;
                case R.id.lblPerfil:
                    onDocumentosAdmListener.perfilClick(getAdapterPosition(),list);
                    break;
                case R.id.btnOpcion:
                    onDocumentosAdmListener.opcionClick(getAdapterPosition(),list);
                    break;
                case R.id.btnDocumento:
                    onDocumentosAdmListener.onDocumentosClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                default:
                    onDocumentosAdmListener.onDocumentosClick(getAdapterPosition(),list,
                            Toast.makeText(context, "Este es el Video"+list.get(getAdapterPosition()), Toast.LENGTH_SHORT));

            }
        }
    }

    public interface OnDocumentosAdmListener{
        void onDocumentosClick(int position, List<DocumentosAdm> list, Toast toast);

        void comentarioClick(int adapterPosition, List<DocumentosAdm> list);

        void opcionClick(int adapterPosition, List<DocumentosAdm> list);

        void perfilClick(int adapterPosition, List<DocumentosAdm> list);
    }
}
