package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.entidades.Estudiante;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarEstudiante.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarEstudiante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarEstudiante extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private iComunicacionFragments comunicacionFragments;
    EditText txtNombre, txtApellidos, txtContrasena, idUsuario, txtTelefono, txtDescripcion;
    Button btnBuscar, btnModificar;
    ProgressDialog progreso;
    Activity actividad;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final boolean BUSCAR=false;
    private final boolean MODIFICAR=true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarEstudiante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarEstudiante.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarEstudiante newInstance(String param1, String param2) {
        ModificarEstudiante fragment = new ModificarEstudiante();
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
        View vista = inflater.inflate(R.layout.fragment_modificar_estudiante, container, false);
        txtNombre = vista.findViewById(R.id.txtNombre);
        txtApellidos = vista.findViewById(R.id.txtApellidos);
        txtContrasena= vista.findViewById(R.id.txtContrasena);
        idUsuario = vista.findViewById(R.id.idUsuario);
        txtDescripcion = vista.findViewById(R.id.txtDescripcion);
        txtTelefono = vista.findViewById(R.id.txtTelefono);
        btnModificar = vista.findViewById(R.id.btnModificar);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        request= Volley.newRequestQueue(getContext());

        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuarios = preferences.getInt("idUsuario", 0);
        /*String nombre = preferences.getString("nombre", "No tiene informacion");
        String apellidos = preferences.getString("apellidos", "No tiene informacion");
        String descripcion = preferences.getString("descripcion", "No tiene informacion");
        String contrasena = preferences.getString("contrasena", "No tiene informacion");
        String telefono = preferences.getString("telefono", "No tiene informacion");*/

        /*txtNombre.setText(nombre);
        txtApellidos.setText(apellidos);
        txtContrasena.setText(contrasena);
        txtDescripcion.setText(descripcion);
        txtTelefono.setText(telefono);*/
        idUsuario.setText(Integer.toString(idUsuarios));

        cargarWebService(BUSCAR);



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService(BUSCAR);
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService(MODIFICAR);
                Toast.makeText(getContext(),"Datos actualizados con éxito",Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

    private void cargarWebService(boolean php) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        if(php==MODIFICAR){
            url = "https://readandwatch.herokuapp.com/php/updateEstudiante.php?" +
                    "idUsuario="+idUsuario.getText().toString()+"&nombre="+
                    txtNombre.getText().toString()+"&apellidos="+txtApellidos.getText().toString()+
                    "&contrasena="+txtContrasena.getText().toString()+"&telefono="+
                    txtTelefono.getText().toString()+"&descripcion="+txtDescripcion.getText().toString();
            url=url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }else{
            url = "https://readandwatch.herokuapp.com/php/buscarEstudiante.php?idUsuario="+idUsuario.getText().toString();
            url=url.replace(" ", "%20");
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
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            comunicacionFragments =(iComunicacionFragments) actividad;
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
        Toast.makeText(getContext(), "No se encontro el usuario "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(getContext(), "Mensaje: "+response, Toast.LENGTH_SHORT).show();
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
        txtNombre.setText(estudiante.getNombre());
        txtApellidos.setText(estudiante.getApellidos());
        txtContrasena.setText(estudiante.getContrasena());
        txtDescripcion.setText(estudiante.getDescripcion());
        txtTelefono.setText(estudiante.getTelefono());
        progreso.hide();
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
