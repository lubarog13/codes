package com.elegion.test.behancer.common;

import android.support.v4.app.Fragment;

/**
 * Created by Vladislav Falzan.
 */

public abstract class PresenterFragment<P extends BasePresenter> extends Fragment {

    protected abstract P getPresenter();

    @Override
    public void onDetach() {
        if (getPresenter() != null) {
            getPresenter().disposeAll();
        }
        super.onDetach();
    }
}
