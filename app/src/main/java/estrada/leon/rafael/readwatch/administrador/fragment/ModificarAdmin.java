package estrada.leon.rafael.readwatch.administrador.fragment;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.general.pojo.Admin;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarAdmin extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private iComunicacionFragmentsAdm comunicacionFragmentsAdm;
    EditText txtNombre, txtApellidos, txtContrasena, txtEscribeCorreo;
    Button btnBuscar, btnModificar;
    ProgressDialog progreso;
    Activity actividad;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    Admin admin;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final boolean BUSCAR=false;
    private final boolean MODIFICAR=true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarAdmin newInstance(String param1, String param2) {
        ModificarAdmin fragment = new ModificarAdmin();
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
        View vista = inflater.inflate(R.layout.fragment_modificar_admin, container, false);
        txtNombre = vista.findViewById(R.id.txtNombre);
        txtApellidos = vista.findViewById(R.id.txtApellidos);
        txtContrasena= vista.findViewById(R.id.txtContrasena);
        txtEscribeCorreo = vista.findViewById(R.id.txtEscribeCorreo);
        btnModificar = vista.findViewById(R.id.btnModificar);
        request= Volley.newRequestQueue(getContext());

        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuarios = preferences.getInt("idUsuario", 0);
        txtEscribeCorreo.setText(Integer.toString(idUsuarios));
        cargarWebService(BUSCAR);


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNombre.getText().toString().equals("")|| txtApellidos.getText().toString().equals("")
                || txtContrasena.getText().toString().equals("")){Toast.makeText(getContext(),"Llena todos los datos",Toast.LENGTH_LONG).show();}
                else {
                    if (txtContrasena.getText().length() < 9) {

                        Toast.makeText(getContext(), "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
                    } else {
                        char clave;
                        int a = 0, b = 0, c = 0;
                        for (int i = 0; i < txtContrasena.getText().length(); i++) {
                            clave = txtContrasena.getText().charAt(i);
                            String passValue = String.valueOf(clave);
                            if (passValue.matches("[A-Z]")) {
                                a = 1;
                            } else if (passValue.matches("[a-z]")) {
                                b = 1;
                            } else if (passValue.matches("[0-9]")) {
                                c = 1;
                            }
                        }
                        if (a == 1 && b == 1 && c == 1) {
                            cargarWebService(MODIFICAR);
                            Toast.makeText(getContext(), "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "La contraseña debe contener mayúsculas, minúsculas y números", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });
        return vista;

    }

    private void cargarWebService(boolean php) {
        String url,ip=getString(R.string.ip);
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        if(php==MODIFICAR){
            url = ip+"/php/updateAdmin.php?" +
                    "idUsuario="+txtEscribeCorreo.getText().toString()+"&nombre="+
                    txtNombre.getText().toString()+"&apellidos="+txtApellidos.getText().toString()+"&contrasena="+txtContrasena.getText().toString()+"";
            url=url.replace(" ", "%20");
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }else{
            url = ip+"/php/buscarAdmin.php?idUsuario="+txtEscribeCorreo.getText().toString();
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
        //progreso.hide();
        Toast.makeText(getContext(), "No se encontro el usuario "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(getContext(), "Mensaje: "+response, Toast.LENGTH_SHORT).show();
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        admin = new Admin();
        try {
            jsonObject=json.getJSONObject(0);
            admin.setNombre(jsonObject.optString("nombre"));
            admin.setApellidos(jsonObject.optString("apellidos"));
            admin.setContrasena(jsonObject.optString("contrasena"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNombre.setText(admin.getNombre());
        txtApellidos.setText(admin.getApellidos());
        txtContrasena.setText(admin.getContrasena());
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
