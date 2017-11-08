package com.android.qdbsample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etEmail, etPassword;
    ImageView ivLogo;
    TextView tvLogin;
    SharedPreferences settings;
    SharedPreferences.Editor editor2;
    Boolean isLogin;
    String email, password;
    String URL = "http://api.smesconnect.com/api/web/index.php/v1/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bindComponentsOnClick();

        settings = getApplicationContext().getSharedPreferences("Loginpref", Context.MODE_PRIVATE);
        editor2 = settings.edit();

        Boolean flag = settings.getBoolean("login", false);

        if (flag == true) {
            Intent intent = new Intent(LoginActivity.this, DashBoard.class);
            finish();
            startActivity(intent);
        } else {
//            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//            startActivity(intent);

        }

    }

    private void bindComponentsOnClick() {
        btnLogin.setOnClickListener(this);
    }

    private void init() {

        btnLogin = findViewById(R.id.btnLogin);
        ivLogo = findViewById(R.id.ivLogo);
        tvLogin = findViewById(R.id.tvLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {

            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            isLogin = true;
            editor2.putBoolean("login", isLogin);
            editor2.commit();
            new FetchAsync().execute();
        }
    }

    class FetchAsync extends AsyncTask<Void, Void, String> {

        Boolean errorStatus = null;
        String message, token;
        ProgressDialog pd;
        SharedPreferences sharedPreferences;

        //ProgressDialogActivity objProgressDialogAcivity = new ProgressDialogActivity();

        @Override
        protected void onPreExecute() {

           // objProgressDialogAcivity.showProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", password)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                String mMessage = response.body().string();
                JSONObject jsonObject1 = new JSONObject(mMessage);

                errorStatus = jsonObject1.getBoolean("errorStatus");
                message = jsonObject1.getString("message");

                if (errorStatus == true) {

                    Log.d("TAG", message.toString());
                    Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(LoginActivity.this, DashBoard.class);
                    finish();
                    startActivity(intent);

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                    token = jsonObject2.getString("token");

                    Log.d("TAG", message.toString());
                    Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //objProgressDialogAcivity.dismissProgressDialog();
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Token", token);
            editor.commit();

            if (errorStatus == true) {
                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }

}
