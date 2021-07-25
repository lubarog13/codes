package elegion.com.roomdatabase.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

/**
 * @author Azret Magometov
 */

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSongs(List<AlbumSong> AlbumSongs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setLinksAlbumSongs(List<AlbumSong> linksAlbumSongs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSong(AlbumSong albumSong);

    @Query("select * from album")
    List<Album> getAlbums();

    @Query("select * from album")
    Cursor getAlbumsCursor();

    @Query("select * from song")
    List<Song> getSongs();

    @Query("select * from album where id = :albumId")
    Cursor getAlbumWithIdCursor(int albumId);

    @Query("select * from song where id = :songId")
    Song getSong(int songId);

    @Query("select * from song where id = :songId")
    Cursor getSongWithIdCursor(int songId);

    @Query("select * from album where id = :albumId")
    Album getAlbum(int albumId);

    @Query("select * from albumsong where id = :albumSongId")
    Cursor getAlbumSongWithIdCursor(int albumSongId);

    @Query("select * from albumsong where id = :albumSongId")
    AlbumSong getAlbumSong(int albumSongId);

    @Query("select * from song")
    Cursor getSongCursor();

    @Query("select * from AlbumSong")
    List<AlbumSong> getAlbumSongs();

    @Query("select * from AlbumSong")
    Cursor getAlbumSongsCursor();

    @Delete
    void deleteSong(Song song);

    @Delete
    void deleteAlbumSong(AlbumSong albumSong);

    @Delete
    void deleteAlbum(Album album);

    @Query("DELETE from album")
    void deleteAlbums();

    @Query("DELETE from albumsong")
    void deleteAlbumSongs();

    @Query("DELETE from song")
    void deleteSongs();

    //получить список песен переданного id альбома
    @Query("select * from song inner join albumsong on song.id = albumsong.song_id where album_id = :albumId")
    List<Song> getSongsFromAlbum(int albumId);
    @Update
    int updateSongInfo(Song song);

    @Update
    int updateAlbumSongInfo(AlbumSong albumSong);
    //обновить информацию об альбоме
    @Update
    int updateAlbumInfo(Album album);
    //удалить альбом по id
    @Query("DELETE FROM album where id = :albumId")
    int deleteAlbumById(int albumId);

    @Query("DELETE FROM song where id = :songId")
    int deleteSongById(int songId);

    @Query("DELETE FROM albumsong where id = :albumSongId")
    int deleteAlbumSongById(int albumSongId);

}
