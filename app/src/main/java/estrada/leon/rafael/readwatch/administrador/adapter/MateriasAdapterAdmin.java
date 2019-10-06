package estrada.leon.rafael.readwatch.administrador.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

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
    RequestQueue request;
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
        MateriasAdapterAdmin.OnMateriaListener onMateriaListenerAdm;
        private MateriasViewHolder(@NonNull View itemView, MateriasAdapterAdmin.OnMateriaListener onMateriaListenerAdm) {
            super(itemView);
            btnMateria=itemView.findViewById(R.id.btnMateria);
            lblMateria=itemView.findViewById(R.id.lblMateria);
            btnOpcion = itemView.findViewById(R.id.btnOpcion);
            //this.onMateriaListener=onMateriaListener;
            this.onMateriaListenerAdm = onMateriaListenerAdm;
            btnMateria.setOnClickListener(this);
            lblMateria.setOnClickListener(this);
            btnOpcion.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnMateria){
                onMateriaListenerAdmin.onMateriaClick(getAdapterPosition(),list);
            }else if(v.getId()==R.id.btnOpcion){
                onMateriaListenerAdmin.onClickOpciones(getAdapterPosition(),list);
            }else if(v.getId()==R.id.lblMateria){
                onMateriaListenerAdmin.onMateriaClick(getAdapterPosition(),list);
            }
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.materias_adm,viewGroup,false);
        viewHolder= new MateriasAdapterAdmin.MateriasViewHolder(view, onMateriaListenerAdmin);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Materias materia= list.get(position);
        MateriasAdapterAdmin.MateriasViewHolder materiasViewHolder= (MateriasAdapterAdmin.MateriasViewHolder)viewHolder;
        if(materia.getRutaImagen().length()>30) {
            cargarImagenWebService(materia.getRutaImagen(),materiasViewHolder);
            materiasViewHolder.lblMateria.setEnabled(false);
            materiasViewHolder.lblMateria.setVisibility(View.GONE);

        }else if(materia.getRutaImagen().length()>3){
            String uri = materia.getRutaImagen();
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            materiasViewHolder.btnMateria.setImageResource(imageResource);
            materiasViewHolder.lblMateria.setEnabled(false);
            materiasViewHolder.lblMateria.setVisibility(View.GONE);
        }else{
            materiasViewHolder.btnMateria.setEnabled(false);
            materiasViewHolder.btnMateria.setVisibility(View.GONE);
            materiasViewHolder.lblMateria.setText(materia.getNombre());
        }
    }

    public void cargarImagenWebService(String rutaImagen, final MateriasAdapterAdmin.MateriasViewHolder materiasViewHolder){
        request= Volley.newRequestQueue(context);
        ImageRequest imageRequest= new ImageRequest(rutaImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                materiasViewHolder.btnMateria.setImageBitmap(response);
            }

        },0,0,ImageView.ScaleType.CENTER,null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Error al cargar las imagenes", Toast.LENGTH_SHORT).show();
            }});
        request.add(imageRequest);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnMateriaListener{
        void onClickOpciones(int position,List<Materias>lista);
        void onMateriaClick(int position,List<Materias> lista);
    }
}

