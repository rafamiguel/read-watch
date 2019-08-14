package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.pojo.InactivoAdm;

public class UsuarioInactivoAdapter extends RecyclerView.Adapter<UsuarioInactivoAdapter.ViewHolder>{

    Context context;
    List<InactivoAdm> list;
    OnInactivoListener onInactivoListener;

    public UsuarioInactivoAdapter (Context context, List<InactivoAdm> list, OnInactivoListener onInactivoListener){
        this.onInactivoListener=onInactivoListener;
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.usuario_inactivo, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView, onInactivoListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.btnPerfil.setText(list.get(i).getFotoperfil());
        viewHolder.lblPerfil.setText(list.get(i).getPerfil());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnPerfil,btnEliminar;
        TextView lblPerfil;
        OnInactivoListener onInactivoListener;
        public ViewHolder (View item, OnInactivoListener onInactivoListener){
            super(item);
            btnPerfil = item.findViewById(R.id.btnPerfil);
            btnEliminar = item.findViewById(R.id.btnEliminar);
            lblPerfil = item.findViewById(R.id.lblPerfil);


            btnPerfil.setOnClickListener(this);
            btnEliminar.setOnClickListener(this);
            lblPerfil.setOnClickListener(this);

            this.onInactivoListener = onInactivoListener;
            item.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btnPerfil:
                    onInactivoListener.OnInactivoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Esta es la foto del perfil", Toast.LENGTH_SHORT));
                    break;
                case R.id.btnEliminar:
                    onInactivoListener.OnInactivoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Botón para eliminar usuario", Toast.LENGTH_SHORT));
                    break;
                case R.id.lblPerfil:
                    onInactivoListener.OnInactivoClick(getAdapterPosition(), list,
                            Toast.makeText(context, "Nombre del perfil", Toast.LENGTH_SHORT));
                    break;
            }
        }
    }
    public interface OnInactivoListener{
        void OnInactivoClick (int posicion, List<InactivoAdm> list, Toast toast);
    }
}
