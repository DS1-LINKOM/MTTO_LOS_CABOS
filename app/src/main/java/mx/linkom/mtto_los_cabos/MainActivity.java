package mx.linkom.mtto_los_cabos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TOKEN";
    private FirebaseAuth fAuth;
    private Configuracion Conf;
    EditText User, Pass,Pin;
    Button Iniciar;
    JSONArray ja1,ja2;
    CheckBox ver;


    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
    private String[] permissions = new String[]{android.Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conf = new Configuracion(this);

        if (mx.linkom.mtto_los_cabos.Global.TOKEN.equals("")) {
            mx.linkom.mtto_los_cabos.Global.TOKEN = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Token Generado: " + mx.linkom.mtto_los_cabos.Global.TOKEN);
        }

        fAuth = FirebaseAuth.getInstance();
        Pin = (EditText) findViewById(R.id.pin);
        User = (EditText) findViewById(R.id.usu);
        Pass = (EditText) findViewById(R.id.contra);
        Iniciar = (Button) findViewById(R.id.entrar);
        ver = (CheckBox) findViewById (R.id.ver);

        ver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PIN();
            }});


    }


    public void PIN() {
        if (Pin.toString().isEmpty()) {

        } else {


            String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/pin.php";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    response = response.replace("][", ",");
                    if (response.length() > 0) {
                        try {
                            ja1 = new JSONArray(response);

                            Conf.setNomResi(ja1.getString(1));
                            Conf.setBd(ja1.getString(2));
                            Conf.setBdUsu(ja1.getString(3));
                            Conf.setBdCon(ja1.getString(4));
                            Conf.setPin(ja1.getString(6));
                            Residencial();



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Error: " + error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("pin", Pin.getText().toString().trim());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void Residencial() {


        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/residencial.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPUESTA: " + response);

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        ja2 = new JSONArray(response);
                        Conf.setResid(ja2.getString(0));

                        Login();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_LONG).show();

                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pin", Pin.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void Login() {

        if (User.toString().isEmpty()) {

        } else {
            String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/session.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    response = response.replace("][", ",");
                    if (response.length() > 0) {
                        try {
                            ja1 = new JSONArray(response);
                            Conf.setUsu(ja1.getString(0));
                            Conf.setUserTipo(ja1.getString(2));
                            Conf.setNombre(ja1.getString(12));

                            mx.linkom.mtto_los_cabos.Global.USER = ja1.getString(12);
                            mx.linkom.mtto_los_cabos.Global.PASS = ja1.getString(13);
                            mx.linkom.mtto_los_cabos.Global.EMAIL = ja1.getString(8);

                            if(ja1.getString(2).equals("1")){

                                Intent i = new Intent(getApplication(), mx.linkom.mtto_los_cabos.DashboardAdminActivity.class);
                                startActivity(i);
                                finish();
                            }else if(ja1.getString(2).equals("2")){

                                Intent i = new Intent(getApplication(), mx.linkom.mtto_los_cabos.DashboardActivity.class);
                                startActivity(i);
                                finish();
                            }



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                    } else {
                        User.setText("");
                        Pass.setText("");
                        Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "Error: " + error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Usuario", User.getText().toString().trim());
                    params.put("Pass", Pass.getText().toString().trim());
                    params.put("Token", mx.linkom.mtto_los_cabos.Global.TOKEN.trim());
                    params.put("Residencial", Conf.getResid());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            //Si alguno de los permisos no esta concedido lo solicita
            ActivityCompat.requestPermissions(MainActivity.this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        } else {
            //Si todos los permisos estan concedidos prosigue con el flujo normal
            permissionGranted();
        }

        FirebaseUser fUser = fAuth.getCurrentUser();

        if(fUser != null){
            if( Conf.getUserTipo().equals("1")){
                Intent i = new Intent(getApplicationContext(), mx.linkom.mtto_los_cabos.DashboardAdminActivity.class);
                startActivity(i);
                finish();
            }else if(Conf.getUserTipo().equals("2")){
                Intent i = new Intent(getApplicationContext(), mx.linkom.mtto_los_cabos.DashboardActivity.class);
                startActivity(i);
                finish();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Inicie Sesion", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS_REQUEST_CODE:
                if (validatePermissions(grantResults)) {
                    permissionGranted();
                } else {
                    permissionRejected();
                }
                break;
        }
    }

    private boolean validatePermissions ( int[] grantResults){
        boolean allGranted = false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                allGranted = true;
            } else {
                allGranted = false;
                break;
            }
        }
        return allGranted;
    }

    public void permissionGranted () {
        //Toast.makeText(getApplicationContext(), getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
    }

    public void permissionRejected () {
        Toast.makeText(getApplicationContext(), getString(R.string.permission_rejected), Toast.LENGTH_SHORT).show();

    }


}
