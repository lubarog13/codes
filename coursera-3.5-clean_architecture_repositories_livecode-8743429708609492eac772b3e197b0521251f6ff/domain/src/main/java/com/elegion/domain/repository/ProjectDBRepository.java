package com.elegion.domain.repository;

import android.support.v4.util.Pair;

import com.elegion.data.database.BehanceDao;
import com.elegion.domain.model.project.Cover;
import com.elegion.domain.model.project.Owner;
import com.elegion.domain.model.project.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectDBRepository implements ProjectRepository {

    @Inject
    BehanceDao mBehanceDao;

    @Inject
    public ProjectDBRepository() {
    }

    @Override
    public Single<List<Project>> getProjects() {
        return Single.fromCallable(new Callable<List<Project>>() {
            @Override
            public List<Project> call() throws Exception {
                List<Project> projects = mBehanceDao.getProjects();
                for (Project project : projects) {
                    project.setCover(mBehanceDao.getCoverFromProject(project.getId()));
                    project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
                }

                return projects;
            }
        });
    }

    @Override
    public void insertProjects(List<Project> projects) {
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
}
