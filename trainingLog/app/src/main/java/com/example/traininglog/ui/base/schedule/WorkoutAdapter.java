package com.example.traininglog.ui.base.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {
    @NonNull
    private final List<Workout> mWorkouts = new ArrayList<>();
    private final OnItemClickListener onClick;
    private final Map<Integer, List<Presence_W_N>> mPresences = new HashMap<>();

    public WorkoutAdapter(WorkoutAdapter.OnItemClickListener onClick) {
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
        if(mPresences.containsKey(workout.getId())) {
            holder.bind(workout, onClick, mPresences.get(workout.getId()));
        } else {
             holder.bind(workout, onClick, null);
        }
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public void addData(List<Presence> data, boolean isRefreshed) {
        if(isRefreshed) mWorkouts.clear();
        for(Presence presence : data) {
            Workout workout = presence.getWorkout();
            workout.setIs_on(presence.isIs_attend());
            mWorkouts.add(workout);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onItemClick(int workout_id, boolean is_attend);
        void onPresencesClick(int workout_id);
        void removePresences(int workout_id);
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
}
