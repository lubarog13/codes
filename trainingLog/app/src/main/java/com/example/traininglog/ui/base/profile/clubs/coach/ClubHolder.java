package com.example.traininglog.ui.base.profile.clubs.coach;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;
import com.example.traininglog.utils.ParseStringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClubHolder extends RecyclerView.ViewHolder {

    private TextView mClubName;
    private TextView mGroupName;
    private TextView mCoachName;
    private TextView mAddress;
    private TextView mIdentifier;
    private Button mSignUps;
    private Button mCreateWorkout;
    private Button mCreateSignup;
    private View mNames;
    private TextView mNames1;
    private TextView mNames2;
    private boolean is_opened=false;
    public ClubHolder(@NonNull View itemView) {
        super(itemView);
        mClubName = itemView.findViewById(R.id.club_name);
        mGroupName = itemView.findViewById(R.id.club_group);
        mCoachName = itemView.findViewById(R.id.club_coach_name);
        mAddress = itemView.findViewById(R.id.club_building_address);
        mIdentifier = itemView.findViewById(R.id.identifier);
        mSignUps = itemView.findViewById(R.id.signups);
        mCreateWorkout = itemView.findViewById(R.id.create_workout);
        mCreateSignup = itemView.findViewById(R.id.create_signup);
        mNames = itemView.findViewById(R.id.names_in_club);
        mNames1 = itemView.findViewById(R.id.usernames_1);
        mNames2 = itemView.findViewById(R.id.usernames_2);
    }

    public void bind(Club item, List<User> users, ClubAdapter.onItemClickListener onItemClickListener) {
        mClubName.setText(item.getName());
        Log.d("club", item.toString());
        mGroupName.setText(item.getGroup());
        mCoachName.setText(String.format("%s %s.%s.", item.getCoach().getUser().getLast_name(), item.getCoach().getUser().getFirst_name().charAt(0), item.getCoach().getUser().getSecond_name().substring(0, 1)));
        mAddress.setText(ParseStringUtils.buildingSmallAddress(item.getBuilding()));
        mIdentifier.setText(item.getIdentifier());
        if(users!=null) {
            Log.e("users", users.toString());
            mNames.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            for(User user: users.subList(0, users.size()/2)){
                sb.append(user.getLast_name()).append(" ").append(user.getFirst_name().charAt(0)).append(".\n");
            }
            mNames2.setText(sb.toString());
            Log.e("sb", sb.toString());
            sb.delete(0, sb.length());
            for(User user: users.subList(users.size()/2, users.size())){
                sb.append(user.getLast_name()).append(" ").append(user.getFirst_name().charAt(0)).append(".\n");
            }
            mNames1.setText(sb.toString());
            Log.e("sb", sb.toString());
            is_opened=true;
        }
        if(onItemClickListener!=null) {
            mSignUps.setOnClickListener(view -> OpenUsers(onItemClickListener, item.getId()));
            mCreateWorkout.setOnClickListener(v -> onItemClickListener.addWorkout(item.getId()));
            mCreateSignup.setOnClickListener(v -> onItemClickListener.addSignup(item.getId()));
        }

    }

    private void OpenUsers(ClubAdapter.onItemClickListener onItemClickListener, int club_id) {
        if(is_opened) {
            mNames.setVisibility(View.GONE);
            onItemClickListener.hideUser(club_id);
            mSignUps.setText("Посмотреть занимающихся");
        } else {
            onItemClickListener.onUsersClick(club_id);
            mSignUps.setText("Скрыть занимаюихся");
        }
        is_opened = !is_opened;
    }

}


