package elegion.com.roomdatabase;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import elegion.com.roomdatabase.database.Album;
import elegion.com.roomdatabase.database.AlbumSong;
import elegion.com.roomdatabase.database.MusicDao;
import elegion.com.roomdatabase.database.MusicDatabase;
import elegion.com.roomdatabase.database.Song;

public class MusicProvider extends ContentProvider {

    private static final String TAG = MusicProvider.class.getSimpleName();

    private static final String AUTHORITY = "com.elegion.roomdatabase.musicprovider";
    private static final String TABLE_ALBUM = "album";
    private static final String TABLE_SONG = "song";
    private static final String TABLE_ALBUM_SONG = "albumsong";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ALBUM_TABLE_CODE = 100;
    private static final int ALBUM_ROW_CODE = 101;
    private static final int SONG_TABLE_CODE = 102;
    private static final int SONG_ROW_CODE = 103;
    private static final int ALBUM_SONG_TABLE_CODE = 104;
    private static final int ALBUM_SONG_ROW_CODE = 105;
    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM, ALBUM_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM + "/*", ALBUM_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG, SONG_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG + "/*", SONG_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM_SONG, ALBUM_SONG_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM_SONG + "/*", ALBUM_SONG_ROW_CODE);
    }

    private MusicDao mMusicDao;

    public MusicProvider() {
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            mMusicDao = Room.databaseBuilder(getContext().getApplicationContext(), MusicDatabase.class, "music_database")
                    .build()
                    .getMusicDao();
            return true;
        }

        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case ALBUM_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_ALBUM;
            case ALBUM_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUM;
            case SONG_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_SONG;
            case SONG_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_SONG;
            case ALBUM_SONG_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_ALBUM_SONG;
            case ALBUM_SONG_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUM_SONG;
            default:
                throw new UnsupportedOperationException("not yet implemented");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int code = URI_MATCHER.match(uri);

        if (code != ALBUM_ROW_CODE && code != ALBUM_TABLE_CODE && code!=ALBUM_SONG_TABLE_CODE
        && code!=ALBUM_SONG_ROW_CODE && code!=SONG_TABLE_CODE && code!=SONG_ROW_CODE) return null;

        Cursor cursor;

        if (code == ALBUM_TABLE_CODE) {
            cursor = mMusicDao.getAlbumsCursor();
        } else if(code==ALBUM_ROW_CODE){
            cursor = mMusicDao.getAlbumWithIdCursor((int) ContentUris.parseId(uri));
        } else if(code == SONG_TABLE_CODE) {
            cursor = mMusicDao.getSongCursor();
        } else if(code==SONG_ROW_CODE) {
            cursor = mMusicDao.getSongWithIdCursor((int) ContentUris.parseId(uri));
        } else if(code==ALBUM_SONG_TABLE_CODE) {
            cursor = mMusicDao.getAlbumSongsCursor();
        }
        else {
            cursor = mMusicDao.getAlbumSongWithIdCursor((int) ContentUris.parseId(uri));
        }
        Log.d("taked", uri.toString());
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == ALBUM_TABLE_CODE && isValuesValidForAlbum(values)) {
            Album album = new Album();
            Integer id = values.getAsInteger("id");
            album.setId(id);
            album.setName(values.getAsString("name"));
            album.setReleaseDate(values.getAsString("release"));
            mMusicDao.insertAlbum(album);
            return ContentUris.withAppendedId(uri, id);
        } else if (URI_MATCHER.match(uri) == SONG_TABLE_CODE && isValuesValidForSong(values)) {
            Song song = new Song();
            Integer id = values.getAsInteger("id");
            song.setId(id);
            song.setName(values.getAsString("name"));
            song.setDuration(values.getAsString("duration"));
            mMusicDao.insertSong(song);
            return ContentUris.withAppendedId(uri, id);
        } else if (URI_MATCHER.match(uri) == ALBUM_SONG_TABLE_CODE && isValuesValidForAlbumSong(values)) {
            AlbumSong albumSong = new AlbumSong();
            Integer id = values.getAsInteger("id");
            albumSong.setId(id);
            albumSong.setAlbumId(values.getAsInteger("album_id"));
            albumSong.setSongId(values.getAsInteger("song_id"));
            mMusicDao.insertAlbumSong(albumSong);
            return ContentUris.withAppendedId(uri, id);
        }
        else {
            throw new IllegalArgumentException("cant add multiple items");
        }
    }

    private boolean isValuesValidForAlbum(ContentValues values) {
        return values.containsKey("id") && values.containsKey("name") && values.containsKey("release");
    }
    private boolean isValuesValidForSong(ContentValues values) {
        return values.containsKey("id") && values.containsKey("name") && values.containsKey("duration");
    }
    private boolean isValuesValidForAlbumSong(ContentValues values) {
        return values.containsKey("id") && values.containsKey("album_id") && values.containsKey("song_id");
    }
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE && isValuesValidForAlbum(values)) {
            Album album = new Album();
            int id = (int) ContentUris.parseId(uri);
            album.setId(id);
            album.setName(values.getAsString("name"));
            album.setReleaseDate(values.getAsString("release"));
            int updatedRows = mMusicDao.updateAlbumInfo(album);
            return updatedRows;
        } else if (URI_MATCHER.match(uri) == SONG_ROW_CODE && isValuesValidForSong(values)) {
            Song song = new Song();
            int id = (int) ContentUris.parseId(uri);
            song.setId(id);
            song.setName(values.getAsString("name"));
            song.setDuration(values.getAsString("duration"));
            int updateRows = mMusicDao.updateSongInfo(song);
            return updateRows;
        } else if (URI_MATCHER.match(uri) == ALBUM_SONG_ROW_CODE && isValuesValidForAlbumSong(values)) {
            AlbumSong albumSong = new AlbumSong();
            int id = (int) ContentUris.parseId(uri);
            albumSong.setId(id);
            albumSong.setAlbumId(values.getAsInteger("album_id"));
            albumSong.setSongId(values.getAsInteger("song_id"));
            int updateRows = mMusicDao.updateAlbumSongInfo(albumSong);
            return updateRows;
        }
        else {
            throw new IllegalArgumentException("cant add multiple items");
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteAlbumById(id);
        } else if (URI_MATCHER.match(uri) == SONG_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteSongById(id);
        } else if(URI_MATCHER.match(uri) == ALBUM_SONG_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteAlbumSongById(id);
        }
        else {
            throw new IllegalArgumentException("cant add multiple items");
        }

    }
}
