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

import estrada.leon.rafael.readwatch.Estudiante.POJO.POJOVideos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<POJOVideos> pojoVideosList;

    public VideosAdapter(Context context, List<POJOVideos> pojoVideosList){
        this.context = context;
        this.pojoVideosList = pojoVideosList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos, viewGroup, false);
        viewHolder = new VideosViewHolder(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return pojoVideosList.size();
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder{
        TextView txtPerfil, txtDescripcion, txtReportar;
        EditText txtComentario;
        Button btnEditar, btnFavorito, btnOpcion, btnAdvertencia, btnMiniatura;

        public VideosViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
