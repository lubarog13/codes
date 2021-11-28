package com.example.traininglog.ui.base.messages.create;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.MessageCreate;
import com.example.traininglog.data.model.User;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CreateMessagePresenter extends BasePresenter<CreateMessageView> {
    public void getUsers(String username) {
        User user = new User();
        user.setUsername(username);
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUsersByName(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        userResponse -> getViewState().showUsers(userResponse.getUsers()),
                        getViewState()::showError
                )
        );
    }

    public void createMessage(MessageCreate message) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().createMessage(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getViewState()::saveMessage,
                        getViewState()::showError
                )
        );
    }
}
