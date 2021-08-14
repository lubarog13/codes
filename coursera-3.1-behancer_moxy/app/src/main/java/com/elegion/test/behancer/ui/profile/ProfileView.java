package com.elegion.test.behancer.ui.profile;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.User;

public interface ProfileView extends BaseView {
    @StateStrategyType(AddToEndStrategy.class)
    void showUser(User user);
}
