package com.example.traininglog.ui.base.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traininglog.R;
import com.example.traininglog.data.model.Workout;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WorkoutHolder extends RecyclerView.ViewHolder {
    private TextView mType;
    private TextView mName;
    private TextView mCountOn;
    private TextView mCountDN;
    private TextView mCountNotOn;
    private TextView mWeekDay;
    private TextView mTime;

    public WorkoutHolder(View itemView) {
        super(itemView);
        mType = itemView.findViewById(R.id.workout_week_type);
        mName = itemView.findViewById(R.id.workout_week_name);
        mCountOn = itemView.findViewById(R.id.workout_week_on_train);
        mCountDN = itemView.findViewById(R.id.workout_week_dont_know);
        mCountNotOn = itemView.findViewById(R.id.workout_week_not_on_train);
        mWeekDay = itemView.findViewById(R.id.week_day);
        mTime = itemView.findViewById(R.id.train_time);
    }

    public void bind(Workout item, WorkoutAdapter.OnItemClickListener onItemClickListener, boolean newDay){
        mType.setBackgroundColor(bindColor(item.getType()));
        if(item.getClub().getName().length()>15) {
            mName.setText(String.format("%s...", item.getClub().getName().substring(0, 15)));
        } else {
            mName.setText(item.getClub().getName());
        }
        mCountNotOn.setText(String.format("-%s", item.getNot_on_train()));
        mCountOn.setText(String.format("+%s", item.getOn_train()));
        mCountDN.setText(item.getDont_know());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        mTime.setText(simpleDateFormat.format(item.getStart_time()));
        if(newDay) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM EEEE", Locale.getDefault());
            mWeekDay.setText(simpleDateFormat.format(item.getStart_time()));
            mWeekDay.setVisibility(View.VISIBLE);
        }
    }

    public int bindColor(String type) {
        switch (type){
            case "общая": return R.color.colorRed;
            case "кардио": return R.color.colorOrange;
            case "другое": return R.color.colorYellow;
            case "силовая": return R.color.colorGreen;
            case "на технику": return R.color.colorPrimaryDark;
        }
        return R.color.colorRed;
    }
}
