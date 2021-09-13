package com.elegion.data.repository;

import com.elegion.data.api.BehanceApi;
import com.elegion.domain.model.user.User;
import com.elegion.domain.model.user.UserResponse;
import com.elegion.domain.repository.ProfileRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ProfileServerRepository implements ProfileRepository {
    @Inject
    BehanceApi mApi;

    @Inject
    public ProfileServerRepository() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mApi.getUserInfo(username).map(new Function<UserResponse, User>() {
            @Override
            public User apply(@NonNull UserResponse userResponse) throws Exception {
                return userResponse.getUser();
            }
        });
    }

    @Override
    public void insertUser(User user) {

    }
}
