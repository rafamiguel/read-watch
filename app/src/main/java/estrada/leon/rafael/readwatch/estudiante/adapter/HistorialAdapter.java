package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.pojo.Historial;
import estrada.leon.rafael.readwatch.R;

public class HistorialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Historial> list;
    private final int comentario=1;
    private final int videoOdocumento=2;
    public HistorialAdapter(Context context,List<Historial> list){
    this.context=context;
    this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case comentario:
                view= LayoutInflater.from(context).inflate(R.layout.comentario_borrado,viewGroup,false);
                viewHolder=new ComentarioViewHolder(view);
                break;
                case videoOdocumento:
                    view= LayoutInflater.from(context).inflate(R.layout.video_documento_borrado,viewGroup,false);
                    viewHolder=new VidDocViewHolder(view);
                    break;
                    default:
                        view= LayoutInflater.from(context).inflate(R.layout.video_documento_borrado,viewGroup,false);
                        viewHolder=new VidDocViewHolder(view);
                        break;
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        VidDocViewHolder vidDocViewHolder;
    switch(getItemViewType(position)){
        case comentario:
            ComentarioViewHolder comentarioViewHolder=(ComentarioViewHolder)viewHolder;
            comentarioViewHolder.lblCastigo.setText("Castigo:\n"+list.get(position).getCastigo());
            comentarioViewHolder.lblMotivo.setText("Motivo:\n"+list.get(position).getMotivo());
            break;
        case videoOdocumento:
            vidDocViewHolder=(VidDocViewHolder)viewHolder;
            //vidDocViewHolder.img.setImageResource(list.get(position).getRutaImagen());
            vidDocViewHolder.lblCastigo.setText("Castigo:\n"+list.get(position).getCastigo());
            vidDocViewHolder.lblMotivo.setText("Motivo:\n"+list.get(position).getMotivo());
            vidDocViewHolder.lblLinkNombre.setText(list.get(position).getRuta());
            if (list.get(position).getRutaImagen().equals("@drawable/miniatura")) {
                vidDocViewHolder.img.setImageResource(R.drawable.miniatura);
            }else{vidDocViewHolder.img.setImageResource(R.drawable.doc);
                vidDocViewHolder.lblLinkNombre.setText(list.get(position).getRuta()+".pdf");
            }
            break;

            default:
                vidDocViewHolder=(VidDocViewHolder)viewHolder;
                // vidDocViewHolder.img.setBackgroundResource(list.get(position).getId());
                vidDocViewHolder.lblCastigo.setText(list.get(position).getCastigo());
                vidDocViewHolder.lblMotivo.setText(list.get(position).getMotivo());
                vidDocViewHolder.lblLinkNombre.setText(list.get(position).getRuta());

    }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public int getItemViewType(int posicion){
        return list.get(posicion).getViewType();
    }

    public class ComentarioViewHolder extends RecyclerView.ViewHolder{
        TextView lblComentario,lblMotivo,lblCastigo;
        private ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            lblComentario=itemView.findViewById(R.id.lblComentario);
            lblMotivo=itemView.findViewById(R.id.lblMotivo);
            lblCastigo=itemView.findViewById(R.id.lblCastigo);
        }
    }
    public class VidDocViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView lblLinkNombre,lblMotivo,lblCastigo;
        private VidDocViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            lblLinkNombre=itemView.findViewById(R.id.lblLinkNombre);
            lblMotivo=itemView.findViewById(R.id.lblMotivo);
            lblCastigo=itemView.findViewById(R.id.lblCastigo);
        }

    }
}
