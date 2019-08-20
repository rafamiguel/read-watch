package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.pojo.VideosAdm;

public class VideosAdapterAdm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<VideosAdm> list;
    private OnVideoAdmListener onVideoAdmListener;

    public VideosAdapterAdm(Context context, List<VideosAdm> list, OnVideoAdmListener onVideoAdmListener) {
        this.context = context;
        this.list = list;
        this.onVideoAdmListener = onVideoAdmListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.videos_adm,viewGroup,false);
        viewHolder=new VideosAdmViewHolder(view, onVideoAdmListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VideosAdmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblDescripcion, lblPerfil, lblReportar;
        OnVideoAdmListener onVideoAdmListener;
        ImageView btnMiniatura;
        EditText txtComentario;

        private VideosAdmViewHolder(@NonNull View itemView, OnVideoAdmListener onVideoAdmListener) {
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPerfil = itemView.findViewById(R.id.lblPerfil);
            lblReportar = itemView.findViewById(R.id.lblReportar);
            btnMiniatura = itemView.findViewById(R.id.btnMiniatura);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            lblDescripcion.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);
            lblReportar.setOnClickListener(this);
            btnMiniatura.setOnClickListener(this);
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
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Este es el perfil", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnMiniatura:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la miniatura", Toast.LENGTH_SHORT));
                    break;
                 default:
                    onVideoAdmListener.onVideoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Este es el Video" + list.get(getAdapterPosition()), Toast.LENGTH_SHORT));
            }
        }
    }
    public interface OnVideoAdmListener {
        void onVideoClick(int position, List<VideosAdm> list, Toast toast);
    }
}
