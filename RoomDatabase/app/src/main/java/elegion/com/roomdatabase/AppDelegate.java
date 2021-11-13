package elegion.com.roomdatabase;

import android.app.Application;
import android.arch.persistence.room.Room;

import elegion.com.roomdatabase.database.MusicDatabase;

/**
 * @author Azret Magometov
 */

public class AppDelegate extends Application {

    private MusicDatabase mMusicDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicDatabase = Room.databaseBuilder(getApplicationContext(), MusicDatabase.class, "music_database")
                .allowMainThreadQueries()
                .build();
    }

    public MusicDatabase getMusicDatabase() {
        return mMusicDatabase;
    }
}
