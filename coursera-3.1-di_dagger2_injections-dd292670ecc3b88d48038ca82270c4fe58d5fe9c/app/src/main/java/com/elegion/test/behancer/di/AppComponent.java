package com.elegion.test.behancer.di;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    Storage provideStorage();
    BehanceApi provideApiService();

    void inject(ProjectsFragment injector);
}
