package com.example.traininglog.ui.base.home.coach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.ui.base.home.WorkoutAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CoachWorkoutAdapter extends RecyclerView.Adapter<CoachWorkoutHolder> {
    private final List<Workout> mWorkouts = new ArrayList<>();
    private final List<Club> mClubs = new ArrayList<>();
    private final List<Coach> mCoaches = new ArrayList<>();
    private final List<Hall> mHalls = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;
    private final WorkoutAdapter.OnItemClickListener onClick;
    private int lastPosition = -1;
    private final Map<Integer, List<Presence_W_N>> mPresences = new HashMap<>();

    public CoachWorkoutAdapter(OnItemClickListener onItemClickListener, WorkoutAdapter.OnItemClickListener onClick) {
        this.onItemClickListener = onItemClickListener;
        this.onClick = onClick;
    }

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
                mCoaches, mHalls, mClubs, onItemClickListener, onClick, mPresences.getOrDefault(workout.getId(), null));
        setAnimation(holder.itemView, position);
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

    public interface OnItemClickListener {
        void editWorkout(WorkoutForEdit workout);
    }

    public void addPresences(List<Presence_W_N> presences) {
        if(presences!=null)  {
            mPresences.put(presences.get(0).getWorkout(), presences);
            notifyDataSetChanged();
        }
    }

    public void removePresence(int workout_id) {
        mPresences.remove(workout_id);
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CoachWorkoutHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }
}
