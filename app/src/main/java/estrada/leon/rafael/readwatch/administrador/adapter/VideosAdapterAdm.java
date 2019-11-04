package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.content.Intent;
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
import estrada.leon.rafael.readwatch.administrador.pojo.VideosAdm;
import estrada.leon.rafael.readwatch.estudiante.adapter.VideosAdapter;

public class VideosAdapterAdm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<VideosAdm> list;
    private OnVideoAdmListener onVideoAdmListener;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    Intent entrar;
    String nombre;

    public VideosAdapterAdm(Context context, List<VideosAdm> list, OnVideoAdmListener onVideoAdmListener) {
        this.context = context;
        this.list = list;
        this.onVideoAdmListener = onVideoAdmListener;
    }

    public class VideosAdmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion, lblPerfil, txtComentario;
        OnVideoAdmListener onVideoAdmListener;
        WebView btnMiniatura;
        Button btnOpcion;


        private VideosAdmViewHolder(@NonNull View itemView, OnVideoAdmListener onVideoAdmListener) {
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            btnMiniatura = itemView.findViewById(R.id.btnMiniatura);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            btnOpcion=itemView.findViewById(R.id.btnOpcion);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
            btnMiniatura.getSettings().setJavaScriptEnabled(true);
            btnMiniatura.setWebChromeClient(new WebChromeClient() {

            } );
            btnOpcion.setOnClickListener(this);
            txtComentario.setOnClickListener(this);
            this.onVideoAdmListener = onVideoAdmListener;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.lblDescripcion:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la descripcion", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onVideoAdmListener.perfilClick(getAdapterPosition(),list);
                    break;
                case R.id.txtComentario:
                    onVideoAdmListener.comentarioClick(getAdapterPosition(),list);
                    break;
                case R.id.btnOpcion:
                    onVideoAdmListener.opcionClick(getAdapterPosition(),list);
                    break;
                case R.id.btnMiniatura:
                   // onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                     //       Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                default:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Este es el Video" + list.get(getAdapterPosition()), Toast.LENGTH_SHORT));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        request = Volley.newRequestQueue(context);
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos_adm,viewGroup,false);
        viewHolder= new VideosAdmViewHolder(view, onVideoAdmListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        VideosAdm video = list.get(position);
        VideosAdmViewHolder videosViewHolder = (VideosAdmViewHolder) viewHolder;
        videosViewHolder.lblDescripcion.setText(video.getDescripcion());
        obtenerNombre(video.getPerfil(), videosViewHolder);
        //videosViewHolder.lblPerfil.setText(video.getPerfil());

        String uri = video.getRutaImagen();
        int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        String url= video.getVideoUrl();
        videosViewHolder.btnMiniatura.loadData(url, "text/html" , "utf-8" );

        videosViewHolder.btnOpcion.setVisibility(View.VISIBLE);


    }

    private void obtenerNombre(String idUsuario, final VideosAdmViewHolder viewHolder) {
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


    public interface OnVideoAdmListener {
        void onVideoClick(int position, List<VideosAdm> list, Toast toast);
        void perfilClick(int position, List<VideosAdm> list);
        void comentarioClick(int position, List<VideosAdm> list);
        void opcionClick(int adapterPosition, List<VideosAdm> list);
    }
}
