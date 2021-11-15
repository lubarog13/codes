package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachesAdapter extends RecyclerView.Adapter<CoachesHolder> {
    private final List<Coach> mCoaches = new ArrayList<>();
    private final CoachesAdapter.OnItemClickListener onItemClickListener;

    public CoachesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_button_layout, parent, false);
        return new CoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachesHolder holder, int position) {
        Coach coach = mCoaches.get(position);
        holder.bind(coach, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCoaches.size();
    }

    public void addData(List<Coach> coaches, boolean isRefresh){
        if(isRefresh) mCoaches.clear();
        mCoaches.addAll(coaches);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onCoachClick(Coach coach);
    }
}
