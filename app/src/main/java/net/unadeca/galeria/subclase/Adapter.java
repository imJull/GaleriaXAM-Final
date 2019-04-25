package net.unadeca.galeria.subclase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();


    public  void agregarFragmento(Fragment f){
        fragments.add(f);
    }

    public Adapter (FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
