package com.elegion.domain.service;

import com.elegion.domain.ApiUtils;
import com.elegion.domain.model.project.Project;
import com.elegion.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

/**
 * Created by tanchuev on 24.04.2018.
 */

public class ProjectServiceImpl implements ProjectService {

    @Inject
    @Named(ProjectRepository.SERVER)
    ProjectRepository mServerRepository;

    @Inject
    @Named(ProjectRepository.DB)
    ProjectRepository mDBRepository;

    @Inject
    public ProjectServiceImpl() {
    }

    @Override
    public Single<List<Project>> getProjects() {
        return mServerRepository.getProjects()
                .doOnSuccess(mDBRepository::insertProjects)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())
                                ? mDBRepository.getProjects().blockingGet()
                                : null);
    }

    @Override
    public void insertProjects(List<Project> projects) {
        mDBRepository.insertProjects(projects);
    }
}
