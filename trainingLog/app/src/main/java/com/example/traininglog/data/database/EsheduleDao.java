package com.example.traininglog.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.traininglog.data.model.Building;
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
    void insertClub(Club club);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBuildings(List<Building> building);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBuilding(Building building);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoach(Coach coach);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHalls(List<Hall> halls);

    @Query("delete from presence")
    void deletePresences();

    @Query("delete from user")
    void deleteUsers();

    @Query("delete from building")
    void deleteBuildings();

    @Query("delete from hall")
    void deleteHalls();

    @Query("delete from workout")
    void deleteWorkouts();

    @Query("delete from coach")
    void deleteCoaches();

    @Query("delete from club")
    void deleteClubs();

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

    @Query("select * from building")
    List<Building> selectBuildings();

    @Query("select * from building where id=:building_id")
    Building selectBuilding(int building_id);

    @Query("select * from hall where building_id=:building_id")
    List<Hall> selectHalls(int building_id);

}
