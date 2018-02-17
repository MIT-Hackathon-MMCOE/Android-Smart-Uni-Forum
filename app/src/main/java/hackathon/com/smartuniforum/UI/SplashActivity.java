package hackathon.com.smartuniforum.UI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hackathon.com.smartuniforum.R;
import hackathon.com.smartuniforum.SharedPreferences.LoginSessionManager;

public class SplashActivity extends AppCompatActivity {

    LoginSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new LoginSessionManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.isLoggedIn()){
                    startActivity(new Intent(getApplicationContext(),BottomNavActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }
        },3000);
    }
}
