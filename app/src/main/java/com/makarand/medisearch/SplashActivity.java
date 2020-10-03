package com.makarand.medisearch;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    PermissionListener permissionListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();

        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if(auth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else{
                    goOn();
                    finish();
                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                checkPermission();
                Toast.makeText(SplashActivity.this, "Location permission is needed.", Toast.LENGTH_SHORT).show();
            }
        };
        checkPermission();
    }
    public void checkPermission(){
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }
    public void goOn(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
