package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        loginBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        loginBinding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn()
    {
        String email = loginBinding.emailSignIn.getText().toString();
        String password = loginBinding.passwordSignIn.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password Length must be greater than 6 letters.", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Empty Fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        loginBinding.setLoading(true);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loginBinding.setLoading(false);
                            Toast.makeText(LoginActivity.this,"Successfully Login",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            loginBinding.setLoading(false);
                            Toast.makeText(LoginActivity.this,"Failed Login,Check your email or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }
}