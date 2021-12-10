package com.example.traininglog.ui.base.analysis.coach.users;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;

public class UsersButtonHolder extends RecyclerView.ViewHolder {
    private Button mButton;
    public UsersButtonHolder(@NonNull View itemView) {
        super(itemView);
        mButton = itemView.findViewById(R.id.item_button);
    }

    public void bind(Club club, User user, UserButtonAdapter.OnItemClickListener onItemClickListener) {
        String name = "";
        if(club!=null){
            mButton.setBackground(itemView.getResources().getDrawable(R.drawable.iten_button_background_blue));
            mButton.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
            name = club.getGroup();
            if(onItemClickListener!=null){
                mButton.setOnClickListener(v -> onItemClickListener.getUsersForClub(club));
            }
        }
        else if(user!=null){
            mButton.setBackground(itemView.getResources().getDrawable(R.drawable.item_button_background));
            name = String.format("%s %s. %s.", user.getLast_name(), user.getFirst_name().charAt(0), user.getSecond_name().charAt(0));
            if(onItemClickListener!=null){
                mButton.setOnClickListener(v -> onItemClickListener.selectUser(user));
            }
        }
        mButton.setText(name);
    }
}
