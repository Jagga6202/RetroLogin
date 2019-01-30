package dave.com.retrologin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRetro {
    public static final String BaseURL="http://10.0.2.2/androidphp/";

    @FormUrlEncoded
    @POST("register.php")
    Call<String> registerex(@Field("name") String name, @Field("email")String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<String> loginex(@Field("email")String email,@Field("password") String password);
}
