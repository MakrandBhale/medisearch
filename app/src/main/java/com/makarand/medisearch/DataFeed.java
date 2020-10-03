package com.makarand.medisearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.MedData;

public class DataFeed extends AppCompatActivity {
    DatabaseReference dbRef;
    EditText brand, expdate,manudate,medtype,mg,name,ownedby,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_feed);
        dbRef = FirebaseDatabase.getInstance().getReference("meddata");
        brand = findViewById(R.id.brand);
        expdate = findViewById(R.id.expdate);
        manudate = findViewById(R.id.manudate);
        medtype = findViewById(R.id.medtype);
        mg = findViewById(R.id.mg);
        name = findViewById(R.id.name);
        ownedby = findViewById(R.id.ownedby);
        price = findViewById(R.id.price);
    }
    public void feed(View v){
//        MedData m = new MedData(brand.getText().toString(),
//                expdate.getText().toString(),
//                manudate.getText().toString(),
//                medtype.getText().toString(),
//                mg.getText().toString(),
//                name.getText().toString(),
//                ownedby.getText().toString(),
//                price.getText().toString());
//        dbRef.push().setValue(m);
    }
}

