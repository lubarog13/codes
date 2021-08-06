package com.elegion.myfirstapplication.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Comment;
import com.elegion.myfirstapplication.model.Song;

@Database(entities = {Album.class, Song.class, Comment.class}, version = 5)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
