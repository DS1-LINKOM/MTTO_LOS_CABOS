package mx.linkom.mtto_los_cabos;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AsignarUsuarioActivity extends  mx.linkom.mtto_los_cabos.Menu {
    private Configuracion Conf;
    JSONArray ja1,ja2;

    TextView Tipo,Actividad,Fechas,Zona,Detalles;
    Button Registrar;
    LinearLayout fecha;
    private int mMonth, mYear, mDay,mes;
    String mes_completo;
    ArrayList<String> usuarios;
    Spinner Usuario;
    LinearLayout RegistrarL,Espacio5;
    String FechaP,selec;
    EditText Empresa,Tel,Correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_usuario);
        Conf = new Configuracion(this);
        usuarios = new ArrayList<String>();
        Usuario = (Spinner)findViewById(R.id.setUsuario);
        Detalles = (TextView) findViewById(R.id.setDetalles);

        Empresa = (EditText) findViewById(R.id.setEmpresa);
        Tel = (EditText) findViewById(R.id.setTel);
        Correo = (EditText) findViewById(R.id.setCorreo);
        Tipo = (TextView) findViewById(R.id.setTipo);
        Actividad = (TextView) findViewById(R.id.setActividad);
        Zona = (TextView) findViewById(R.id.setZona);
        RegistrarL = (LinearLayout) findViewById(R.id.registrar);
        Espacio5 = (LinearLayout) findViewById(R.id.Espacio5);

        Registrar = (Button) findViewById(R.id.btnContinuar2);
        Fechas = (TextView) findViewById(R.id.setFecha);
        fecha = findViewById(R.id.fecha);



        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(AsignarUsuarioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mes=(monthOfYear + 1);
                                if(mes<10){
                                    mes_completo="0"+mes;
                                }else{
                                    mes_completo= String.valueOf(mes);

                                }
                                Fechas.setText(year + "-" + mes_completo + "-" + dayOfMonth);
                                RegistrarL.setVisibility(View.VISIBLE);
                                Espacio5.setVisibility(View.VISIBLE);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        traerTrabajadores();
        traerRutina();
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AsignarUsuarioActivity.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("¿ Desea asignar la tarea ?")
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               Registrar();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        


    }


    public void traerTrabajadores() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_programados_trabajadores.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        ja2 = new JSONArray(response);

                        cargarSpinner();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ", "Id: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();
                params.put("idResi", Conf.getResid());
                params.put("estatus", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void cargarSpinner(){

        usuarios.add("Seleccionar...");

        try{
            for (int i=0;i<ja2.length();i+=17){
                usuarios.add(ja2.getString(i+12));
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,usuarios);
            Usuario.setAdapter(adapter1);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void traerRutina() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_programados_asignar_detalle.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Error ", "TRABAJADORES:"+response);
                if (response.length() > 0) {
                    try {
                        ja1 = new JSONArray(response);

                        informacion();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ", "Id: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();
                params.put("idResi", Conf.getResid());
                params.put("id", Conf.getIdAsignar());
                params.put("estatus", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void informacion(){

        try {
            Actividad.setText(ja1.getString(0));
            Zona.setText(ja1.getString(2));
            if(ja1.getString(1).equals("1")){
                Tipo.setText("Preventivo");
            }else if(ja1.getString(1).equals("2")){
                Tipo.setText("Correctivo");
            }
            Detalles.setText(ja1.getString(3));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Registrar() {

        selec = Usuario.getSelectedItem().toString();

        if (selec.equals("Seleccionar...")) {
            Toast.makeText(getApplicationContext(), "Usuario No Seleccinado", Toast.LENGTH_SHORT).show();
        }else if(Empresa.getText().toString().equals("") ){
            Toast.makeText(getApplicationContext(),"Registra una empresa", Toast.LENGTH_SHORT).show();
        }else if(Tel.getText().toString().equals("") ){
            Toast.makeText(getApplicationContext(),"Registra un teléfono", Toast.LENGTH_SHORT).show();
        } else {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_programados_asignar_actu.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response.equals("error")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AsignarUsuarioActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Tarea No Asignada")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "Tarea No Terminada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AsignarUsuarioActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Tarea Asignada")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "Tarea Terminada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ", "Id: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                selec = Usuario.getSelectedItem().toString();
                FechaP = Fechas.getText().toString();


                params.put("idResi", Conf.getResid());
                params.put("idOrden", Conf.getIdAsignar());
                params.put("idUsuario", selec);
                params.put("fecha", FechaP);
                try {
                    params.put("descripcion", ja1.getString(3)+"\n"+"Empresa: "+Empresa.getText().toString().trim()+"\n"+"Teléfono: "+
                            Tel.getText().toString().trim()+"\n"+"Correo E: "+Correo.getText().toString().trim()+"\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };
        requestQueue.add(stringRequest);
        }
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), mx.linkom.mtto_los_cabos.DashboardAdminActivity.class);
        startActivity(intent);
        finish();
    }

}
