package hackathon.com.smartuniforum.API;

import java.util.List;

import hackathon.com.smartuniforum.Models.FeedModel;
import hackathon.com.smartuniforum.Models.ProfileModel;
import hackathon.com.smartuniforum.Models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by aditya on 16/2/18.
 */

public interface API {

    @Multipart
    @POST("/rest-auth/login/")
    Call<UserModel> login(
            @Part("username") RequestBody email,
            @Part("password") RequestBody password);

    @Multipart
    @POST("/rest-auth/registration")
    Call<ResponseBody> register(@Part("username") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password1") RequestBody password1,
                                @Part("password2") RequestBody password2,
                                @Part MultipartBody.Part image);

    @GET("/rest/user/")
    Call<ProfileModel> getUser(
            @Header("Authorization") String token
    );

    @GET("/api-auth/question/")
    Call<List<FeedModel>> getQuestions(

    );
}
