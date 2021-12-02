package com.example.traininglog.ui.base.home.coach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoachWorkoutAdapter extends RecyclerView.Adapter<CoachWorkoutHolder> {
    private final List<Workout> mWorkouts = new ArrayList<>();
    private final List<Club> mClubs = new ArrayList<>();
    private final List<Coach> mCoaches = new ArrayList<>();
    private final List<Hall> mHalls = new ArrayList<>();
    @NonNull
    @Override
    public CoachWorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.coach_home_workout, parent, false);
        return new CoachWorkoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachWorkoutHolder holder, int position) {
        Workout workout = mWorkouts.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        holder.bind(workout, position == 0 || !simpleDateFormat.format(workout.getStart_time())
                .equals(simpleDateFormat.format(mWorkouts.get(position - 1).getStart_time())),
                mCoaches, mHalls, mClubs);
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public void addValues(List<Coach> coaches, List<Hall> halls, List<Club> clubs) {
        mClubs.clear();
        mCoaches.clear();
        mHalls.clear();
        mClubs.addAll(clubs);
        mHalls.addAll(halls);
        mCoaches.addAll(coaches);
    }

    public void addData(List<Workout> workouts, boolean isRefreshed) {
        if(isRefreshed) mWorkouts.clear();
        mWorkouts.addAll(workouts);
        notifyDataSetChanged();
    }
}
