package estrada.leon.rafael.readwatch.Estudiante.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.AdapterVideo;
import estrada.leon.rafael.readwatch.Estudiante.POJO.POJOVideos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder>{

    Context context;
    List<POJOVideos> POJOVideosList;

    public VideosAdapter(Context context, List<POJOVideos> POJOVideosList){
        this.context = context;
        this.POJOVideosList = POJOVideosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemvideo, viewGroup, false);
        VideosAdapter.ViewHolder viewHolder = new VideosAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.btnAdvertencia.setText(POJOVideosList.get(i).getIconoAdvertencia());
        viewHolder.btnOpcion.setText(POJOVideosList.get(i).getOpciones());
        viewHolder.btnFavorito.setText(POJOVideosList.get(i).getEstrellita());
        viewHolder.btnEditar.setText(POJOVideosList.get(i).getEditar());
        viewHolder.txtComentario.setText(POJOVideosList.get(i).getComentario());
        viewHolder.txtReportar.setText(POJOVideosList.get(i).getReportar());
        viewHolder.txtDescripcion.setText(POJOVideosList.get(i).getDescripcion());
        viewHolder.txtPerfil.setText(POJOVideosList.get(i).getPerfil());
        viewHolder.btnMiniatura.setText(POJOVideosList.get(i).getMiniatura());
    }

    @Override
    public int getItemCount() {
        return POJOVideosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtPerfil, txtDescripcion, txtReportar;
        EditText txtComentario;
        Button btnEditar, btnFavorito, btnOpcion, btnAdvertencia, btnMiniatura;

        public ViewHolder (View itemVideo){
            super(itemVideo);
            btnMiniatura = itemVideo.findViewById(R.id.btnMiniatura);
            txtPerfil = itemVideo.findViewById(R.id.txtPerfil);
            txtDescripcion = itemVideo.findViewById(R.id.txtDescripcion);
            txtReportar = itemVideo.findViewById(R.id.txtReportar);
            txtComentario = itemVideo.findViewById(R.id.txtComentario);
            btnEditar = itemVideo.findViewById(R.id.btnEditar);
            btnFavorito = itemVideo.findViewById(R.id.btnFavorito);
            btnOpcion = itemVideo.findViewById(R.id.btnOpcion);
            btnAdvertencia = itemVideo.findViewById(R.id.btnAdvertencia);


        }
    }

}
