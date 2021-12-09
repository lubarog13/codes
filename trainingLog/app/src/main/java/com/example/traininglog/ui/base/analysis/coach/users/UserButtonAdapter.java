package com.example.traininglog.ui.base.analysis.coach.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserButtonAdapter extends RecyclerView.Adapter<UsersButtonHolder> {
    private final List<Club> clubs = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public UserButtonAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UsersButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_button_layout, parent, false);
        return new UsersButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersButtonHolder holder, int position) {
        Club club = clubs.get(position);
        holder.bind(club, null, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public interface OnItemClickListener{
        void getUsersForClub(Club club);
        void selectUser(User user);
    }

    public void addData(List<Club> clubs, boolean isRefreshed){
        if(isRefreshed) this.clubs.clear();
        this.clubs.addAll(clubs);
        notifyDataSetChanged();
    }
}
