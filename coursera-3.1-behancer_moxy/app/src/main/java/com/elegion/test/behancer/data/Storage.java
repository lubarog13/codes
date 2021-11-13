package com.elegion.test.behancer.data;

import android.support.v4.util.Pair;

import com.elegion.test.behancer.data.database.BehanceDao;
import com.elegion.test.behancer.data.model.project.Cover;
import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.user.Image;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.data.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class Storage {

    private BehanceDao mBehanceDao;

    public Storage(BehanceDao behanceDao) {
        mBehanceDao = behanceDao;
    }

    public void insertProjects(ProjectResponse response) {
        List<Project> projects = response.getProjects();
        mBehanceDao.insertProjects(projects);

        Pair<List<Cover>, List<Owner>> assembled = assemble(projects);

        mBehanceDao.clearCoverTable();
        mBehanceDao.insertCovers(assembled.first);
        mBehanceDao.clearOwnerTable();
        mBehanceDao.insertOwners(assembled.second);
    }

    private Pair<List<Cover>, List<Owner>> assemble(List<Project> projects) {

        List<Cover> covers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Cover cover = projects.get(i).getCover();
            cover.setId(i);
            cover.setProjectId(projects.get(i).getId());
            covers.add(cover);

            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return new Pair<>(covers, owners);
    }

    public ProjectResponse getProjects() {
        List<Project> projects = mBehanceDao.getProjects();
        for (Project project : projects) {
            project.setCover(mBehanceDao.getCoverFromProject(project.getId()));
            project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
        }

        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);

        return response;
    }

    public void insertUser(UserResponse response) {
        User user = response.getUser();
        Image image = user.getImage();
        image.setId(user.getId());
        image.setUserId(user.getId());

        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }

    public UserResponse getUser(String username) {
        User user = mBehanceDao.getUserByName(username);
        Image image = mBehanceDao.getImageFromUser(user.getId());
        user.setImage(image);

        UserResponse response = new UserResponse();
        response.setUser(user);

        return response;
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }

}
