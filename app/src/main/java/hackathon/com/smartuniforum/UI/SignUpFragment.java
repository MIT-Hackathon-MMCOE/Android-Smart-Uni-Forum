package hackathon.com.smartuniforum.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hackathon.com.smartuniforum.R;

public class SignUpFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return rootView;
    }
}
