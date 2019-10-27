package estrada.leon.rafael.readwatch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class VideosYouTubeAdapter extends RecyclerView.Adapter<VideosYouTubeAdapter.VideoViewHolder>{

    List<YouTubeVideos> videosList;

    public VideosYouTubeAdapter(){

    }

    public VideosYouTubeAdapter(List<YouTubeVideos> videosList){
        this.videosList=videosList;
    }

    @NonNull
    @Override
    public VideosYouTubeAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.videoWeb.loadData(videosList.get(i).getVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoWeb =  itemView.findViewById(R.id.videoWebView);
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {

            } );
        }
    }
}
