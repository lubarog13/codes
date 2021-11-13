package com.elegion.test.behancer.common;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by Vladislav Falzan.
 */

public abstract class PresenterFragment extends MvpAppCompatFragment {

    protected abstract BasePresenter getPresenter();
}
