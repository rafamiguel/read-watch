package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import estrada.leon.rafael.readwatch.DialogOpciones;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;

public class MateriasAdapterAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Materias> list;
    private MateriasAdapter.OnMateriaListener onMateriaListener;
    private MateriasAdapterAdmin.OnMateriaListener onMateriaListenerAdmin;
    iComunicacionFragmentsAdm interfaceFragments;
    Button btnOpcion;

    public MateriasAdapterAdmin(Context context, List<Materias> list, MateriasAdapter.OnMateriaListener onMateriaListener, MateriasAdapterAdmin.OnMateriaListener onMateriaListenerAdmin) {
        this.context=context;
        this.list=list;
        this.onMateriaListener = onMateriaListener;
        this.onMateriaListenerAdmin = onMateriaListenerAdmin;

    }
    public class MateriasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView btnMateria;
        TextView lblMateria;

        MateriasAdapter.OnMateriaListener onMateriaListener;
        private MateriasViewHolder(@NonNull View itemView, MateriasAdapter.OnMateriaListener onMateriaListener) {
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
        view= LayoutInflater.from(context).inflate(R.layout.materias_adm,viewGroup,false);
        btnOpcion = view.findViewById(R.id.btnOpcion);
        btnOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMateriaListenerAdmin.onClickOpciones();

            }
        });
        viewHolder= new MateriasAdapterAdmin.MateriasViewHolder(view,onMateriaListener);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Materias materia= list.get(position);
        MateriasAdapterAdmin.MateriasViewHolder materiasViewHolder= (MateriasAdapterAdmin.MateriasViewHolder)viewHolder;
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
        public void onClickOpciones();
    }
}

