package com.elegion.data.repository;

import com.elegion.data.BuildConfig;
import com.elegion.data.api.BehanceApi;
import com.elegion.domain.model.project.Project;
import com.elegion.domain.model.project.ProjectResponse;
import com.elegion.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectServerRepository implements ProjectRepository {

    @Inject
    BehanceApi mApi;

    @Inject
    public ProjectServerRepository() {
    }

    @Override
    public Single<List<Project>> getProjects() {
        return mApi.getProjects(BuildConfig.API_QUERY).map(new Function<ProjectResponse, List<Project>>() {
            @Override
            public List<Project> apply(ProjectResponse projectResponse) throws Exception {
                return projectResponse.getProjects();
            }
        });
    }

    @Override
    public void insertProjects(List<Project> projects) {
        //do nothing
    }
}
