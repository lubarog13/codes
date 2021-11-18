package com.example.traininglog.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.Workout;

@Database(entities = {Building.class, User.class, Coach.class, Hall.class, Workout.class, Club.class, Presence.class}, version = 5)
public abstract class EsheduleDatabase extends RoomDatabase {
    public abstract EsheduleDao esheduleDao();
}
