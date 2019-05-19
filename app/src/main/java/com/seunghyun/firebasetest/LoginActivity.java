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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        loginButton.setOnClickListener(v -> {
            int nameLength = Objects.requireNonNull(textInputEditText.getText()).toString().length();
            if (nameLength < 1)
                Toast.makeText(LoginActivity.this, "닉네임을 입력해주세요.", Toast.LENGTH_LONG).show();
            else if (nameLength > 10)
                Toast.makeText(LoginActivity.this, "닉네임은 10자 이하여야 됩니다.", Toast.LENGTH_LONG).show();
            else {
                final Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 10) {
                    textInputLayout.setError("닉네임은 10자 이하여야 됩니다.");
                } else {
                    textInputLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        loginButton = findViewById(R.id.login_button);
        textInputLayout = findViewById(R.id.text_input_layout);
        textInputEditText = findViewById(R.id.text_input_edit_text);
    }
}
