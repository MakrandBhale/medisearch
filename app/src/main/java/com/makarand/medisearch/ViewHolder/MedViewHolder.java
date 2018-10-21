package com.makarand.medisearch.ViewHolder;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.makarand.medisearch.MapsActivity;
import com.makarand.medisearch.R;

import Models.MedData;

public class MedViewHolder extends RecyclerView.ViewHolder{
    private View mView;
    private Context context;

    public MedViewHolder(View v, Context context){
        super(v);
        mView = v;
        this.context = context;
    }

    public void setName(String name){
        TextView medName = mView.findViewById(R.id.med_name);
        medName.setText(name);
    }

    public void setMg(String mg){
        TextView mginfo = mView.findViewById(R.id.mg);
        mginfo.setText(mg + "mg");
    }
    public void setPrice(String price){
        TextView priceText = mView.findViewById(R.id.price);
        priceText.setText("₹" + price);
    }
    public void setDistance(String distance){
        TextView disText = mView.findViewById(R.id.distance);
        disText.setText(distance + " km");
    }
    public void setSeller(String seller){
        TextView sellerText = mView.findViewById(R.id.seller);
        sellerText.setText(seller);
    }

    public void setListener(final MedData medData){
        ConstraintLayout layout = mView.findViewById(R.id.listItem);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("medData", medData);
                context.startActivity(i);
            }
        });
    }

    public void addBookmark(){
        ImageButton bookmark = mView.findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
