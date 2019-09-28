package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;

public class ElegirVideoAdm extends Fragment implements  MateriasAdapter.OnMateriaListener,
        Response.Listener<JSONObject>, Response.ErrorListener {
    private iComunicacionFragmentsAdm comunicacionFragmentsAdm;
    private iComunicacionFragments interfaceFragments;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    private List<Materias> listMaterias,listMateriasPropuestas;
    private MateriasAdapter materiasAdapter;
    RequestQueue request;
    View vista;
    Activity actividad;
    RecyclerView recyclerMaterias, recyclerMateriasPropuestas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirVideoAdm() {
        // Required empty public constructor
    }

    public static ElegirVideoAdm newInstance(String param1, String param2) {
        ElegirVideoAdm fragment = new ElegirVideoAdm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView lblTemaInexistente;
        vista = inflater.inflate(R.layout.fragment_elegir_materia, container, false);
        recyclerMaterias=vista.findViewById(R.id.recyclerMaterias);
        recyclerMateriasPropuestas=vista.findViewById(R.id.recyclerMateriasPropuestas);
        lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
        lblTemaInexistente.setText("");
        recyclerMaterias.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerMateriasPropuestas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        request= Volley.newRequestQueue(getContext());
        cargarWebService();

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            comunicacionFragmentsAdm =(iComunicacionFragmentsAdm) actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
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
    public void onMateriaClick(int position, List<Materias> lista) {
        if(lista.equals(listMaterias)){
            interfaceFragments.seleccionarSemestre(listMaterias.get(position).getIdMateria());
        }else if(lista.equals(listMateriasPropuestas)){
            interfaceFragments.seleccionarSemestre(listMateriasPropuestas.get(position).getIdMateria());
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
