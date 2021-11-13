package com.elegion.test.behancer.di;

import com.elegion.domain.service.ProjectService;
import com.elegion.domain.service.ProjectServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Module
public class ServiceModule {

    @Provides
    @Singleton
    ProjectService provideProjectService(ProjectServiceImpl projectService) {
        return projectService;
    }
}
