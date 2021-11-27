package com.example.traininglog.ui.base.messages.incoming;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class IncomingMessagesPresenter extends BasePresenter<IncomingMessagesView> {
    public void getMessages() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getMessagesForUser(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        messageResponse -> getViewState().showMessages(messageResponse.getMessages()),
                        getViewState()::showError
                )
        );
    }

    public void getMessagesFromUser() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getMessagesFromUser(ApiUtils.user_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                messageResponse -> getViewState().showMessages(messageResponse.getMessages()),
                                getViewState()::showError
                        )
        );
    }
}
