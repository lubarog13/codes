package com.example.traininglog.ui.base.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {

    @NonNull
    private final List<Workout> mWorkouts = new ArrayList<>();
    private final OnItemClickListener onClick;

    public WorkoutAdapter(OnItemClickListener onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_workout, parent, false);
        return new WorkoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        Workout workout = mWorkouts.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        if(position!=0 && simpleDateFormat.format(workout.getStart_time()).equals(simpleDateFormat.format(mWorkouts.get(position-1).getStart_time())))
            holder.bind(workout, false, onClick);
        else holder.bind(workout, true, onClick);
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }
    public void addData(List<Workout> data, boolean isRefreshed) {
        if(isRefreshed) mWorkouts.clear();
        mWorkouts.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onItemClick(int workout_id, boolean is_attend);
    }

}
