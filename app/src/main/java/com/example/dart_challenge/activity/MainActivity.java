package com.example.dart_challenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dart_challenge.R;

/*
login activity
 */

public class MainActivity extends AppCompatActivity {

    private final String correctEmail = "usuario@email.com";
    private final String correctSenha = "senha123";
    EditText etEmail, etPassword;
    Button bLogin;
    TextView tvWrongPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        tvWrongPassword = findViewById(R.id.textWrong);
        tvWrongPassword.setVisibility(View.GONE);
    }

    public void tryLogin(View view) {

        // check if the fields are empty
        if (etEmail.getText().toString().equals("")) {
            tvWrongPassword.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Preencha o campo de e-mail", Toast.LENGTH_SHORT).show();
            return;
        } else if (!etEmail.getText().toString().contains("@")) {
            tvWrongPassword.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "e-mail deve conter @", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etPassword.getText().toString().equals("")) {
            tvWrongPassword.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Preencha o campo de senha", Toast.LENGTH_SHORT).show();
            return;
        } else if (etPassword.getText().toString().length() < 6) {
            tvWrongPassword.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Senha deve ter mais de 6 digitos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etEmail.getText().toString().equals(correctEmail) && etPassword.getText().toString().equals(correctSenha)) {
            tvWrongPassword.setVisibility(View.GONE);
            // must open the list
            Intent intent = new Intent(this, listActivity.class);
            startActivity(intent);

        } else
            tvWrongPassword.setVisibility(View.VISIBLE);
    }
}
