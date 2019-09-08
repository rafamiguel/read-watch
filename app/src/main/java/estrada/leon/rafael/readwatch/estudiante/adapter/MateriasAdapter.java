package estrada.leon.rafael.readwatch.estudiante.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.R;

public class MateriasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Materias> list;
    private OnMateriaListener onMateriaListener;

    public MateriasAdapter(Context context, List<Materias> list, OnMateriaListener onMateriaListener) {
        this.context=context;
        this.list=list;
        this.onMateriaListener = onMateriaListener;
    }
    public class MateriasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView btnMateria;
        TextView lblMateria;
        OnMateriaListener onMateriaListener;
        private MateriasViewHolder(@NonNull View itemView, OnMateriaListener onMateriaListener) {
            super(itemView);
            btnMateria=itemView.findViewById(R.id.btnMateria);
            lblMateria=itemView.findViewById(R.id.lblMateria);
            this.onMateriaListener=onMateriaListener;
            btnMateria.setOnClickListener(this);
            lblMateria.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnMateria){
                onMateriaListener.onMateriaClick(getAdapterPosition(),list);
            }else if(v.getId()==R.id.lblMateria){
                onMateriaListener.onMateriaClick(getAdapterPosition(),list);
            }
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.materia,viewGroup,false);
        viewHolder=new MateriasViewHolder(view,onMateriaListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Materias materia= list.get(position);
        MateriasViewHolder materiasViewHolder= (MateriasViewHolder)viewHolder;
        if(materia.getRutaImagen()!="") {
            String uri = materia.getRutaImagen();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            materiasViewHolder.lblMateria.setEnabled(false);
            materiasViewHolder.lblMateria.setVisibility(View.GONE);
            materiasViewHolder.btnMateria.setImageResource(imageResource);
        }else{
            materiasViewHolder.btnMateria.setEnabled(false);
            materiasViewHolder.btnMateria.setVisibility(View.GONE);
            materiasViewHolder.lblMateria.setText(materia.getNombre());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnMateriaListener{
        void onMateriaClick(int position,List<Materias> lista);
    }
}
