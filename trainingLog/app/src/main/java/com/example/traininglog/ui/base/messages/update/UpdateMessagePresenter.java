package com.example.traininglog.ui.base.messages.update;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.MessageCreate;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UpdateMessagePresenter extends BasePresenter<UpdateMessageView> {
    public void getMessage(int message_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getMessage(message_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showMessage,
                        getViewState()::showError
                )
        );
    }

    public void updateMessage(int id, MessageCreate messageCreate) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().updateMessage(id, messageCreate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getViewState()::saveMessage,
                        getViewState()::showError
                )
        );
    }

    public void deleteMessage(int id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().deleteMessage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getViewState()::saveMessage,
                        getViewState()::showError
                )
        );
    }
}
