package com.example.traininglog.ui.base.profile.coaches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachAdapter extends RecyclerView.Adapter<CoachHolder> {
    private final List<Coach> mCoaches = new ArrayList<>();
    private int lastPosition = -1;
    @NonNull
    @Override
    public CoachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.coach_item, parent, false);
        return new CoachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachHolder holder, int position) {
        Coach coach = mCoaches.get(position);
        holder.bind(coach);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mCoaches.size();
    }

    public void addData(List<Coach> coaches, boolean isRefreshed) {
        if(isRefreshed) mCoaches.clear();
        mCoaches.addAll(coaches);
        notifyDataSetChanged();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.coach_animate);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CoachHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
