package hackathon.com.smartuniforum.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.HashMap;

import hackathon.com.smartuniforum.API.API;
import hackathon.com.smartuniforum.Adapter.BottomNavAdapter;
import hackathon.com.smartuniforum.Config.ProfileConfig;
import hackathon.com.smartuniforum.Config.ProgressDialogConfig;
import hackathon.com.smartuniforum.Config.RetrofitConfig;
import hackathon.com.smartuniforum.Models.ProfileModel;
import hackathon.com.smartuniforum.R;
import hackathon.com.smartuniforum.SharedPreferences.LoginSessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BottomNavActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    AHBottomNavigation ahBottomNavigation;
    Pager pager;
    BottomNavAdapter bottomNavAdapter;
    LoginSessionManager loginSessionManager;
    HashMap<String, String> userDetails;
    RetrofitConfig retrofitConfig;
    private boolean notificationVisible;
    public ProfileModel profileModel;
    public static ProfileConfig profileConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        initui();
        setViewPager();
        bottomNavItems();
        bottomNavConfig();
        getUser("Token " + userDetails.get(loginSessionManager.LOGIN_KEY));

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(!wasSelected){
                    pager.setCurrentItem(position);
                }
                return true;
            }
        });
    }

    void initui(){

        loginSessionManager = new LoginSessionManager(BottomNavActivity.this);
        userDetails = loginSessionManager.getUserDetails();
        ahBottomNavigation = findViewById(R.id.bottom_navigation);
        pager = findViewById(R.id.viewPager);
        bottomNavAdapter = new BottomNavAdapter(getSupportFragmentManager());
        pager.setPagingEnabled(false);
        retrofitConfig = new RetrofitConfig();
        profileConfig = new ProfileConfig();
    }

    private void setViewPager() {
        FeedFragment fragment1 = new FeedFragment();
        bottomNavAdapter.addFragments(fragment1);
        NotifsFragment fragment2 = new NotifsFragment();
        bottomNavAdapter.addFragments(fragment2);
        ProfileFragment fragment3 = new ProfileFragment();
        bottomNavAdapter.addFragments(fragment3);
        pager.setAdapter(bottomNavAdapter);
    }

    private void bottomNavConfig() {
        ahBottomNavigation.setCurrentItem(0);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setBehaviorTranslationEnabled(true);
    }

    private void bottomNavItems() {

        AHBottomNavigationItem feedItem = new AHBottomNavigationItem(getString(R.string.feeds),
                getDrawable(R.drawable.feed));

        AHBottomNavigationItem notifsItem = new AHBottomNavigationItem(getString(R.string.notifications),
                getDrawable(R.mipmap.ic_notifications_black_24dp));

        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(getString(R.string.me),
                getDrawable(R.mipmap.ic_face_black_24dp));

        ahBottomNavigation.addItem(feedItem);
        ahBottomNavigation.addItem(notifsItem);
        ahBottomNavigation.addItem(profileItem);
    }

    /*private void setNotifsCount() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification ahNotification = new AHNotification.Builder()
                        .setText("2")
                        .setBackgroundColor(getColor(R.color.colorAccent))
                        .setTextColor(Color.BLACK)
                        .build();
                ahBottomNavigation.setNotification(ahNotification,1);

                notificationVisible = true;
            }
        },1000);
    }*/

    void getUser(String token){

        Retrofit retrofit = retrofitConfig.config();
        API api = retrofit.create(API.class);

        Call<ProfileModel> call = api.getUser(token);
        profileModel = new ProfileModel();
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                profileModel.setEmail(response.body().getEmail());
                profileModel.setPk(response.body().getPk());
                profileModel.setUsername(response.body().getUsername());
                profileModel.setFirst_name(response.body().getFirst_name());
                profileModel.setLast_name(response.body().getLast_name());
                profileConfig.setProfileModel(profileModel);
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
    }
}