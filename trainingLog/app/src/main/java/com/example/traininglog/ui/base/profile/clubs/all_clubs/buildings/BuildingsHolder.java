package com.example.traininglog.ui.base.profile.clubs.all_clubs.buildings;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.ui.base.profile.clubs.all_clubs.coaches.CoachesAdapter;

public class BuildingsHolder extends RecyclerView.ViewHolder {
    private Button mButton;
    private Resources mResources;
    public BuildingsHolder(@NonNull View itemView) {
        super(itemView);
        mButton = itemView.findViewById(R.id.item_button);
        mResources = itemView.getResources();
    }

    public void bind(Building item, BuildingAdapter.OnItemClickListener onItemClickListener) {
        String[] buildingNames = mResources.getStringArray(R.array.building_names);
        String[] addresses = mResources.getStringArray(R.array.addresses);
        String address = item.getAddress() + ", " + item.getNumber();
        String name ="";
        for(int i=0; i<addresses.length; i++){
            if(addresses[i].equals(address)){
                name = buildingNames[i];
            }
            else if((address + item.getLiter()).equals(addresses[i])){
               name = buildingNames[i];
            }
        }
        mButton.setText(name);
        String finalName = name;
        mButton.setOnClickListener(view -> onItemClickListener.onBuildingClick(item, finalName));
    }
}
