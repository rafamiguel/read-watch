package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.Videos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Videos> list;
    public VideosAdapter(Context context, List<Videos> list) {
    this.context=context;
    this.list=list;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder{
    TextView lblDescripcion;
        public VideosViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos,viewGroup,false);
        viewHolder=new VideosViewHolder(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Videos video = list.get(i);
        VideosViewHolder videosViewHolder = (VideosViewHolder) viewHolder;
        videosViewHolder.lblDescripcion.setText(video.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
