package mx.linkom.mtto_los_cabos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdminRutinaBitacoraActivity extends mx.linkom.mtto_los_cabos.Menu {
    private Configuracion Conf;
    JSONArray ja1,ja2;
    TextView Actividad,Usuario,Area,Fecha,Observaciones11,Observaciones22;
    EditText Notas,Observaciones1,Observaciones2;
    Button Guardar_Nota;
    Uri uri_img,uri_img2,uri_img3;
    ImageView foto_antes,foto_despues;
    LinearLayout Antes,Despues,RegistrarTer;
    Button Foto_Antes,RegistarAntes,Foto_Despues,RegistarDespues,Terminar;
    int foto;
    String foto_antes_nombre,foto_despues_nombre;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog pd,pd2,pd3;
    LinearLayout tituloDes,ObserAntG,ObserDesG,FotoDes,RegistrarDes,ObserAntG2,ObserDesG2,GuardarAntes,GuardarDespues,FotoAntes,TerminarL;
    LinearLayout Espacio1,Espacio2,Espacio3,Espacio4,Espacio5,Espacio6,Espacio7,Espacio8,Espacio9,Espacio10;
    int antes_a,despues_a,antes_d,despues_d;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_bitacora_rutina);
        Conf = new Configuracion(this);
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        Actividad = (TextView) findViewById(R.id.setActividad);
        Area = (TextView) findViewById(R.id.setArea);
        Usuario = (TextView) findViewById(R.id.setUsuario);
        Fecha = (TextView) findViewById(R.id.setFecha);
        Notas = (EditText) findViewById(R.id.setNotas);
        Guardar_Nota = (Button) findViewById(R.id.Btn_Guardar);
        Observaciones1 = (EditText) findViewById(R.id.setObs1);
        Observaciones2 = (EditText) findViewById(R.id.setObs2);
        foto_antes = (ImageView) findViewById(R.id.view_foto1);
        foto_despues = (ImageView) findViewById(R.id.view_foto2);
        Antes = (LinearLayout) findViewById(R.id.setAntesView);
        Despues = (LinearLayout) findViewById(R.id.setDespuesView);
        tituloDes = (LinearLayout) findViewById(R.id.setDespues);
        Terminar = (Button) findViewById(R.id.btnContinuar3);
        TerminarL = (LinearLayout) findViewById(R.id.registrar3);
        Espacio1 = (LinearLayout) findViewById(R.id.Espacio1);
        Espacio2 = (LinearLayout) findViewById(R.id.Espacio2);
        Espacio3 = (LinearLayout) findViewById(R.id.Espacio3);
        Espacio4 = (LinearLayout) findViewById(R.id.Espacio4);
        Espacio5 = (LinearLayout) findViewById(R.id.Espacio5);
        Espacio6 = (LinearLayout) findViewById(R.id.Espacio6);
        Espacio7 = (LinearLayout) findViewById(R.id.Espacio7);
        Espacio8 = (LinearLayout) findViewById(R.id.Espacio8);
        Espacio9 = (LinearLayout) findViewById(R.id.Espacio9);
        Espacio10 = (LinearLayout) findViewById(R.id.Espacio10);
        ObserDesG = (LinearLayout) findViewById(R.id.setDespuesObs);
        ObserDesG2 = (LinearLayout) findViewById(R.id.setDespuesObs2);
        FotoDes = (LinearLayout) findViewById(R.id.setDespuesFoto);
        RegistrarDes = (LinearLayout) findViewById(R.id.registrar2);
        ObserAntG = (LinearLayout) findViewById(R.id.setAntesObs);
        ObserAntG2 = (LinearLayout) findViewById(R.id.setAntesObs2);
        Observaciones11 = (TextView) findViewById(R.id.setObs11);
        Observaciones22 = (TextView) findViewById(R.id.setObs22);
        GuardarAntes = (LinearLayout) findViewById(R.id.registrar1);
        GuardarDespues = (LinearLayout) findViewById(R.id.registrar2);
        FotoAntes = (LinearLayout) findViewById(R.id.setAntesFoto);

        Foto_Antes = (Button) findViewById(R.id.foto1_boton);
        Foto_Despues = (Button) findViewById(R.id.foto2_boton);
        RegistarAntes = (Button) findViewById(R.id.btnContinuar1);
        RegistarDespues = (Button) findViewById(R.id.btnContinuar2);

        pd= new ProgressDialog(this);
        pd.setMessage("Subiendo Foto 1...");

        pd2= new ProgressDialog(this);
        pd2.setMessage("Subiendo Foto 2...");



        Foto_Antes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdjuntarImg1();
                // imgFoto();
            }
        });

        Foto_Despues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdjuntarImg2();
            }
        });

        Guardar_Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("¿ Desea registrar la nota ?")
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                insertarNota();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        RegistarAntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("¿ Desea registrar el antes ?")
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                insertarAntes();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        RegistarDespues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("¿ Desea registrar el después ?")
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                insertarDespues();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        Terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("¿ Desea terminar la tarea ?")
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                terminar();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        traerRutina();
        traerBitacora();
    }

    //ALETORIO
    Random primero = new Random();
    int prime= primero.nextInt(9);

    String[] segundo = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandonsegun = (int) Math.round(Math.random() * 26 ) ;

    Random tercero = new Random();
    int tercer= tercero.nextInt(9);

    String[] cuarto = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandoncuart = (int) Math.round(Math.random() * 26 ) ;

    String numero_aletorio=prime+segundo[numRandonsegun]+tercer+cuarto[numRandoncuart];

    //ALETORIO2

    Random primero2 = new Random();
    int prime2= primero2.nextInt(9);

    String[] segundo2 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandonsegun2 = (int) Math.round(Math.random() * 26 ) ;

    Random tercero2 = new Random();
    int tercer2= tercero2.nextInt(9);

    String[] cuarto2 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandoncuart2 = (int) Math.round(Math.random() * 26 ) ;

    String numero_aletorio2=prime2+segundo2[numRandonsegun2];

    //ALETORIO3

    Random primero3 = new Random();
    int prime3= primero3.nextInt(9);

    String[] segundo3 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandonsegun3 = (int) Math.round(Math.random() * 26 ) ;

    Random tercero3 = new Random();
    int tercer3= tercero3.nextInt(9);

    String[] cuarto3 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m","n","o","p","q","r","s","t","u","v","w", "x","y","z" };
    int numRandoncuart3 = (int) Math.round(Math.random() * 26 ) ;

    String numero_aletorio3=prime3+segundo3[numRandonsegun3]+tercer3+cuarto3[numRandoncuart3];




    Calendar fecha = Calendar.getInstance();
    int anio = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH) + 1;
    int dia = fecha.get(Calendar.DAY_OF_MONTH);

    public void traerRutina() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_detalle.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
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
                params.put("id", Conf.getIdRutina());
                params.put("estatus", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void informacion(){

        try {
            Actividad.setText(ja1.getString(0));
            Fecha.setText(dia+"-"+mes+"-"+anio);
            Area.setText(ja1.getString(1));
            Usuario.setText(ja1.getString(2));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //ADJUNTAR IMAGEN

    public void btnAdjuntarImg1() {
        final PopupMenu popupMenu = new PopupMenu(AdminRutinaBitacoraActivity.this, Foto_Antes);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_image, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                System.out.println("Item get Tittle: " + item.getTitle());
                if(item.getTitle().equals("Galería.")){
                    showFileChooser1();
                }
                if(item.getTitle().equals("Camara.")){
                    imgFoto();
                }
                if(item.getTitle().equals("Cancelar.")){
                    popupMenu.dismiss();
                }
                return false;
            }
        });
    }
    public void btnAdjuntarImg2() {
        final PopupMenu popupMenu = new PopupMenu(AdminRutinaBitacoraActivity.this, Foto_Despues);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_image, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                System.out.println("Item get Tittle: " + item.getTitle());
                if(item.getTitle().equals("Galería.")){
                    showFileChooser2();
                }
                if(item.getTitle().equals("Camara.")){
                    imgFoto2();
                }
                if(item.getTitle().equals("Cancelar.")){
                    popupMenu.dismiss();
                }
                return false;
            }
        });
    }


    public  void traerBitacora(){
        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_bitacora.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Error ", "BITACORA: " + response);
                if(response.equals("error")){
                    int $arreglo[]={0};
                    try {
                        ja2 = new JSONArray($arreglo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    informacion_bitacora();
                }else{
                    response = response.replace("][", ",");
                    if (response.length() > 0) {
                        try {
                            ja2 = new JSONArray(response);
                            Notas.setText(ja2.getString(11));
                            informacion_bitacora();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("idOrden", Conf.getIdRutina());
                params.put("Fecha", anio+"/"+mes+"/"+dia);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void informacion_bitacora(){

        try {


            if(ja2.getString(0).equals("0")){

            }else if(ja2.getString(0)!=("0")){

                //Cuando solo agregan nota
                if(ja2.getString(11)!="") {
                    Terminar.setVisibility(View.VISIBLE);
                    TerminarL.setVisibility(View.VISIBLE);
                    Espacio10.setVisibility(View.VISIBLE);
                }

                //ANTES
                String str_Sample = "0000-00-00 00:00:00";
                antes_a=str_Sample.compareTo(ja2.getString(7));
                despues_a=str_Sample.compareTo(ja2.getString(10));




                if(antes_a==-2 &&  despues_a==0){
                    ObserAntG.setVisibility(View.GONE);
                    GuardarAntes.setVisibility(View.GONE);
                    FotoAntes.setVisibility(View.GONE);

                    ObserAntG2.setVisibility(View.VISIBLE);
                    Observaciones11.setVisibility(View.VISIBLE);
                    Observaciones11.setText(ja2.getString(5));

                    if(ja2.getString(6).equals("")) {
                        Antes.setVisibility(View.GONE);
                        foto_antes.setVisibility(View.GONE);
                        Espacio6.setVisibility(View.GONE);
                        Espacio7.setVisibility(View.GONE);
                        Espacio9.setVisibility(View.GONE);
                    }else {
                        Antes.setVisibility(View.VISIBLE);
                        Espacio6.setVisibility(View.VISIBLE);
                        Espacio7.setVisibility(View.GONE);
                        Espacio9.setVisibility(View.GONE);
                        storageReference.child(Conf.getPin()+"/antes/"+ja2.getString(6))
                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override

                            public void onSuccess(Uri uri) {
                                Glide.with(AdminRutinaBitacoraActivity.this)
                                        .load(uri)
                                        .error(R.drawable.caseta_logo)
                                        .centerInside()
                                        .into(foto_antes);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }



                    Espacio8.setVisibility(View.VISIBLE);
                    tituloDes.setVisibility(View.VISIBLE);
                    Espacio1.setVisibility(View.VISIBLE);
                    ObserDesG.setVisibility(View.VISIBLE);
                    ObserDesG2.setVisibility(View.GONE);

                    Espacio2.setVisibility(View.VISIBLE);
                    FotoDes.setVisibility(View.VISIBLE);
                    Espacio3.setVisibility(View.VISIBLE);
                    RegistrarDes.setVisibility(View.VISIBLE);
                    Espacio5.setVisibility(View.VISIBLE);


                }

                //DESPUÉS
                String str_Samples = "0000-00-00 00:00:00";
                antes_d=str_Samples.compareTo(ja2.getString(7));
                despues_d=str_Samples.compareTo(ja2.getString(10));


                if(antes_d==-2 &&  despues_d==-2){

                    Terminar.setVisibility(View.VISIBLE);
                    TerminarL.setVisibility(View.VISIBLE);
                    Espacio10.setVisibility(View.VISIBLE);

                    ObserAntG.setVisibility(View.GONE);
                    GuardarAntes.setVisibility(View.GONE);
                    FotoAntes.setVisibility(View.GONE);

                    ObserAntG2.setVisibility(View.VISIBLE);
                    Observaciones11.setVisibility(View.VISIBLE);
                    Observaciones11.setText(ja2.getString(5));

                    if(ja2.getString(6).equals("")) {
                        Antes.setVisibility(View.GONE);
                        foto_antes.setVisibility(View.GONE);
                        Espacio6.setVisibility(View.GONE);
                        Espacio7.setVisibility(View.GONE);
                        Espacio9.setVisibility(View.GONE);
                    }else {
                        Antes.setVisibility(View.VISIBLE);
                        Espacio6.setVisibility(View.VISIBLE);
                        Espacio7.setVisibility(View.GONE);
                        Espacio9.setVisibility(View.GONE);
                        storageReference.child(Conf.getPin()+"/antes/"+ja2.getString(6))
                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override

                            public void onSuccess(Uri uri) {
                                Glide.with(AdminRutinaBitacoraActivity.this)
                                        .load(uri)
                                        .error(R.drawable.caseta_logo)
                                        .centerInside()
                                        .into(foto_antes);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }



                    Espacio8.setVisibility(View.VISIBLE);
                    tituloDes.setVisibility(View.VISIBLE);
                    Espacio1.setVisibility(View.VISIBLE);
                    ObserDesG2.setVisibility(View.VISIBLE);
                    Observaciones22.setVisibility(View.VISIBLE);
                    Observaciones22.setText(ja2.getString(8));
                    Espacio2.setVisibility(View.VISIBLE);

                    if(ja2.getString(9).equals("")) {
                        Despues.setVisibility(View.GONE);
                        foto_despues.setVisibility(View.GONE);
                        Espacio3.setVisibility(View.GONE);
                        Espacio9.setVisibility(View.GONE);
                    }else {
                        Despues.setVisibility(View.VISIBLE);
                        foto_despues.setVisibility(View.VISIBLE);
                        Espacio4.setVisibility(View.VISIBLE);
                        storageReference.child(Conf.getPin()+"/despues/"+ja2.getString(9))
                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override

                            public void onSuccess(Uri uri) {
                                Glide.with(AdminRutinaBitacoraActivity.this)
                                        .load(uri)
                                        .error(R.drawable.caseta_logo)
                                        .centerInside()
                                        .into(foto_despues);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }



                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void insertarNota() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_nota.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.equals("error")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Nota No Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Nota No Registrada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), AdminRutinaBitacoraActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Nota  Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Nota  Registrada", Toast.LENGTH_SHORT).show();
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
                params.put("idResi", Conf.getResid());
                params.put("idOrden", Conf.getIdRutina());
                params.put("idUsuario", Conf.getUsu());
                params.put("notas", Notas.getText().toString().trim());
                try {

                params.put("id", ja2.getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //IMAGEN FOTO

    public void imgFoto(){
        Intent intentCaptura = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCaptura.addFlags(intentCaptura.FLAG_GRANT_READ_URI_PERMISSION);
        File foto = new File(getApplication().getExternalFilesDir(null),"antes.png");
        uri_img= FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",foto);
        intentCaptura.putExtra(MediaStore.EXTRA_OUTPUT,uri_img);
        startActivityForResult( intentCaptura, 0);
    }


    public void imgFoto2(){
        Intent intentCaptura = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCaptura.addFlags(intentCaptura.FLAG_GRANT_READ_URI_PERMISSION);
        File foto = new File(getApplication().getExternalFilesDir(null),"despues.png");
        uri_img2= FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",foto);
        intentCaptura.putExtra(MediaStore.EXTRA_OUTPUT,uri_img2);
        startActivityForResult( intentCaptura, 1);
    }


    private void showFileChooser1() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), 2);
    }
    private void showFileChooser2() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                foto=1;

                Bitmap bitmap = BitmapFactory.decodeFile(getApplicationContext().getExternalFilesDir(null) + "/antes.png");

                Antes.setVisibility(View.VISIBLE);
                foto_antes.setVisibility(View.VISIBLE);
                foto_antes.setImageBitmap(bitmap);



            }

            if (requestCode == 1) {
                foto=2;

                Bitmap bitmap = BitmapFactory.decodeFile(getApplicationContext().getExternalFilesDir(null) + "/despues.png");

                Despues.setVisibility(View.VISIBLE);
                foto_despues.setVisibility(View.VISIBLE);
                foto_despues.setImageBitmap(bitmap);
                Espacio4.setVisibility(View.VISIBLE);



            }
            if(requestCode==2){
                foto=3;
                filePath = data.getData();

                //Cómo obtener el mapa de bits de la Galería

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Antes.setVisibility(View.VISIBLE);
                foto_antes.setVisibility(View.VISIBLE);
                foto_antes.setImageBitmap(bitmap);


            }

            if(requestCode==3){
                foto=4;
                filePath = data.getData();

                //Cómo obtener el mapa de bits de la Galería

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Despues.setVisibility(View.VISIBLE);
                foto_despues.setVisibility(View.VISIBLE);
                foto_despues.setImageBitmap(bitmap);
                Espacio4.setVisibility(View.VISIBLE);


            }

        }
    }


    public void insertarAntes() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_antes.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.equals("error")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Antes No Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Antes No Registrada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), AdminRutinaBitacoraActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                }else{
                    if(foto==1){
                        upload1();
                    }else if(foto==3){
                        upload3();
                    }else{

                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Antes  Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Antes  Registrada", Toast.LENGTH_SHORT).show();
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

                if(foto==1){
                    foto_antes_nombre="R"+Conf.getIdRutina()+"-"+numero_aletorio3+numero_aletorio3+".png";
                }else if(foto==3){
                    foto_antes_nombre="R"+Conf.getIdRutina()+"-"+numero_aletorio3+numero_aletorio3+".png";
                }else{
                    foto_antes_nombre="";
                }

                params.put("idResi", Conf.getResid());
                params.put("idOrden", Conf.getIdRutina());
                params.put("observaciones", Observaciones1.getText().toString().trim());
                params.put("idUsuario", Conf.getUsu());
                params.put("fotografia", foto_antes_nombre);
                try {

                    params.put("id", ja2.getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void insertarDespues() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_despues.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.equals("error")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Después No Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Después No Registrada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), AdminRutinaBitacoraActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                }else{
                    if(foto==2){
                        upload2();
                    }else if(foto==4){
                        upload4();
                    }else{

                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Después  Registrada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Después Registrada", Toast.LENGTH_SHORT).show();
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

                if(foto==2){
                    foto_despues_nombre="R"+Conf.getIdRutina()+"-"+numero_aletorio+numero_aletorio+".png";
                }else if(foto==4){
                    foto_despues_nombre="R"+Conf.getIdRutina()+"-"+numero_aletorio+numero_aletorio+".png";
                }else{
                    foto_despues_nombre="";
                }

                params.put("idResi", Conf.getResid());
                params.put("idOrden", Conf.getIdRutina());
                params.put("observaciones", Observaciones2.getText().toString().trim());
                params.put("idUsuario", Conf.getUsu());
                params.put("fotografia", foto_despues_nombre);
                try {

                    params.put("id", ja2.getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void terminar() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas_terminar.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.equals("error")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Tarea No Terminada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Tarea No Terminada", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), AdminRutinaBitacoraActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).create().show();


                }else{

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminRutinaBitacoraActivity.this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Tarea Terminada")
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Tarea Terminada", Toast.LENGTH_SHORT).show();
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

                params.put("idResi", Conf.getResid());
                params.put("idOrden", Conf.getIdRutina());
                params.put("idUsuario", Conf.getUsu());

                try {
                    params.put("id", ja2.getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    public void upload1() {
        StorageReference mountainImagesRef = null;
        mountainImagesRef = storageReference.child(Conf.getPin()+"/antes/R"+Conf.getIdRutina()+"-"+numero_aletorio3+numero_aletorio3+".png");

        UploadTask uploadTask = mountainImagesRef.putFile(uri_img);



        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                pd.show(); // double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //System.out.println("Upload is " + progress + "% done");
                // Toast.makeText(getApplicationContext(),"Cargando Imagen INE " + progress + "%", Toast.LENGTH_SHORT).show();

            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AccesoActivity.this,"Pausado",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AdminRutinaBitacoraActivity.this,"Fallado", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();

            }
        });
    }

    public void upload2() {
        StorageReference mountainImagesRef2 = null;
        mountainImagesRef2 = storageReference.child(Conf.getPin()+"/despues/R"+Conf.getIdRutina()+"-"+numero_aletorio+numero_aletorio+".png");

        UploadTask uploadTask = mountainImagesRef2.putFile(uri_img2);



        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                pd.show(); // double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //System.out.println("Upload is " + progress + "% done");
                // Toast.makeText(getApplicationContext(),"Cargando Imagen INE " + progress + "%", Toast.LENGTH_SHORT).show();

            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AccesoActivity.this,"Pausado",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AdminRutinaBitacoraActivity.this,"Fallado", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();

            }
        });
    }


    public void upload3() {

        if(filePath != null){

            StorageReference ref= null;

            ref = storageReference.child(Conf.getPin()+"/antes/R"+Conf.getIdRutina()+"-"+numero_aletorio3+numero_aletorio3+".png");

            ref.putFile(filePath)

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.show();
                        }

                    })

                    .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(AccesoActivity.this,"Pausado",Toast.LENGTH_SHORT).show();
                        }
                    }).

                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AdminRutinaBitacoraActivity.this,"Fallado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            pd.dismiss();
                        }
                    });

        }

    }

    public void upload4() {

        if(filePath != null){

            StorageReference ref= null;

            ref = storageReference.child(Conf.getPin()+"/despues/R"+Conf.getIdRutina()+"-"+numero_aletorio+numero_aletorio+".png");

            ref.putFile(filePath)

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.show();
                        }

                    })

                    .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(AccesoActivity.this,"Pausado",Toast.LENGTH_SHORT).show();
                        }
                    }).

                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AdminRutinaBitacoraActivity.this,"Fallado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            pd.dismiss();
                        }
                    });

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
