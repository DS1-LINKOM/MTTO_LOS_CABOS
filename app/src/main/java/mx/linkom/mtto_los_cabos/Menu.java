package mx.linkom.mtto_los_cabos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Menu extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, android.widget.PopupMenu.OnMenuItemClickListener {
    private mx.linkom.mtto_los_cabos.Configuracion Conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_documentos);


        Conf = new mx.linkom.mtto_los_cabos.Configuracion(this);


    }

    public void showPopup (View v){
        android.widget.PopupMenu popup= new android.widget.PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenu().findItem(R.id.Usuario).setTitle(Conf.getNombre());

        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Inicio) {

            if( Conf.getUserTipo().equals("1")){
                Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);
                startActivity(i);
                finish();
            }else if(Conf.getUserTipo().equals("2")){
                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(i);
                finish();
            }



            return true;

        }else if (id == R.id.Cerrar) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            startActivity(new Intent(getBaseContext(), mx.linkom.mtto_los_cabos.MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();

            return true;
        }else if (id == R.id.Versión) {

            return true;
        }else if (id == R.id.Usuario) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
