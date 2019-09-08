package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirMateria extends Fragment implements MateriasAdapter.OnMateriaListener,
        Response.Listener<JSONObject>, Response.ErrorListener{
    private iComunicacionFragments interfaceFragments;
    private Activity actividad;
    private List<Materias> listMaterias,listMateriasPropuestas;
    private RecyclerView recyclerMaterias,recyclerMateriasPropuestas;
    private MateriasAdapter materiasAdapter;

    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    private OnFragmentInteractionListener mListener;

    public ElegirMateria() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView lblTemaInexistente;
        View vista;
        vista=inflater.inflate(R.layout.fragment_elegir_materia, container, false);
        lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
        lblTemaInexistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceFragments.onClickProponerMateria();
            }
        });
        recyclerMaterias=vista.findViewById(R.id.recyclerMaterias);
        recyclerMateriasPropuestas=vista.findViewById(R.id.recyclerMateriasPropuestas);
        recyclerMaterias.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerMateriasPropuestas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        request= Volley.newRequestQueue(getContext());
        cargarWebService();

        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragments)actividad;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMateriaClick(int position, List<Materias> lista) {
        if(lista.equals(listMaterias)){
            interfaceFragments.seleccionarSemestre(listMaterias.get(position).getIdMateria());
        }else if(lista.equals(listMateriasPropuestas)){
            interfaceFragments.seleccionarSemestre(listMateriasPropuestas.get(position).getIdMateria());
        }

    }

    private void cargarWebService() {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarMaterias.php";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        listMaterias=new ArrayList<>();
        listMateriasPropuestas = new ArrayList<>();
        JSONArray json;
        JSONObject jsonObject=null;
        Materias materia;
        json = response.optJSONArray("usuario");
        String nombre,rutaImagen;
        int idMateria, votos,idUsuario;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                idMateria=jsonObject.optInt("idMateria");
                nombre=jsonObject.optString("nombre");
                rutaImagen=jsonObject.optString("rutaImagen");
                votos=jsonObject.optInt("votos");
                idUsuario=jsonObject.optInt("idUsuario");
                materia=new Materias(idMateria,rutaImagen,nombre);

                if(idUsuario==1) {
                    listMaterias.add(materia);
                }else{
                    listMateriasPropuestas.add(materia);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        materiasAdapter = new MateriasAdapter(getContext(), listMaterias, this);
        recyclerMaterias.setAdapter(materiasAdapter);
        materiasAdapter = new MateriasAdapter(getContext(), listMateriasPropuestas, this);
        recyclerMateriasPropuestas.setAdapter(materiasAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
