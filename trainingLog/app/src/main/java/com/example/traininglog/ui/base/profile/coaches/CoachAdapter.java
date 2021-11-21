package com.example.traininglog.ui.base.profile.coaches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachAdapter extends RecyclerView.Adapter<CoachHolder> {
    private final List<Coach> mCoaches = new ArrayList<>();
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
}
