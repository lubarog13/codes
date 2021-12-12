package com.example.traininglog.ui.base.profile.clubs.coach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubAdapter extends RecyclerView.Adapter<ClubHolder> {
    private final Map<Integer, List<User>> users = new HashMap<>();
    private final ClubAdapter.onItemClickListener onClick;
    @NonNull
    private final List<Club> mClubs = new ArrayList<>();

    public ClubAdapter(ClubAdapter.onItemClickListener onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ClubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.club_coach_item_layout, parent, false);
        return new ClubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubHolder holder, int position) {
        Club club = mClubs.get(position);
        holder.bind(club, users.getOrDefault(club.getId(), null), onClick);
    }

    @Override
    public int getItemCount() {
        return mClubs.size();
    }

    public void addData(List<Club> data, boolean isRefreshed) {
        if(isRefreshed) mClubs.clear();
        mClubs.addAll(data);
        notifyDataSetChanged();
    }
    public interface onItemClickListener{
        void onUsersClick(int club_id);
        void hideUser(int club_id);
        void addWorkout(int club_id);
        void addSignup(int club_id);

    }

    public void addUsers(int club_id, List<User> user){
        if(user!=null){
            this.users.put(club_id, user);
            notifyDataSetChanged();
        }
    }

    public void updateData(Club club) {
        for (int i = 0; i< mClubs.size(); i++) {
            if(club.getId()== mClubs.get(i).getId()){
                mClubs.set(i, club);
                notifyDataSetChanged();
                return;
            }
        }
        mClubs.add(club);
        notifyDataSetChanged();
    }

    public void removeUsers(int signup_id){
        this.users.remove(signup_id);
        notifyDataSetChanged();
    }
}
