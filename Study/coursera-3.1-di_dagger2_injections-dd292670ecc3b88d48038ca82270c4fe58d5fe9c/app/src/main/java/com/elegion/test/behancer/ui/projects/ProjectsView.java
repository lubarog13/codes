package com.elegion.test.behancer.ui.projects;

import android.support.annotation.NonNull;

import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.common.BaseView;

import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public interface ProjectsView extends BaseView {

    void showProjects(@NonNull List<Project> projects);

    void openProfileFragment(@NonNull String username);
}
