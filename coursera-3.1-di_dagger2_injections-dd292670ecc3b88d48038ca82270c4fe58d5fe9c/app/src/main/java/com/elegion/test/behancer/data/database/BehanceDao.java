package com.elegion.test.behancer.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elegion.test.behancer.data.model.project.Cover;
import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.user.Image;
import com.elegion.test.behancer.data.model.user.User;

import java.util.List;

/**
 * Created by Vladislav Falzan.
 */
@Dao
public interface BehanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjects(List<Project> projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCovers(List<Cover> covers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOwners(List<Owner> owners);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImage(Image image);

    @Query("select * from project")
    List<Project> getProjects();

    @Query("select * from cover where project_id = :projectId")
    Cover getCoverFromProject(int projectId);

    @Query("select * from owner where project_id = :projectId")
    List<Owner> getOwnersFromProject(int projectId);

    @Query("select * from user where username = :userName")
    User getUserByName(String userName);

    @Query("select * from image where user_id = :userId")
    Image getImageFromUser(int userId);

    @Query("delete from owner")
    void clearOwnerTable();

    @Query("delete from cover")
    void clearCoverTable();

    @Query("delete from image")
    void clearImageTable();

    @Query("select * from user")
    List<User> getUsers();

    @Query("select * from image")
    List<Image> getImages();
}
