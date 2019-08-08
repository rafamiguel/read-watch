package estrada.leon.rafael.readwatch.Estudiante.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.Videos;
import estrada.leon.rafael.readwatch.R;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Videos> list;
    OnVideoListener mOnVideoListener;

    public VideosAdapter(Context context, List<Videos> list, OnVideoListener onVideoListener) {
    this.context=context;
    this.list=list;
    this.mOnVideoListener = onVideoListener;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView lblDescripcion;
    OnVideoListener onVideoListener;
    Button btnVideo;
        public VideosViewHolder(@NonNull View itemView, OnVideoListener onVideoListener) {
            super(itemView);
            lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
            itemView.setOnClickListener(this);
            this.onVideoListener = onVideoListener;
            //btnVideo.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onVideoListener.onVideoClick(getAdapterPosition(),list);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos,viewGroup,false);
        viewHolder=new VideosViewHolder(view, mOnVideoListener);
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

    public interface OnVideoListener{
        void onVideoClick(int position,List<Videos> list);
    }

}
