package com.elegion.test.behancer.data.model.project;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectResponse implements Serializable {

    @SerializedName("projects")
    private List<Project> mProjects;

    public List<Project> getProjects() {
        return mProjects;
    }

    public void setProjects(List<Project> projects) {
        mProjects = projects;
    }
}
