package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import estrada.leon.rafael.readwatch.administrador.pojo.BuscarUsuarioAd;
import estrada.leon.rafael.readwatch.R;

public class BuscarUsuarioAdapter extends RecyclerView.Adapter<BuscarUsuarioAdapter.ViewHolder>{

    Context context;
    List<BuscarUsuarioAd> list;
    OnBuscarListener onBuscarListener;
    Response.Listener<JSONObject> a;
    RequestQueue request;
    public BuscarUsuarioAdapter(Context context, List<BuscarUsuarioAd>list, OnBuscarListener onBuscarListener){
        this.context = context;
        this.list = list;
        this.onBuscarListener = onBuscarListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buscarusuario ,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(item, onBuscarListener);
        request= Volley.newRequestQueue(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.lblPerfil.setText(list.get(i).getPerfil());
       viewHolder.lblApellido.setText(list.get(i).getApellido());

        request= Volley.newRequestQueue(context);
        ImageRequest imageRequest= new ImageRequest(list.get(i).getRutaImagen(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                viewHolder.btnPerfil.setImageBitmap(response);
            }

        },0,0, ImageView.ScaleType.CENTER,null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Error al cargar las imagenes", Toast.LENGTH_SHORT).show();
            }});
        request.add(imageRequest);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView btnPerfil,btnEliminar;
        TextView lblPerfil, lblApellido;
        OnBuscarListener onBuscarListener;
        public ViewHolder (View item, OnBuscarListener onBuscarListener){
            super(item);
            this.onBuscarListener = onBuscarListener;
            btnPerfil = item.findViewById(R.id.btnPerfil);
            lblPerfil = item.findViewById(R.id.lblPerfil);
            lblApellido = item.findViewById(R.id.lblApellido);

            btnPerfil.setOnClickListener(this);

            lblPerfil.setOnClickListener(this);

            item.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btnPerfil:
                    onBuscarListener.OnBuscarClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la foto del perfil", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onBuscarListener.OnBuscarClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Nombre del perfil", Toast.LENGTH_SHORT));
                    break;
            }
        }

        public void cargarFoto(String foto) {

        }


    }



    public interface OnBuscarListener{
        void OnBuscarClick (int posicion, List<BuscarUsuarioAd> list, Toast toast);
    }
}
