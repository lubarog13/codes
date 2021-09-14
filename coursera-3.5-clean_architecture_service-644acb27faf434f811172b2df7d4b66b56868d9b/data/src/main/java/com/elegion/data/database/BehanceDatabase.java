package com.elegion.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elegion.domain.model.project.Cover;
import com.elegion.domain.model.project.Owner;
import com.elegion.domain.model.project.Project;
import com.elegion.domain.model.user.Image;
import com.elegion.domain.model.user.User;

/**
 * Created by Vladislav Falzan.
 */

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
