package com.elegion.test.behancer.ui.projects;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.common.BaseView;

import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public interface ProjectsView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProjects(@NonNull List<Project> projects);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void openProfileFragment(@NonNull String username);
}
