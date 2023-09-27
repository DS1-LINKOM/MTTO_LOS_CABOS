package mx.linkom.mtto_los_cabos.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdminControlador extends FragmentPagerAdapter {
    int numofTabs;

    public PagerAdminControlador(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numofTabs=behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RutinasAdmin();
            case 1:
                return new ProgramadosAdmin();
            case 2:
                return new Asignar();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
