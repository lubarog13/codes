package com.example.traininglog.data.api;

import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.PresenceResponse;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.WorkoutResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EsheduleApi {
    @POST("auth/token/login/")
    Single<AuthUser> auth(@Body AuthUser user);

    @GET("auth/users/me/")
    Single<User> me();

    @GET("user/{user_id}/week_workouts/")
    Single<WorkoutResponse> getWeekWorkouts(@Path("user_id") int user_id);

    @PATCH("presence/update/user/{user_id}/workout/{workout_id}/")
    Completable updatePresenceForWorkout(@Path("user_id") int user_id, @Path("workout_id") int workout_id, @Body Presence presence);

    @GET("workout/{workout_id}/presences/")
    Single<PresenceResponse> getPresencesForWorkout(@Path("workout_id") int workout_id);

    @POST("auth/token/logout/")
    Completable logout();
}
