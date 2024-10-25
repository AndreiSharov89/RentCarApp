package com.example.rentcarapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentcarapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.login);
        etPassword = findViewById(R.id.password);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!login.equals("user123") || !password.equals("0000")) {
                Toast.makeText(LoginActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
            } else {
                saveUserInfo(login, password);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveUserInfo(String login, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.apply();
    }
}
