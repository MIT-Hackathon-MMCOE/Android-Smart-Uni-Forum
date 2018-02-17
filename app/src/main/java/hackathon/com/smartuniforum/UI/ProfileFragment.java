package hackathon.com.smartuniforum.UI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import hackathon.com.smartuniforum.Adapter.SectionPagerAdapter;
import hackathon.com.smartuniforum.Models.ProfileModel;
import hackathon.com.smartuniforum.R;

public class ProfileFragment extends Fragment {

    String TAG = getClass().getSimpleName();
    View rootView;
    SectionPagerAdapter sectionPagerAdapter;
    ViewPager viewPager;
    ProfileModel profileModel;
    TextView tvName,tvUsername;
    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.profileToolbar);
        getActivity().setActionBar(toolbar);
        viewPager = rootView.findViewById(R.id.vpToolbarProfile);
        tvName = rootView.findViewById(R.id.tvToolbarName);
        tvUsername = rootView.findViewById(R.id.tvToolbarCollege);
        sectionPagerAdapter = new SectionPagerAdapter(getActivity().getSupportFragmentManager());
        sectionPagerAdapter.addFragments(new PostFragment());
        sectionPagerAdapter.addFragments(new AboutFragment());
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.tlToolbarTabs);
        profileModel = BottomNavActivity.profileConfig.getProfileModel();
        tvName.setText(profileModel.getFirst_name() + " " + profileModel.getLast_name());
        tvUsername.setText(profileModel.getUsername());
        Log.d(TAG, profileModel.getEmail());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return rootView;
    }
}
