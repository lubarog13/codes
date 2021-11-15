package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;

public class AllClubsHolder extends RecyclerView.ViewHolder {
    private TextView mClubName;
    private TextView mGroupName;
    private TextView mCoachName;
    private TextView mAddress;
    private Button mCreate;
    public AllClubsHolder(@NonNull View itemView) {
        super(itemView);
        mClubName = itemView.findViewById(R.id.club_all_name);
        mGroupName = itemView.findViewById(R.id.club_all_group);
        mCoachName = itemView.findViewById(R.id.club_all_coach_name);
        mAddress = itemView.findViewById(R.id.club_all_building_address);
        mCreate = itemView.findViewById(R.id.create_signup);
    }

    public void bind(Club club) {
        mClubName.setText(club.getName());
        mGroupName.setText(club.getGroup());
        mCoachName.setText(club.getCoach().getUser().getLast_name() + " "
                + club.getCoach().getUser().getFirst_name().charAt(0) + "."
                + club.getCoach().getUser().getSecond_name().substring(0,1) + ".");
        mAddress.setText(club.getBuilding().getAddress() + ", " + club.getBuilding().getNumber());
    }
}
