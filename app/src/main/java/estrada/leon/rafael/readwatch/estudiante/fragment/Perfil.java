package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import estrada.leon.rafael.readwatch.entidades.Estudiante;
import estrada.leon.rafael.readwatch.estudiante.adapter.PerfilAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class Perfil extends Fragment implements PerfilAdapter.OnPerfilListener, Response.Listener<JSONObject>, Response.ErrorListener{
    List<Item> list;
    ProgressDialog progreso;
    TextView lblNombreApellidos,lblDescripcion,lblCelular;
    RequestQueue request;
    int idUsuarios;
    int perfilEstudiante;
    String dato;
    private final boolean BUSCAR=true;
    JsonObjectRequest jsonObjectRequest;
    private iComunicacionFragments interfaceFragments;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Perfil() {
    }
    public void setIdUsuario(int idUsuario){
        this.idUsuarios = idUsuario;
    }
    void cargarDatos(){
        list=new ArrayList<>();
        for(int i=1;i<11;i++){
            list.add(new Documentos("perfil"+i,"Documento"+i,"@drawable/btnDocumento"));
            list.add(new Videos("perfil"+i,"video"+i,"@drawable/miniatura",i));
        }
    }
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        ImageView fotoPerfil;
        request= Volley.newRequestQueue(getContext());

        SharedPreferences preferenc = getContext().getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        dato = preferenc.getString("dato", "");
        if(dato.equalsIgnoreCase("PP")) {
            SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
            idUsuarios = preferences.getInt("idUsuario", 0);
            cargarWebServices(1);
        }

        if(dato.equalsIgnoreCase("PO")) {
            SharedPreferences preference = getContext().getSharedPreferences("Perfil Estudiante", Context.MODE_PRIVATE);
            perfilEstudiante = preference.getInt("perfilEstudiante", 0);
            cargarWebServices(2);
        }

        SharedPreferences preference = getContext().getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString("dato", "PP");
        edit.commit();

        RecyclerView recyclerPerfil;
        PerfilAdapter perfilAdapter;
        View view;
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        fotoPerfil=view.findViewById(R.id.fotoPerfil);
        lblNombreApellidos=view.findViewById(R.id.lblNombreApellidos);
        lblDescripcion=view.findViewById(R.id.lblDescripcion);
        lblCelular=view.findViewById(R.id.lblCelular);
        fotoPerfil.setImageResource(R.drawable.profilepic);
        recyclerPerfil=view.findViewById(R.id.recyclerPerfil);
        recyclerPerfil.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        perfilAdapter=new PerfilAdapter(getContext(),list,this);
        recyclerPerfil.setAdapter(perfilAdapter);
        return view;


    }

    private void cargarWebServices(int a) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        if(a==2) {
            url = "https://readandwatch.herokuapp.com/php/buscarEstudiante.php?idUsuario=" + perfilEstudiante;
            url = url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }
        if (a==1) {
            url = "https://readandwatch.herokuapp.com/php/buscarEstudiante.php?idUsuario=" + idUsuarios;
            url = url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }
        request.add(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Activity activity;
        super.onAttach(context);
        if (context instanceof Activity) {
            activity= (Activity) context;
            interfaceFragments=(iComunicacionFragments)activity;
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
    public void onPerfilClick(int position, List<Item> lista, Toast toast) {
        interfaceFragments.onClickDocFavHolder(toast);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se pudieron mostrar los datos "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Estudiante estudiante = new Estudiante();
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            estudiante.setNombre(jsonObject.optString("nombre"));
            estudiante.setApellidos(jsonObject.optString("apellidos"));
            estudiante.setContrasena(jsonObject.optString("contrasena"));
            estudiante.setDescripcion(jsonObject.optString("descripcion"));
            estudiante.setTelefono(jsonObject.optString("telefono"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        lblNombreApellidos.setText(estudiante.getNombre()+" "+estudiante.getApellidos());
        lblDescripcion.setText(estudiante.getDescripcion());
        lblCelular.setText(estudiante.getTelefono());
        progreso.hide();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
