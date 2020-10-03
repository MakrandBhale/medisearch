package com.makarand.medisearch;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.MedData;
import Models.Shop;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MedData medData;
    TextView name, seller;
    DatabaseReference dbRef, prodRef;
    Shop shop;
    Double [] places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        medData = (MedData) getIntent().getSerializableExtra("medData");
        dbRef = FirebaseDatabase.getInstance().getReference("shop/"+medData.getOwnedby());
        prodRef = FirebaseDatabase.getInstance().getReference("proddata");

        name = findViewById(R.id.name);

        seller = findViewById(R.id.sold_by);

        //type.setText(medData.getMedtype());
        name.setText(medData.getName());
       // mg.setText(medData.getMg());
        //manuDate.setText(medData.getManudate());
        //expDate.setText(medData.getExpdate());
        seller.setText("waiting ...");


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    shop = dataSnapshot.getValue(Shop.class);
                    seller.setText(shop.getShopname());
                    LatLng latLng = new LatLng(Double.valueOf(shop.getLatitude()), Double.valueOf(shop.getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(shop.getShopname()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
                    try {
                        mMap.setMyLocationEnabled(true);
                    }
                    catch (SecurityException e){
                        Log.e("MapError", e.getMessage());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchMed(String searchText){

        Query query = prodRef.orderByChild("title").startAt(searchText).endAt(searchText+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final MedData medData = dataSnapshot1.getValue(MedData.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
