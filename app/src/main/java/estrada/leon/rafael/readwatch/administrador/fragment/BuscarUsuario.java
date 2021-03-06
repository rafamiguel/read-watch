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
import android.widget.EditText;
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

import estrada.leon.rafael.readwatch.administrador.dialog.DialogEliminarUsuario;
import estrada.leon.rafael.readwatch.administrador.adapter.BuscarUsuarioAdapter;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.administrador.pojo.BuscarUsuarioAd;
import estrada.leon.rafael.readwatch.R;


public class BuscarUsuario extends Fragment implements BuscarUsuarioAdapter.OnBuscarListener,
        View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{
    RecyclerView recyclerBuscar;
    EditText txtNombre, txtApellido;
    Button buscar, btnOpcion;
    Activity actividad;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    List<BuscarUsuarioAd> usuarioAds;
    BuscarUsuarioAdapter buscarUsuarioAdapter;
    RequestQueue request;
    String url;
    DialogEliminarUsuario a;
    String nombre,apellido, rutaFoto;

    private iComunicacionFragmentsAdm interfaceFragments;
    View vista;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BuscarUsuario() {

    }

    public static BuscarUsuario newInstance(String param1, String param2) {
        BuscarUsuario fragment = new BuscarUsuario();
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

        // Inflate the layout for this fragment
        a = new DialogEliminarUsuario();
        vista=inflater.inflate(R.layout.fragment_buscar_usuario, container, false);
        recyclerBuscar=vista.findViewById(R.id.recyclerBuscar);
        txtNombre = vista.findViewById(R.id.txtNombre);
        txtApellido = vista.findViewById(R.id.txtApellido);
        buscar = vista.findViewById(R.id.btnBuscar);
        btnOpcion = vista.findViewById(R.id.btnOpcion);
        btnOpcion.setVisibility(View.INVISIBLE);
        btnOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialog();
            }
        });
        recyclerBuscar.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        request= Volley.newRequestQueue(getContext());
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarUsuarioWebService();
            }
        });
        return vista;

    }

    private void abrirDialog() {
        DialogEliminarUsuario a = new DialogEliminarUsuario();
        a.setCancelable(false);
        a.DialogEliminarUsuarioConstructor(nombre,apellido,getContext());
        a.show(getFragmentManager(), "ejemplo");
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
            interfaceFragments=(iComunicacionFragmentsAdm) actividad;
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
    public void OnBuscarClick(int posicion, List<BuscarUsuarioAd> list, Toast toast) {

    }

    @Override
    public void onClick(View view) {
    }

    public void buscarUsuarioWebService(){
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String nombre=txtNombre.getText().toString();
        String apellidos = txtApellido.getText().toString();
        url = "https://readandwatch.herokuapp.com/php/buscarEstudianteEliminar.php?nombre="+txtNombre.getText().toString()+"&apellidos="+txtApellido.getText().toString();
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        usuarioAds = new ArrayList<>();
        JSONArray json;
        JSONObject jsonObject=null;
        BuscarUsuarioAd usuario;
        json = response.optJSONArray("usuario");

        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                nombre=jsonObject.optString("nombre");
                apellido=jsonObject.optString("apellidos");
                rutaFoto=jsonObject.optString("rutaFoto");
                if(nombre.equals("")){
                    progreso.hide();
                    Toast.makeText(getContext(), "No se encontro", Toast.LENGTH_SHORT).show();
                    btnOpcion.setVisibility(View.INVISIBLE);
                }
                else{
                usuario=new BuscarUsuarioAd(nombre,apellido,rutaFoto);
                btnOpcion.setVisibility(View.VISIBLE);
                usuarioAds.add(usuario);
                a.DialogEliminarUsuarioConstructor(nombre,apellido,getContext());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //list=new ArrayList<>();
        //list.add(new BuscarUsuarioAd("J Balvin", ""));
        //BuscarUsuarioAdapter h = new BuscarUsuarioAdapter(getContext(),list, this);
        //recyclerBuscar.setAdapter(h);

        progreso.hide();
        buscarUsuarioAdapter=new BuscarUsuarioAdapter(getContext(),usuarioAds,this );
        recyclerBuscar.setAdapter(buscarUsuarioAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
