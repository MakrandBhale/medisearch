package com.makarand.medisearch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makarand.medisearch.R;
import com.makarand.medisearch.ViewHolder.MedViewHolder;

import java.util.List;

import Models.MedData;

public class MedAdapter extends RecyclerView.Adapter<MedViewHolder> {
    private List<MedData> medDataList;
    private Context context;

    public MedAdapter(List<MedData> medDataList, Context context) {
        this.medDataList = medDataList;
        this.context = context;
    }

    @Override
    public MedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.med_search_item, viewGroup, false);
        MedViewHolder medViewHolder = new MedViewHolder(view, context);
        return medViewHolder;
    }

    @Override
    public void onBindViewHolder(MedViewHolder viewHolder, int position){
        MedData medData = medDataList.get(position);
        viewHolder.setName(medData.getName());
        viewHolder.setDistance("00");
        viewHolder.setPrice(medData.getPrice());
        viewHolder.setSeller(medData.getOwnedby());
        viewHolder.setMg(medData.getMg());
        viewHolder.setListener(medData);
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(medDataList.size()==0){
                arr = 0;
            }
            else{
                arr=medDataList.size();
            }
        }catch (Exception e){
        }
        return arr;
    }
}
