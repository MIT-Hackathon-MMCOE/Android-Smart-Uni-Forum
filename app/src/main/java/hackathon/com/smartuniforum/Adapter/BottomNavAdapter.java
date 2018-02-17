package hackathon.com.smartuniforum.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 16/2/18.
 */

public class BottomNavAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentsList = new ArrayList<>();

    public BottomNavAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment fragment){
        fragmentsList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}

