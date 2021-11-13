package com.elegion.data.api;

import com.elegion.domain.model.project.ProjectResponse;
import com.elegion.domain.model.user.UserResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Vladislav Falzan.
 */

public interface BehanceApi {

    @GET("v2/projects")
    Single<ProjectResponse> getProjects(@Query("q") String query);

    @GET("v2/users/{username}")
    Single<UserResponse> getUserInfo(@Path("username") String username);
}
