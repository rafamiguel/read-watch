package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.entidades.Admin;

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
    EditText txtNombre, txtApellidos, txtCorreo, txtContrasena, txtEscribeCorreo;
    Button btnBuscar, btnModificar;
    ProgressDialog progreso;
    Activity actividad;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        txtCorreo = vista.findViewById(R.id.txtCorreo);
        txtContrasena= vista.findViewById(R.id.txtContrasena);
        txtEscribeCorreo = vista.findViewById(R.id.txtEscribeCorreo);
        btnModificar = vista.findViewById(R.id.btnModificar);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        request= Volley.newRequestQueue(getContext());

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
        return vista;

    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "http://192.168.1.67/randwBDRemota/buscarAdmin.php?txtCorreo="+txtEscribeCorreo.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
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
        progreso.hide();
        Toast.makeText(getContext(), "No se encontro el usuario "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //Toast.makeText(getContext(), "Mensaje: "+response, Toast.LENGTH_SHORT).show();
        Admin admin = new Admin();
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            admin.setNombre(jsonObject.optString("nombre"));
            admin.setApellidos(jsonObject.optString("apellidos"));
            admin.setCorreo(jsonObject.optString("correo"));
            admin.setContrasena(jsonObject.optString("contrasena"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNombre.setText(admin.getNombre());
        txtApellidos.setText(admin.getApellidos());
        txtContrasena.setText(admin.getContrasena());
        txtCorreo.setText(admin.getCorreo());
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
