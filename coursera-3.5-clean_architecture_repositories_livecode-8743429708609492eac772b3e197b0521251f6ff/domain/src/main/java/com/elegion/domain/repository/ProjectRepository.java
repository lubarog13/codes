package com.elegion.domain.repository;

import com.elegion.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by tanchuev on 24.04.2018.
 */

public interface ProjectRepository {
    public static final String SERVER = "SERVER";
    public static final String DB = "DB";

    Single<List<Project>> getProjects();

    void insertProjects(List<Project> projects);

}
