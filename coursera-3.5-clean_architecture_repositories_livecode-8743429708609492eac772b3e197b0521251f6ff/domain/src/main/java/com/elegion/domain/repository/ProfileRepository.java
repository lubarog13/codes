package com.elegion.domain.repository;

import com.elegion.domain.model.project.Project;
import com.elegion.domain.model.user.User;

import java.util.List;

import io.reactivex.Single;

public interface ProfileRepository {
    public static final String SERVER = "SERVER";
    public static final String DB = "DB";

    Single<User> getUser(String username);

    void insertUser(User user);
}
