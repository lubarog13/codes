package com.example.traininglog.ui.base.profile.clubs.all_clubs.buildings;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Building;

import java.util.ArrayList;
import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingsHolder> {
    private final List<Building> mBuildings = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public BuildingAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BuildingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_button_layout, parent, false);
        return new BuildingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingsHolder holder, int position) {
        Building building = mBuildings.get(position);
        holder.bind(building, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mBuildings.size();
    }


    public void addData(List<Building> buildings, boolean isRefreshed){
        if(isRefreshed) mBuildings.clear();
        mBuildings.addAll(buildings);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onBuildingClick(Building building, String name);
    }
}
