package mx.linkom.mtto_los_cabos.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerControlador extends FragmentPagerAdapter {
    int numofTabs;

    public PagerControlador(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numofTabs=behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Rutinas();
            case 1:
                return new Programados();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
