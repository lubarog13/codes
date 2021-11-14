package com.example.traininglog.ui.base.profile.clubs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.ui.base.home.WorkoutAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubAdapter extends RecyclerView.Adapter<ClubHolder> {
    private final Map<Integer, List<User>> users = new HashMap<>();
    private final ClubAdapter.onItemClickListener onClick;
    @NonNull
    private final List<SignUp> mSignups = new ArrayList<>();

    public ClubAdapter(onItemClickListener onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ClubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.club_item_layout, parent, false);
        return new ClubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubHolder holder, int position) {
        SignUp signUp = mSignups.get(position);
        if(users.containsKey(signUp.getId())){
            holder.bind(signUp, users.get(signUp.getId()), onClick);
        }
        else holder.bind(signUp, null, onClick);
    }

    @Override
    public int getItemCount() {
        return mSignups.size();
    }

    public void addData(List<SignUp> data, boolean isRefreshed) {
        if(isRefreshed) mSignups.clear();
        mSignups.addAll(data);
        notifyDataSetChanged();
    }
    public interface onItemClickListener{
         void onUsersClick(int club_id, int signup_id);
         void hideUser(int signup_id);
    }

    public void addUsers(int signup_id, List<User> user){
        if(user!=null){
        this.users.put(signup_id, user);
        notifyDataSetChanged();
        }
    }

    public void removeUsers(int signup_id){
        this.users.remove(signup_id);
        notifyDataSetChanged();
    }
}
