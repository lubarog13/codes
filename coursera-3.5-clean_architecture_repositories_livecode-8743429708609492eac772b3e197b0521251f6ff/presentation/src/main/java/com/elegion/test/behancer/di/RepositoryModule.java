package com.elegion.test.behancer.di;

import com.elegion.data.repository.ProfileDBRepository;
import com.elegion.data.repository.ProfileServerRepository;
import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.domain.repository.ProfileRepository;
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
    ProjectRepository provideProjectServerRepository() {
        return new ProjectServerRepository();
    }

    @Provides
    @Singleton
    @Named(ProjectRepository.DB)
    ProjectRepository provideProjectDBRepository() {
        return new ProjectDBRepository();
    }

    @Provides
    @Singleton
    @Named(ProfileRepository.SERVER)
    ProfileRepository provideProfileServerRepository(){return new ProfileServerRepository();}

    @Provides
    @Singleton
    @Named(ProfileRepository.DB)
    ProfileRepository provideProfileDBRepository(){return new ProfileDBRepository();}
}
