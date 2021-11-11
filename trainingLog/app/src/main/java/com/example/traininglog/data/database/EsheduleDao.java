package com.example.traininglog.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.Workout;

import java.util.List;

@Dao
public interface EsheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkouts(List<Workout> workouts);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClub(Club club);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoach(Coach coach);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHall(Hall hall);


    @Query("select * from workout")
    List<Workout> selectWorkouts();


    @Query("select * from club inner join workout on club.id=workout.club_id where workout.id= :workoutId")
    Club selectClub(int workoutId);


    @Query("select * from coach inner join workout on coach.id=coach_id where workout.id=:workoutId")
    Coach selectCoach(int workoutId);

    @Query("select * from hall inner join workout on hall.id=hall_id where workout.id=:workoutId")
    Hall selectHall(int workoutId);

    @Query("select * from user where id=:userId")
    User selectUser(int userId);
}
