package com.example.traininglog.ui.base.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.ui.base.home.coach.CoachWorkoutHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {

    @NonNull
    private final List<Workout> mWorkouts = new ArrayList<>();
    private final OnItemClickListener onClick;
    private final Map<Integer, List<Presence_W_N>> mPresences = new HashMap<>();
    private int lastPosition = -1;

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
        if(mPresences.containsKey(workout.getId())) {
            if (position != 0 && simpleDateFormat.format(workout.getStart_time()).equals(simpleDateFormat.format(mWorkouts.get(position - 1).getStart_time())))
                holder.bind(workout, false, onClick, mPresences.get(workout.getId()));
            else holder.bind(workout, true, onClick, mPresences.get(workout.getId()));
        } else {
            if (position != 0 && simpleDateFormat.format(workout.getStart_time()).equals(simpleDateFormat.format(mWorkouts.get(position - 1).getStart_time())))
                holder.bind(workout, false, onClick, null);
            else holder.bind(workout, true, onClick, null);
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public void addData(List<Workout> data, boolean isRefreshed) {
        if(isRefreshed) mWorkouts.clear();
        data.sort(new Comparator<Workout>() {
            @Override
            public int compare(Workout workout, Workout t1) {
                return workout.getStart_time().compareTo(t1.getStart_time());
            }
        });
        mWorkouts.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onItemClick(int workout_id, boolean is_attend);
        void onPresencesClick(int workout_id);
        void removePresences(int workout_id);
        void onSendClick(String reason, int workout_id);
        void onHallClick(int hall_id);
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
    public void onViewDetachedFromWindow(@NonNull WorkoutHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

}
