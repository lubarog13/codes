package com.elegion.test.behancer.di;

import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.domain.repository.ProjectRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @Named(ProjectRepository.SERVER)
    ProjectRepository provideProjectServerRepository(ProjectServerRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    @Named(ProjectRepository.DB)
    ProjectRepository provideProjectDBRepository(ProjectDBRepository repository) {
        return repository;
    }
}
