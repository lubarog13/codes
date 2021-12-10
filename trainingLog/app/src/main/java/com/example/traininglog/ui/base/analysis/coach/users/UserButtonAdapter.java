package com.example.traininglog.ui.base.analysis.coach.users;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.base.profile.coaches.CoachHolder;

import java.util.ArrayList;
import java.util.List;

public class UserButtonAdapter extends RecyclerView.Adapter<UsersButtonHolder> {
    private final List<Club> clubs = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;
    private final List<UsersButtonHolder> holders = new ArrayList<>();
    private int lastPosition = -1;

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
        holders.add(holder);
        setAnimation(holders.get(position).itemView, position);
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

    private void setAnimation(View viewToAnimate, int position)
    {
        Log.e("animate", "animate");
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.coach_animate);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void startAnimation() {
        lastPosition=-1;
        Log.e("animate", "animate");
        for(int i=0; i<holders.size(); i++) {
            setAnimation(holders.get(i).itemView, i);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull UsersButtonHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
