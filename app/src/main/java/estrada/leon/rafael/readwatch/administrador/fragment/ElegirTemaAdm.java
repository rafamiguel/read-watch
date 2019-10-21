package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import estrada.leon.rafael.readwatch.administrador.adapter.TemasAdapterAdm;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;


public class ElegirTemaAdm extends Fragment implements TemasAdapterAdm.OnTemasListener,
        Response.Listener<JSONObject>, Response.ErrorListener{
    private iComunicacionFragments interfaceFragments;
    List<Item> temasList;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    int idMateria, semestre, idTema ;;
    TemasAdapterAdm temasAdapter;
    RecyclerView temas;
    FloatingActionButton btnAgregarTema;
    TextView lblModificar, lblEliminar, lblAnadir;


    private OnFragmentInteractionListener mListener;

    public ElegirTemaAdm() {
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
        vista=inflater.inflate(R.layout.fragment_elegir_tema_adm, container, false);
        temas=vista.findViewById(R.id.temas);
        btnAgregarTema = vista.findViewById(R.id.btnAgregarTema);
        btnAgregarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {cargarDia();
            }
        });
        lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
        lblTemaInexistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceFragments.onClickProponerTema();
            }
        });
        temas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getContext().getSharedPreferences("Materia", Context.MODE_PRIVATE);
        idMateria = preferences.getInt("materia", 0);
        preferences =  getContext().getSharedPreferences("Semestre", Context.MODE_PRIVATE);
        semestre = preferences.getInt("semestre", 0);
        request= Volley.newRequestQueue(getContext());
        cargarWebService();
        return vista;
    }

    @Override
    public void onAttach(Context context) {
        Activity actividad;
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragments)actividad;
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
    public void onTemaClick(int position, List<Item> lista) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        interfaceFragments.seleccionarVideo(((Subtemas) (temasList.get(position))).getIdSubtema(),idUsuario);
    }

    @Override
    public void Tema(final int posicion, List<Item> lista) {
        AlertDialog.Builder a = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_opciones, null);
        lblEliminar = mView.findViewById(R.id.lblEliminar);
        lblModificar = mView.findViewById(R.id.lblModificar);
        lblAnadir = mView.findViewById(R.id.lblAnadir);

        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idTema = (((Temas)(temasList.get(posicion))).getIdTema());
                cargarDialogTema(idTema);
            }
        });
        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idTema = (((Temas)(temasList.get(posicion))).getIdTema());
                Toast.makeText(getContext(), String.valueOf(idTema), Toast.LENGTH_SHORT).show();
                deleteTema(idTema);
            }
        });

        lblAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idTema = (((Temas)(temasList.get(posicion))).getIdTema());
                cargarDialog(idTema);
            }
        });
        a.setView(mView);
        AlertDialog dialog = a.create();
        dialog.show();

    }

    private void cargarDia() {
        final EditText txtTitulo;
        TextView title;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_tema,  null);
        builder.setView(view);
        txtTitulo = view.findViewById(R.id.txtTitulo);


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                        agregarTema(txtTitulo.getText().toString());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void agregarTema(String nombre) {
        SharedPreferences preferences = getContext().getSharedPreferences("Semestre", Context.MODE_PRIVATE);
        int semestre = preferences.getInt("semestre",0);

        preferences = getContext().getSharedPreferences("Materia", Context.MODE_PRIVATE);
        int materia= preferences.getInt("materia", 0);

        preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);

        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Subiendo...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/insertTema.php?" +
                "idMateria="+materia+"&semestre="+semestre+"&idUsuario="+idUsuario+"&nombre="+nombre;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(),"Se agrego correctamente", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void cargarDialogTema(final int idTema) {
        final EditText txtTitulo;
        TextView title;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_tema,  null);
        builder.setView(view);
        txtTitulo = view.findViewById(R.id.txtTitulo);


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                        modificarTema(idTema, txtTitulo.getText().toString());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void modificarTema(int idTema, String nombre) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Modificando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/updateTema.php?" +
                "idTema="+idTema+"&nombre="+nombre;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(),"Se modifico correctamente", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }


    private void deleteTema(int idTema) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/deleteTema.php?" +
                "idTema="+idTema;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se elimino el tema correctamente", Toast.LENGTH_SHORT).show();
            }
        }, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void Subtema(final int posicion, List<Item> lista) {
        AlertDialog.Builder a = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_opciones, null);
        lblEliminar = mView.findViewById(R.id.lblEliminar);
        lblModificar = mView.findViewById(R.id.lblModificar);
        lblAnadir = mView.findViewById(R.id.lblAnadir);

        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i= (((Subtemas)(temasList.get(posicion))).getIdSubtema());
                modificarSubtema(i);
            }
        });
        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i= (((Subtemas)(temasList.get(posicion))).getIdSubtema());
                Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                deleteSubtema(i);
            }
        });
        lblAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idSubtema = (((Subtemas)(temasList.get(posicion))).getIdSubtema());
                buscarId(idSubtema);

            }
        });
        a.setView(mView);
        AlertDialog dialog = a.create();
        dialog.show();
    }

    private void buscarId(int idSubtema) {

        String url;

        url = "https://readandwatch.herokuapp.com/php/buscarIdTema.php?" +
                "idSubtema="+idSubtema;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json;

                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idTema =jsonObject.optInt("idTema");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), String.valueOf(idTema), Toast.LENGTH_SHORT).show();
                cargarDialog(idTema);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }

    private void cargarDialog(final int idTema) {
        final EditText txtTitulo;
        TextView title;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_tema,  null);
        builder.setView(view);
        txtTitulo = view.findViewById(R.id.txtTitulo);


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                        añadirSubtema(idTema, txtTitulo.getText().toString());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void añadirSubtema(int idTema, String nombre) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Creando...");
        progreso.show();
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);

        url = "https://readandwatch.herokuapp.com/php/insertarSubtema.php?" +
                "idTema="+idTema+"&idUsuario="+idUsuario+"&nombre="+nombre;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se agrego correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);


    }

    private void modificarSubtema(final int i) {
        final EditText txtTitulo;
        TextView title;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_tema,  null);
        builder.setView(view);
        txtTitulo = view.findViewById(R.id.txtTitulo);


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                            modificarSub(i,txtTitulo.getText().toString());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();



    }

    private void modificarSub(int i, String nombre) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Actualizando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/updateSubtema.php?" +
                "idSubtema="+i+"&nombre="+nombre;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }

    private void deleteSubtema(int i) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/deleteSubtema.php?" +
                "idSubtema="+i;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se elimino correctamente", Toast.LENGTH_SHORT).show();
            }
        }, this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebService() {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarTemas.php?" +
                "idMateria="+idMateria+"&semestre="+semestre;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this , this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        String nombre,rutaImagen,tipo;
        int id,idUsuario, votos, semestre;
        Temas tema;
        Subtemas subtema;
        temasList=new ArrayList<>();
        JSONArray json;
        JSONObject jsonObject=null;
        json = response.optJSONArray("usuario");

        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                tipo=jsonObject.optString("tipo");
                if(tipo.equals("tema")) {
                    nombre=jsonObject.optString("nombre");
                    id=jsonObject.optInt("idTema");
                    tema = new Temas(nombre,id);
                    temasList.add(tema);
                }else if(tipo.equals("subtema")){
                    nombre=jsonObject.optString("nombre");
                    id=jsonObject.optInt("idSubtema");
                    subtema = new Subtemas(nombre,id);
                    temasList.add(subtema);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        temasAdapter= new TemasAdapterAdm(getContext(),temasList,this);
        temas.setAdapter(temasAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}