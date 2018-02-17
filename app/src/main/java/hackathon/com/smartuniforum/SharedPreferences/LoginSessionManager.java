package hackathon.com.smartuniforum.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import hackathon.com.smartuniforum.UI.LoginActivity;

/**
 * Created by aditya on 16/2/18.
 */

public class LoginSessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    String PREFERENCE_NAME = "Login Preference";
    String IS_LOGGED_IN = "IsLoggedIn";
    public String LOGIN_PK = "pk";
    public String LOGIN_EMAIL = "email";
    public String LOGIN_KEY = "key";
    public String LOGIN_USERNAME = "username";
    public String LOGIN_PASSWORD= "password";

     public LoginSessionManager(Context ctx){
        this.context = ctx;
        preferences = context.getSharedPreferences(PREFERENCE_NAME,0);
        editor = preferences.edit();
    }

    public void createLoginSession(String email, String key, int pk, String username, String password){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(LOGIN_EMAIL,email);
        editor.putString(LOGIN_KEY,key);
        editor.putInt(LOGIN_PK,pk);
        editor.putString(LOGIN_USERNAME,username);
        editor.putString(LOGIN_PASSWORD,password);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(LOGIN_EMAIL, preferences.getString(LOGIN_EMAIL, null));
        user.put(LOGIN_KEY, preferences.getString(LOGIN_KEY, null));
        return user;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void logOutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
