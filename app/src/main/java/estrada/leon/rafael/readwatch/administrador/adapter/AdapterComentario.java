package estrada.leon.rafael.readwatch.administrador.adapter;

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

import estrada.leon.rafael.readwatch.administrador.pojo.PojoComentario;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.ViewHolderComentario> {

    Context context;
    List<Item> list;
    private OnComentariosListener onComentariosListener;
    private int []idComentarioUsuario;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    String nombre;

    public AdapterComentario(Context context, List<Item> list, OnComentariosListener onComentariosListener, int []idComentarioUsuario){
        this.context = context;
        this.list=list;
        this.onComentariosListener=onComentariosListener;
        this.idComentarioUsuario=idComentarioUsuario;

    }


    @NonNull
    @Override
    public ViewHolderComentario onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vista_comentario_recycler, viewGroup, false );
        ViewHolderComentario viewHolderComentario = new ViewHolderComentario(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        request = Volley.newRequestQueue(context);
        return viewHolderComentario;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComentario viewHolderComentario, int i) {
        Comentarios comentario = (Comentarios) list.get(i);
        viewHolderComentario.txtComentario.setText(comentario.getComentario());
        obtenerNombreCom(comentario.getPerfil(),viewHolderComentario);
        //viewHolderComentario.lblPerfil.setText(comentario.getPerfil());
        viewHolderComentario.btnEliminar.setVisibility(View.VISIBLE);

    }

    private void obtenerNombreCom(String idUsuario, final ViewHolderComentario viewHolderComentario) {
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
                    viewHolderComentario.lblPerfil.setText(nombre);
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

    public class ViewHolderComentario extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblPerfil,  txtComentario;
        ImageView btnEliminar;

        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEliminar.setVisibility(View.VISIBLE);
            btnEliminar.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnEliminar:{
                    onComentariosListener.opcionClick(getLayoutPosition(),list);
                }

            }
        }
    }
    public interface OnComentariosListener{
        void opcionClick(int position, List<Item> list);
    }
}