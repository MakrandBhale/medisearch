package com.makarand.medisearch;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.Users;

public class SignupActivity extends AppCompatActivity {
    ProgressBar waiter;
    Boolean touchable = true;
    FirebaseAuth auth;
    String email,password, mobile, name;
    DatabaseReference usersRef;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        waiter = findViewById(R.id.waiter);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        usersRef = FirebaseDatabase.getInstance().getReference("users");

    }

    public void goToLogin(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signup(View v){
        if(!validate()) return;
        pd.show();
        waiter.setVisibility(View.VISIBLE);
        toggleTouchable();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String uid = authResult.getUser().getUid();
                        Users u = new Users(email, mobile, name, uid);
                        usersRef.push().setValue(u);
                        Toast.makeText(SignupActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        toggleTouchable();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        toggleTouchable();
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean validate() {
        EditText emailText = findViewById(R.id.email);
        EditText passText = findViewById(R.id.password);
        EditText nameText = findViewById(R.id.name);
        EditText numberText = findViewById(R.id.number);
        email = emailText.getText().toString().trim();
        password = passText.getText().toString().trim();
        mobile = numberText.getText().toString().trim();
        name = nameText.getText().toString().trim();
        if(email.length() <= 4){
            Toast.makeText(this, "Email should be least 4 characters long.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(password.length() <= 4){
            Toast.makeText(this, "Password should be least 4 characters long.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(mobile.length() < 10){
            Toast.makeText(this, "Check the mobile number.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(name.length() <= 0){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
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
