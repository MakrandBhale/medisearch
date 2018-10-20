package com.makarand.medisearch;

import android.content.Intent;
import android.media.tv.TvContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ProgressBar waiter;
    Boolean touchable = true;
    FirebaseAuth auth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        waiter = findViewById(R.id.waiter);
        auth = FirebaseAuth.getInstance();
    }

    public void goToSignup(View view){
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void login(View view){
        if(!validate()) return;
        waiter.setVisibility(View.VISIBLE);
        toggleTouchable();
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    toggleTouchable();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toggleTouchable();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private boolean validate() {
        EditText emailText = findViewById(R.id.email);
        EditText passText = findViewById(R.id.password);
        email = emailText.getText().toString().trim();
        password = passText.getText().toString().trim();
        if(email.length() <= 4 || password.length() <= 4){
            Toast.makeText(this, "Email and password should be at least 4 characters long.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void toggleTouchable() {
        if(touchable) {
            waiter.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            touchable = false;
        }
        else {
            waiter.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            touchable = true;
        }
    }
}
