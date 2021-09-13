package com.elegion.test.behancer.di;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.ui.profile.ProfilePresenter;
import com.elegion.test.behancer.ui.profile.ProfileView;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;
import com.elegion.test.behancer.ui.projects.ProjectsView;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class PresenterModule {
    private final BaseView mBaseView;

    public PresenterModule(BaseView mBaseView) {
        this.mBaseView = mBaseView;
    }

    @Provides
    @FragmentScope
    ProfileView provideView() {
        return (ProfileView)mBaseView;
    }
}
