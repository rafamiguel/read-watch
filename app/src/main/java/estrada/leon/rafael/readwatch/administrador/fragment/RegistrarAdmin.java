package estrada.leon.rafael.readwatch.administrador.fragment;

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


public class RegistrarAdmin extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    View vista;
    EditText txtNombre, txtApellidos, txtCorreo, txtContrasena;
    Button btnRegistrar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistrarAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarAdmin newInstance(String param1, String param2) {
        RegistrarAdmin fragment = new RegistrarAdmin();
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


        vista=inflater.inflate(R.layout.fragment_registrar_admin, container, false);

        txtNombre = vista.findViewById(R.id.txtNombre);
        txtApellidos = vista.findViewById(R.id.txtApellidos);
        txtCorreo = vista.findViewById(R.id.txtCorreo);
        txtContrasena = vista.findViewById(R.id.txtContrasena);
        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNombre.getText().toString().equals("") || txtApellidos.getText().toString().equals("")
                        || txtCorreo.getText().toString().equals("") || txtContrasena.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Llena todos los datos", Toast.LENGTH_LONG).show();

                } else {
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
                            cargarWebService();
                        } else {
                            Toast.makeText(getContext(), "La contraseña debe contener mayúsculas, minúsculas y números", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
        request = Volley.newRequestQueue(getContext());
        return vista;
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "https://readandwatch.herokuapp.com/php/registroAdmin.php?txtCorreo="+txtCorreo.getText().toString()+
                "&txtContrasena="+txtContrasena.getText().toString()+
                "&txtNombre="+txtNombre.getText().toString()+
                "&txtApellidos="+txtApellidos.getText().toString()+ "";
        url=url.replace(" ", "%20");
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
        Toast.makeText(getContext(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "No se pudo registrar"+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            String existencia=jsonObject.optString("existencia");
            Toast.makeText(getContext(),existencia,Toast.LENGTH_LONG);
            if(existencia.equals("si")){
                Toast.makeText(getContext(), "Este correo electrónico ya está registrado.", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }else{
                Toast.makeText(getContext(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
                progreso.hide();
                txtApellidos.setText("");
                txtNombre.setText("");
                txtContrasena.setText("");
                txtCorreo.setText("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
