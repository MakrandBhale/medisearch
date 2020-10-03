package com.makarand.medisearch.ViewHolder;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makarand.medisearch.MapsActivity;
import com.makarand.medisearch.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        TextView medName = mView.findViewById(R.id.prod_name);
        medName.setText(name);
    }

//    public void setMg(String mg){
//        TextView mginfo = mView.findViewById(R.id.mg);
//        mginfo.setText(mg + "mg");
//    }
    public void setPrice(String price){
        TextView priceText = mView.findViewById(R.id.prod_price);
        priceText.setText(price);
    }
    public void setDistance(Double distance){
        TextView disText = mView.findViewById(R.id.seller_distance);
        disText.setText(String.valueOf(Math.round(distance/1000)));
    }

    public void setImage(String image){
        ImageView imageView = mView.findViewById(R.id.prod_image);
        Picasso.get().load(image)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    public void setBrand(String brand){
        TextView brandname = mView.findViewById(R.id.brand_name);
        brandname.setText(brand);
    }


    public void setOffer(String offer){
        TextView offerText = mView.findViewById(R.id.offer);
        if(!offer.equals(""))
            offerText.setText(offer);
    }


//    public void setSeller(String seller){
//        TextView sellerText = mView.findViewById(R.id.seller);
//        sellerText.setText(seller);
//    }

    public void setListener(final MedData medData){
        LinearLayout layout = mView.findViewById(R.id.list_item);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("medData", medData);
                context.startActivity(i);
            }
        });
    }

//    public void addBookmark(){
//        ImageButton bookmark = mView.findViewById(R.id.bookmark);
//        bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
}
