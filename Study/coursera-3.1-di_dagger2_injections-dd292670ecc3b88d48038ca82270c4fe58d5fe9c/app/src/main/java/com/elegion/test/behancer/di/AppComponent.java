package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.projects.ProjectsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(ProjectsFragment injector);
}
