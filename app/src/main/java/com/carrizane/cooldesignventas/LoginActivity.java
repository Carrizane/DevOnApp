package com.carrizane.cooldesignventas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carrizane.cooldesignventas.controller.ApiClient;
import com.carrizane.cooldesignventas.controller.ApiInterface;
import com.carrizane.cooldesignventas.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email, pass;

    Button login;

    boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.emailLogin);
        pass = (EditText) findViewById(R.id.passLogin);

        login = (Button) findViewById(R.id.loginBtn);

        SharedPreferences prefs = getSharedPreferences("PREFS", 0);
        isLogged = prefs.getBoolean("isLogged", false);
        if (isLogged){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        login.setOnClickListener( (view) -> {
            logInUser(email.getText().toString(), pass.getText().toString());
        });
    }

    public void logInUser(String e, String p){
        ApiInterface apiInterface = ApiClient.getApiLoginClient().create(ApiInterface.class);
        Call<User> call = apiInterface.signInUser(e, p);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null){
                    SharedPreferences prefs = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nameUser", response.body().getName());
                    editor.putString("last_nameUser", response.body().getLast_name());
                    editor.putBoolean("isLogged", true);
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                email.setError("Email is not correct!");
                pass.setError("Password is not correct!");
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}