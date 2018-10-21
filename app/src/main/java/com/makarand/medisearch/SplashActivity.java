package com.makarand.medisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
            startActivity(new Intent(this, MainActivity.class));

    }

    public void goOn(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
