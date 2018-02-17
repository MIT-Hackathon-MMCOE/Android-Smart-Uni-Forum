package hackathon.com.smartuniforum.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hackathon.com.smartuniforum.API.API;
import hackathon.com.smartuniforum.Adapter.SectionPagerAdapter;
import hackathon.com.smartuniforum.Config.ProgressDialogConfig;
import hackathon.com.smartuniforum.Config.RetrofitConfig;
import hackathon.com.smartuniforum.Models.UserModel;
import hackathon.com.smartuniforum.R;
import hackathon.com.smartuniforum.SharedPreferences.LoginSessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    String TAG = getClass().getSimpleName();
    RetrofitConfig retrofitConfig;
    View rootView;
    EditText etEmail,etPassword;
    Button btnLogin;
    LoginSessionManager loginSessionManager;


    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initui();
        login();
        return rootView;
    }

    void initui(){
        etEmail = rootView.findViewById(R.id.etUsername);
        etPassword = rootView.findViewById(R.id.etPassword);
        btnLogin = rootView.findViewById(R.id.btnlogin);
        loginSessionManager = new LoginSessionManager(getActivity());
        retrofitConfig = new RetrofitConfig();
    }

    void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError(getString(R.string.cannot_be_empty));
                }else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError(getString(R.string.cannot_be_empty));
                }else{

                    final ProgressDialog progressDialog = ProgressDialogConfig.config(getActivity(), getString(R.string.verifying));
                    progressDialog.show();
                    Retrofit retrofit = retrofitConfig.config();
                    API api = retrofit.create(API.class);

                    RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
                    RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString());

                    Call<UserModel> call = api.login(emailBody, passwordBody);

                    call.enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                            progressDialog.dismiss();
                            loginSessionManager.createLoginSession(response.body().getEmail(),
                                    response.body().getKey(),
                                    response.body().getPk(),
                                    response.body().getUsername(),
                                    response.body().getPassword());
                            startActivity(new Intent(getActivity(), BottomNavActivity.class));
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d(TAG, t.getMessage());
                            Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
