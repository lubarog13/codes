package com.elegion.test.behancer.ui.projects;

import com.elegion.domain.service.ProjectService;
import com.elegion.test.behancer.common.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsPresenter extends BasePresenter {

    private ProjectsView mView;
    @Inject
    ProjectService mService;

    @Inject
    public ProjectsPresenter() {
    }

    public void setView(ProjectsView view) {
        mView = view;
    }

    public void getProjects() {
        mCompositeDisposable.add(
                mService.getProjects()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                        .subscribe(
                                response -> mView.showProjects(response),
                                throwable -> mView.showError())
        );
    }

    public void openProfileFragment(String username) {
        mView.openProfileFragment(username);
    }
}
