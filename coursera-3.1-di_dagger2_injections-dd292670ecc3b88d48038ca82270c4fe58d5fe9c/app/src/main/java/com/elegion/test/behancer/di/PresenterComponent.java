package com.elegion.test.behancer.di;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;
@FragmentScope
@Component(dependencies = AppComponent.class, modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(ProfileFragment inject);
}
