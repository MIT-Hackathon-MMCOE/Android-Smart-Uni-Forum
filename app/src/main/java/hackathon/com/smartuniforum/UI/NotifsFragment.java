package hackathon.com.smartuniforum.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hackathon.com.smartuniforum.R;

public class NotifsFragment extends Fragment {

    View rootView;
    public NotifsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifs, container, false);
        return rootView;
    }


}
