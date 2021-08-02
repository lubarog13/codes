package com.elegion.myfirstapplication.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Song;

import java.util.List;

/**
 * @author Azret Magometov
 */

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Query("SELECT * from album")
    List<Album> getAlbums();


    @Query("SELECT * from album where id = :albumId")
    Album getAlbum(int albumId);

    @Query("Select * from song where albumId = :albumId")
    List<Song> getSongFromAlbum(int albumId);
    //удалить альбом
    @Delete
    void deleteAlbum(Album album);

    //удалить альбом по id
    @Query("DELETE FROM album where id = :albumId")
    void deleteAlbumById(int albumId);

    @Query("DELETE fROM album")
    void deleteAllAlbums ();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("Select * from song")
    List<Song> getSongs();

    @Delete
    void deleteSong(Song song);

    @Query("DELETE FROM song where id = :songId")
    void deleteSongById(int songId);

}
