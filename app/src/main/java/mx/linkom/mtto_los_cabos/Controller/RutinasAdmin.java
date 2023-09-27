package mx.linkom.mtto_los_cabos.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

import mx.linkom.mtto_los_cabos.AdminRutinaBitacoraActivity;
import mx.linkom.mtto_los_cabos.Configuracion;
import mx.linkom.mtto_los_cabos.R;

public class RutinasAdmin extends Fragment {

    private TextView evento;
    private GridView gridList;
    private Configuracion Conf;
    JSONArray ja1;
    String Valor_dia;


    public RutinasAdmin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutinas_admin, container, false);



        Conf = new Configuracion(getActivity());
        evento = (TextView) view.findViewById(R.id.evento);
        gridList = (GridView) view.findViewById(R.id.gridList);

        Calendar c = Calendar.getInstance();

        // Array con los dias de la semana
        String[] strDays = new String[]{
                "DOMINGO",
                "LUNES",
                "MARTES",
                "MIERCOLES",
                "JUEVES",
                "VIERNES",
                "SABADO"};

        Valor_dia = strDays[c.get(Calendar.DAY_OF_WEEK) - 1];

        evento.setText(Valor_dia);


        recoger1();
        return view;
    }

    public void recoger1() {

        String URL = "https://communitycabo.sist.com.mx/plataforma/casetaV2/controlador/community_cabo_mtto/admin_rutinas.php?bd_name="+Conf.getBd()+"&bd_user="+Conf.getBdUsu()+"&bd_pwd="+Conf.getBdCon();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        ja1 = new JSONArray(response);

                        llenado();

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
                params.put("idUsu", Conf.getUsu());
                params.put("estatus", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void llenado(){

        ArrayList<RutinasClassGrid> recibir = new ArrayList<RutinasClassGrid>();
        for (int i = 0; i < ja1.length(); i += 3) {
            try {
                recibir.add(new RutinasClassGrid(ja1.getString(i +1), "ID:"+ja1.getString(i + 0)+" / USUARIO: "+ja1.getString(i + 2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        gridList.setAdapter(new adaptador_RutinasAdmin(getActivity(), R.layout.activity_lista_lista, recibir){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {

                    final TextView title = (TextView) view.findViewById(R.id.title);
                    if (title != null)
                        title.setText(((RutinasClassGrid) entrada).getTitle());


                    final TextView subtitle = (TextView) view.findViewById(R.id.sub);
                    if (subtitle != null)
                        subtitle.setText(((RutinasClassGrid) entrada).getSubtitle());

                    gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                            int posicion=position*3;
                            try {
                                Conf.setIdRutina(ja1.getString(posicion));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent(getActivity(), AdminRutinaBitacoraActivity.class);
                            startActivity(i);


                        }
                    });


                }
            }

        });





    }

}