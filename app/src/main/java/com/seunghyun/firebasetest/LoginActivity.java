package com.seunghyun.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextInputLayout nameLayout, passwordLayout;
    TextInputEditText nameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        loginButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(nameEditText.getText()).toString();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString();
            int nameLength = Objects.requireNonNull(nameEditText.getText()).toString().length();
            int passwordLength = Objects.requireNonNull(passwordEditText.getText()).toString().length();
            if (nameLength < 1)
                Toast.makeText(LoginActivity.this, R.string.require_name, Toast.LENGTH_LONG).show();
            else if (nameLength > 10)
                Toast.makeText(LoginActivity.this, R.string.name_length, Toast.LENGTH_LONG).show();
            else if (passwordLength < 1)
                Toast.makeText(LoginActivity.this, R.string.require_password, Toast.LENGTH_LONG).show();
            else if (passwordLength > 16 || passwordLength < 4)
                Toast.makeText(LoginActivity.this, R.string.password_length, Toast.LENGTH_LONG).show();
            else {
                final Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 10) nameLayout.setError(getString(R.string.name_length));
                else nameLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 16) passwordLayout.setError(getString(R.string.password_length));
                else passwordLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        loginButton = findViewById(R.id.login_button);
        nameLayout = findViewById(R.id.name_layout);
        nameEditText = findViewById(R.id.name_editText);
        passwordLayout = findViewById(R.id.password_layout);
        passwordEditText = findViewById(R.id.password_editText);
    }
}
