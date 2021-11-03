package com.example.traininglog.ui.base.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {
    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public interface OnItemClickListener {

        void onItemClick();
    }
}
