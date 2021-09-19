package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ActivityRegisterBinding;
import com.example.ecommerceapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    public static String TAG = "Ecommerce Log";
    private SharedPreferences sharedPreferences ;
    boolean isFirstTime;

    //user info
    String userID;
    String username;
    String email;
    String password;
    String address;
    String city;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
        }

        registerBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        registerBinding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // handling if it the first time install the app show the onBoarding to the user
        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if(isFirstTime)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();
            Intent intent = new Intent(RegisterActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void signIn() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void signUp() {
        username = registerBinding.username.getText().toString();
        email = registerBinding.emailReg.getText().toString();
        address = registerBinding.adderss.getText().toString();
        city = registerBinding.city.getText().toString();
        password = registerBinding.passwordReg.getText().toString();
        phone = registerBinding.phoneNumber.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Name is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Address is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "City is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Phone Number is Empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password Length must be greater than 6 letters.", Toast.LENGTH_LONG).show();
            return;
        }
        if (username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()|| city.trim().isEmpty()
                || address.trim().isEmpty() || phone.trim().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Empty Fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        // create user
        registerBinding.setLoading(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            registerBinding.setLoading(false);
                            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(userID,username,email,address,phone,city);
                            firestore.collection("Users").document(username)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Successfully Register",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        } else {
                            registerBinding.setLoading(false);
                            Toast.makeText(RegisterActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"error -> "+task.getException().getMessage());
                        }
                    }
                });


    }

    private void saveUserData()
    {
        Map<String,String> map = new HashMap<>();
        map.put("userAddress",address);
        map.put("userName",username);
        map.put("userPhoneNumber",phone);
        map.put("userEmail",email);
        map.put("userCity",city);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User(userID,username,email,address,phone,city);
        firestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this,"Successfully Register",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Something Went Wrong!!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}