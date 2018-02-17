package hackathon.com.smartuniforum.Models;

/**
 * Created by aditya on 16/2/18.
 */

public class UserModel {

    public int getPk() {
        return pk;
    }

    int pk;
    String username;
    String key;
    String first_name;
    String last_name;

    public String getPassword() {
        return password;
    }

    String password;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    String email;

    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
