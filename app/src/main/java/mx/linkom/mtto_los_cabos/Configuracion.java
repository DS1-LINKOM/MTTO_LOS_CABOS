package mx.linkom.mtto_los_cabos;

import android.content.Context;
import android.content.SharedPreferences;

public class Configuracion {

    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String KEY_PIN = "cpin";
    private final String KEY_NOMRE = "cnombrere";
    private final String KEY_BD = "cbd";
    private final String KEY_BDUSU = "cbdusu";
    private final String KEY_BDCON = "cbdcon";


    private final String KEY_EMAIL = "cor";
    private final String KEY_PASS = "con";
    private final String KEY_USU_TIPO = "ctipo";
    private final String KEY_USU = "cusu";
    private final String KEY_RESI = "cresid";
    private final String KEY_IDRutina = "cidrutina";
    private final String KEY_IDAsignar = "cidasignar";
    private final String KEY_NOMBRE= "usu_nombre";

    private Context mContext;

    public Configuracion(Context context){
        mContext = context;
    }

    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    //PIN
    public String getPin(){
        return getSettings().getString(KEY_PIN, null);
    }

    public void setPin(String cpin){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_PIN, cpin );
        editor.commit();
    }

    //NOMBRE_RESIDENCIAL
    public String getNomResi(){
        return getSettings().getString(KEY_NOMRE, null);
    }

    public void setNomResi(String cnombrere){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_NOMRE, cnombrere );
        editor.commit();
    }

    //BD
    public String getBd(){
        return getSettings().getString(KEY_BD, null);
    }

    public void setBd(String cbd){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_BD, cbd );
        editor.commit();
    }

    //BD_USUARIO
    public String getBdUsu(){
        return getSettings().getString(KEY_BDUSU, null);
    }

    public void setBdUsu(String cbdusu){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_BDUSU, cbdusu );
        editor.commit();
    }


    //BD_CONTRASEÑA
    public String getBdCon(){
        return getSettings().getString(KEY_BDCON, null);
    }

    public void setBdCon(String cbdcon){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_BDCON, cbdcon );
        editor.commit();
    }


    //Nombre
    public String getNombre(){
        return getSettings().getString(KEY_NOMBRE, null);
    }

    public void setNombre(String usu_nombre){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_NOMBRE, usu_nombre );
        editor.commit();
    }

//RESIDENCIAL
    public String getResid(){
        return getSettings().getString(KEY_RESI, null);
    }

    public void setResid(String cresid){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_RESI, cresid );
        editor.commit();
    }
//USUARIO
    public String getUsu(){
        return getSettings().getString(KEY_USU, null);
    }

    public void setUsu(String cusu){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USU, cusu );
        editor.commit();
    }
//CORREO
    public String getUserEmail(){
        return getSettings().getString(KEY_EMAIL, null);
    }

    public void setUserEmail(String cor){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EMAIL, cor );
        editor.commit();
    }
//PASWORD
    public String getUserPass(){
        return getSettings().getString(KEY_PASS, null);
    }

    public void setUserPass(String con){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_PASS, con );
        editor.commit();
    }
 //TIPO USUARIO
    public String getUserTipo(){
        return getSettings().getString(KEY_USU_TIPO, null);
    }

    public void setUserTipo(String ctipo){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USU_TIPO, ctipo );
        editor.commit();
    }

//ID RUTINA
    public String getIdRutina(){
        return getSettings().getString(KEY_IDRutina, null);
    }

    public void setIdRutina(String cidrutina){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_IDRutina, cidrutina );
        editor.commit();
    }
//ID_ASIGNAR
    public String getIdAsignar(){
        return getSettings().getString(KEY_IDAsignar, null);
    }

    public void setIdAsignar(String cidasignar){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_IDAsignar, cidasignar );
        editor.commit();
    }
}








