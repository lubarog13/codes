package com.example.traininglog.data.api;

import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.WorkoutResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EsheduleApi {
    @POST("auth/token/login/")
    Single<AuthUser> auth(@Body AuthUser user);

    @GET("auth/users/me/")
    Single<User> me();

    @GET("user/{user_id}/week_workouts")
    Single<WorkoutResponse> getWeekWorkouts(@Path("user_id") int user_id);
}
