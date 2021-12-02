package com.example.traininglog.ui.base.profile.clubs;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClubHolder extends RecyclerView.ViewHolder {

    private TextView mClubName;
    private TextView mGroupName;
    private TextView mCoachName;
    private TextView mAddress;
    private TextView mEndDate;
    private Button mSignUps;
    private Button mDelete;
    private View mAcceptView;
    private View mNames;
    private TextView mNames1;
    private TextView mNames2;
    private Button mAcceptButton;
    private Button mDenyButton;
    private boolean isAcceptOpen = false;
    private boolean is_opened=false;
    public ClubHolder(@NonNull View itemView) {
        super(itemView);
        mClubName = itemView.findViewById(R.id.club_name);
        mGroupName = itemView.findViewById(R.id.club_group);
        mCoachName = itemView.findViewById(R.id.club_coach_name);
        mAddress = itemView.findViewById(R.id.club_building_address);
        mEndDate = itemView.findViewById(R.id.signup_end);
        mSignUps = itemView.findViewById(R.id.signups);
        mDelete = itemView.findViewById(R.id.delete_sinup);
        mNames = itemView.findViewById(R.id.names_in_club);
        mNames1 = itemView.findViewById(R.id.usernames_1);
        mNames2 = itemView.findViewById(R.id.usernames_2);
        mAcceptView = itemView.findViewById(R.id.accept_delete);
        mAcceptButton = itemView.findViewById(R.id.yes_button);
        mDenyButton = itemView.findViewById(R.id.no_button);
    }

    public void bind(SignUp item, List<User> users, ClubAdapter.onItemClickListener onItemClickListener) {
        mClubName.setText(item.getClub().getName());
        mGroupName.setText(item.getClub().getGroup());
        mCoachName.setText(item.getClub().getCoach().getUser().getLast_name() + " "
                + item.getClub().getCoach().getUser().getFirst_name().charAt(0) + "."
                + item.getClub().getCoach().getUser().getSecond_name().substring(0,1) + ".");
        mAddress.setText(item.getClub().getBuilding().getAddress() + ", "  + item.getClub().getBuilding().getNumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        mEndDate.setText(simpleDateFormat.format(item.getEnd_date()));
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
            mSignUps.setOnClickListener(view -> OpenUsers(onItemClickListener, item.getClub().getId(), item.getId()));
            mAcceptButton.setOnClickListener(v -> {
                acceptDelete();
                onItemClickListener.deleteSignUp(item.getId());
            });
            mDenyButton.setOnClickListener(v -> acceptDelete());
            mDelete.setOnClickListener(v -> acceptDelete());
        }

    }

    private void OpenUsers(ClubAdapter.onItemClickListener onItemClickListener, int club_id, int signup_id) {
        if(is_opened) {
            mNames.setVisibility(View.GONE);
            onItemClickListener.hideUser(signup_id);
            mSignUps.setText("Посмотреть занимающихся");
        } else {
            onItemClickListener.onUsersClick(club_id, signup_id);
            mSignUps.setText("Скрыть занимаюихся");
        }
        is_opened = !is_opened;
    }

    private void acceptDelete() {
        if(isAcceptOpen) {
            mAcceptView.setVisibility(View.GONE);
        } else {
            mAcceptView.setVisibility(View.VISIBLE);
        }
        isAcceptOpen = !isAcceptOpen;
    }
}


