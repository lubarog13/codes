package com.example.traininglog.data;

import android.util.Log;

import androidx.room.Database;

import com.example.traininglog.data.database.EsheduleDao;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutResponse;
import com.example.traininglog.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class Storage {
    private EsheduleDao mDao;

    public Storage(EsheduleDao mDao) {
        this.mDao = mDao;
    }

    public void insertWorkouts(WorkoutResponse response) {
        Log.e("storage", "hi");
        List<Workout> workouts = response.getWorkouts();
        for(Workout workout: workouts){
            workout.setStartTime(DateUtils.ToTimestamp(workout.getStart_time()));
            workout.setEndTime(DateUtils.ToTimestamp(workout.getEnd_time()));
            workout.setClub_id(workout.getClub().getId());
            Coach coach;
            if(workout.getCoach()!=null) {
                workout.setCoach_id(workout.getCoach().getId());
                 coach = workout.getCoach();
            }
            else{
                workout.setCoach_id(workout.getClub().getCoach().getId());
                 coach = workout.getClub().getCoach();
            }
            workout.setHall_id(workout.getHall().getId());
            Club club = workout.getClub();
            coach.setUserId(coach.getUser().getId());
            Hall hall = workout.getHall();
            mDao.insertClub(club);
            mDao.insertHall(hall);
            mDao.insertUser(coach.getUser());
            mDao.insertCoach(coach);
        }
        mDao.insertWorkouts(workouts);
        Log.e("storage", this.getWeekWorkout().getWorkouts().toString());
    }

    public WorkoutResponse getWeekWorkout() {
        List<Workout> workouts = mDao.selectWorkouts();
        workouts.removeIf(workout -> DateUtils.FromTimestamp(workout.getStartTime())
                .compareTo(DateUtils.lastMonday(new Date()))<0 ||
                DateUtils.FromTimestamp(workout.getStartTime())
                        .compareTo(DateUtils.nextMonday(new Date()))>0);
        for(Workout workout: workouts){
            workout.setStart_time(DateUtils.FromTimestamp(workout.getStartTime()));
            workout.setEnd_time(DateUtils.FromTimestamp(workout.getEndTime()));
            workout.setHall(mDao.selectHall(workout.getId()));
            Coach coach = mDao.selectCoach(workout.getId());
            coach.setUser(mDao.selectUser(coach.getUserId()));
            workout.setCoach(coach);
            workout.setClub(mDao.selectClub(workout.getId()));
        }
        return new WorkoutResponse(workouts);
    }
    public interface StorageOwner {
        Storage obtainStorage();
    }
}
