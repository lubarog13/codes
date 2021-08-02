package com.elegion.myfirstapplication.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Song;

@Database(entities = {Album.class, Song.class}, version = 3)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
