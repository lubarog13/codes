package com.example.traininglog.ui.base.profile.clubs.all_clubs;



import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Coach;

public class CoachesHolder extends RecyclerView.ViewHolder {
    private Button mButton;
    public CoachesHolder(@NonNull View itemView) {
        super(itemView);
        mButton = itemView.findViewById(R.id.item_button);
    }

    public void bind(Coach item, CoachesAdapter.OnItemClickListener onItemClickListener) {
        mButton.setText(String.format("%s %s. %s.", item.getUser().getLast_name(), item.getUser().getFirst_name().charAt(0), item.getUser().getSecond_name().charAt(0)));
        mButton.setOnClickListener(view -> onItemClickListener.onCoachClick(item));
    }
}
