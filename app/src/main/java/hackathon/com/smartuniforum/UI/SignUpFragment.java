package hackathon.com.smartuniforum.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import hackathon.com.smartuniforum.API.API;
import hackathon.com.smartuniforum.Config.ProgressDialogConfig;
import hackathon.com.smartuniforum.Config.RetrofitConfig;
import hackathon.com.smartuniforum.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class SignUpFragment extends Fragment {

    String TAG = getClass().getSimpleName();
    String encodedImage;
    Uri filePath;
    View rootView;
    CircleImageView circleImageView;
    EditText etName, etConfirmPassword,etEmail, etPassword;
    Button btnSignUp;
    FloatingActionButton floatingActionButton;
    RetrofitConfig retrofitConfig;
    int PICK_IMAGE_REQUEST = 0, STORAGE_PERMISSION = 1;
    Bitmap bitmap;
    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initui();
        floatingActionButton= rootView.findViewById(R.id.fbAddPhoto);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                    /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);*/
                } else {
                    requestStoragePermission();
                }
            }
        });
        signup(etName.getText().toString(),
                etConfirmPassword.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString());
        return rootView;
    }

    void initui(){
        etName = rootView.findViewById(R.id.etName);
        etConfirmPassword = rootView.findViewById(R.id.etConfirmPassword);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        btnSignUp = rootView.findViewById(R.id.btnSignUp);
        circleImageView = rootView.findViewById(R.id.userImageView);

        retrofitConfig = new RetrofitConfig();
    }

    void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
    }

    void signup(final String name, final String email, final String phone, final String password){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Name Cannot Be Empty!");
                } else if (etConfirmPassword.getText().toString().isEmpty()) {
                    etConfirmPassword.setError("Phone Cannot Be Empty!");
                } else if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email Cannot Be Empty!");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password Cannot Be Empty!");
                } else {
                    if (encodedImage.isEmpty()) {
                        Toast.makeText(getActivity(), getString(R.string.click_the_add_button), Toast.LENGTH_LONG).show();
                    }else {

                        ProgressDialog progressDialog = ProgressDialogConfig.config(getActivity(), getString(R.string.signing_up));
                        progressDialog.show();

                        Retrofit retrofit = retrofitConfig.config();
                        API api = retrofit.create(API.class);

                        File file = new File(getPath(filePath));
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part imageBody= MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                        RequestBody nameBody= RequestBody.create(MediaType.parse("text/plain"), name);
                        RequestBody emailBody= RequestBody.create(MediaType.parse("text/plain"), email);
                        RequestBody phoneBody= RequestBody.create(MediaType.parse("text/plain"), phone);
                        RequestBody passwordBody= RequestBody.create(MediaType.parse("text/plain"), password);

                        Call<ResponseBody> call = api.register(nameBody, emailBody, phoneBody, passwordBody, imageBody);
                        etName.setText("");
                        etConfirmPassword.setText("");
                        etPassword.setText("");
                        etEmail.setText("");
                        circleImageView.setImageDrawable(getActivity().getDrawable(R.drawable.upload_placeholder_300x300));
                    }
                }
            }
        });

    }

    private String getPath(Uri filePath) {
        String path = null;

        return path;
    }

    void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        getString(R.string.permission_required_to_access_storage),
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                circleImageView.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
