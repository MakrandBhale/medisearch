package com.makarand.medisearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makarand.medisearch.Adapter.MedAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Models.MedData;
import Models.Shop;

public class MainActivity extends AppCompatActivity {
    LinearLayout row;
    //ImageButton ham;
    EditText searchBar;
    DatabaseReference dbRef, shopRef;
    List<MedData> medDataList;
    RecyclerView recyclerView;
    MedAdapter medAdapter;
    LinearLayoutManager layoutManager;
    ImageButton searchButton;
    ProgressDialog pd;
    TextView nothingFound;
    Location userLocation, storeLocation;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //row = findViewById(R.id.row);
        userLocation = null;
        storeLocation = new Location("");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        searchBar = findViewById(R.id.search_bar);
        dbRef = FirebaseDatabase.getInstance().getReference("meddata");
        shopRef = FirebaseDatabase.getInstance().getReference("shop");
        layoutManager = new LinearLayoutManager(this);
        searchButton = findViewById(R.id.search_button);
        nothingFound = findViewById(R.id.nothing_found);
        //createChip();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchBar.getText().toString().trim();
                if(!searchText.equals(""))
                    searchForMed(searchText);
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = searchBar.getText().toString().trim();
                    searchForMed(searchText);
                    handled = true;
                }
                return handled;
            }
        });
        getLocation();
        pd = new ProgressDialog(this);
        pd.setMessage("Searching...");
        pd.setTitle("Please wait");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(true);
    }

    private void getLocation() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        userLocation = location;
                    }
                }
            });
        }
        catch (SecurityException e){
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void searchForMed(String searchText) {
        pd.show();
        nothingFound.setVisibility(View.GONE);
        Query query = dbRef.orderByChild("name").startAt(searchText).endAt(searchText+"\uf8ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    medDataList = new ArrayList<MedData>();
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        final MedData medData = dataSnapshot1.getValue(MedData.class);
                        FirebaseDatabase.getInstance().getReference("shop/"+ medData.getOwnedby())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Shop shop = dataSnapshot.getValue(Shop.class);
                                        storeLocation.setLatitude(Double.valueOf(shop.getLatitude()));
                                        storeLocation.setLongitude(Double.valueOf(shop.getLongitude()));
                                        medData.setOwnedby(shop.getId());
                                        medData.setDistance(userLocation.distanceTo(storeLocation));
                                        //medData.setShopId(shop.getId());
                                        medDataList.add(medData);
                                        Collections.sort(medDataList);
                                        medAdapter = new MedAdapter(medDataList, MainActivity.this);
                                        recyclerView.setAdapter(medAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        pd.dismiss();
                                    }
                                });

                    }

                    pd.dismiss();
                }
                else {
                    nothingFound.setVisibility(View.VISIBLE);
                    //Toast.makeText(MainActivity.this, "Your search did not turn up any results.", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(null);
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void openFeed(View view){
        //startActivity(new Intent(this, DataFeed.class));
    }
    private void createChip() {
        Button chip = new Button(getApplicationContext());
        chip.setText("Soemthign");
        chip.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.chip_background));
        chip.setTextSize(12);
        /*remove following line to ENABLE shadow. However i don't recommend it.*/
        chip.setStateListAnimator(null);
        /*setting height, here 102 is random value generated by trial and error method,
        * I hate that android does not has the literal setHeight function where you can pass dp values*/
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 108);
        param.leftMargin = 36;
        param.rightMargin = 36;
        chip.setLayoutParams(param);
        row.addView(chip);
    }
}
