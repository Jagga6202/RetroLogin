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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Button register , login ;
    EditText name, email , password ;
    Boolean CheckEditText ;
    String NameHolder, EmailHolder, PasswordHolder ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button)findViewById(R.id.button);
        login = (Button)findViewById(R.id.button2);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    registerUser(NameHolder,EmailHolder,PasswordHolder);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);

            }
        });
    }
    public void GetCheckEditTextIsEmptyOrNot(){
        NameHolder = name.getText().toString();
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void registerUser(final String name,final String email, final String password){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiRetro.BaseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRetro apiRetro=retrofit.create(ApiRetro.class);

        Call<String> call=apiRetro.registerex(name,email,password);
        progressDialog = ProgressDialog.show(MainActivity.this,"Loading Data",null,true,true);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
          progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                Log.e("Response",response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Faliure",t.getMessage());
            }
        });




    }
}
