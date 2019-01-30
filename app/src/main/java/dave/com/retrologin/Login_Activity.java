package dave.com.retrologin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login_Activity extends AppCompatActivity {
    Button login ;
    ProgressDialog progressDialog;
    EditText email, password ;
    String EmailHolder, PasswordHolder ;
    boolean CheckEditText ;
    String ServerLoginURL = "http://10.0.2.2/androidphp/login.php";
    public static final String UserEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.button);
        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    loginuser(EmailHolder,PasswordHolder);
                }
                else {

                    Toast.makeText(Login_Activity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void GetCheckEditTextIsEmptyOrNot(){
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void loginuser(final String emailh, final String passwordh){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiRetro.BaseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRetro apiRetro=retrofit.create(ApiRetro.class);

        Call<String> call=apiRetro.loginex(emailh,passwordh);
        progressDialog = ProgressDialog.show(Login_Activity.this,"Loading Data",null,true,true);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                String res=response.body();
                if(res.equalsIgnoreCase("Data Matched")){
                    finish();
                    Intent intent = new Intent(Login_Activity.this, profile.class);
                    intent.putExtra(UserEmail,emailh);
                    startActivity(intent);
                }
                Toast.makeText(Login_Activity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                Log.e("Faliure",response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Login_Activity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Faliure",t.getMessage());
            }
        });
}
}
