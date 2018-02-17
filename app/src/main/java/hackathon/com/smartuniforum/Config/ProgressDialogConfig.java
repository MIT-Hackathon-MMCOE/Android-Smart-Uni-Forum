package hackathon.com.smartuniforum.Config;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by aditya on 16/2/18.
 */

public class ProgressDialogConfig {

    public static ProgressDialog config(Context context, String message){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
