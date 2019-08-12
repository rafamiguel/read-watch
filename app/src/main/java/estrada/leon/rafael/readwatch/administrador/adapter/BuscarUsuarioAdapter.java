package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.administrador.pojo.BuscarUsuarioAd;
import estrada.leon.rafael.readwatch.R;

public class BuscarUsuarioAdapter extends RecyclerView.Adapter<BuscarUsuarioAdapter.ViewHolder>{

    Context context;
    List<BuscarUsuarioAd> list;

    public BuscarUsuarioAdapter(Context context, List<BuscarUsuarioAd>list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buscarusuario,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.lblPerfil.setText(list.get(i).getPerfil());
       viewHolder.btnPerfil.setText(list.get(i).getRutaImagen());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnEliminar;
        Button btnPerfil;
        TextView lblPerfil;
        public ViewHolder (View item){
            super(item);
            btnPerfil = item.findViewById(R.id.btnPerfil);
            btnEliminar = item.findViewById(R.id.btnEliminar);
            lblPerfil = item.findViewById(R.id.lblPerfil);
        }
    }
}
