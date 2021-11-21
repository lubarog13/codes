package com.example.traininglog.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.Workout;

import java.util.List;

@Dao
public interface EsheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkouts(List<Workout> workouts);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPresences(List<Presence> presences);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPresence(Presence presence);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoaches(List<Coach> coaches);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClub(Club club);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkout(Workout workout);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoach(Coach coach);

    @Query("delete from presence")
    void deletePresences();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHall(Hall hall);


    @Query("select * from workout")
    List<Workout> selectWorkouts();


    @Query("select * from club where id=:club_id")
    Club selectClub(int club_id);


    @Query("select * from coach where id=:coach_id")
    Coach selectCoach(int coach_id);

    @Query("select * from hall where id=:hall_id")
    Hall selectHall(int hall_id);

    @Query("select * from user where id=:userId")
    User selectUser(int userId);

    @Query("select * from workout inner join presence on workout_id=workout.id where presence.id=:presence_id")
    Workout selectWorkout(int presence_id);

    @Query("select * from presence")
    List<Presence> selectPresences();

    @Query("select * from coach")
    List<Coach> selectCoaches();


}
