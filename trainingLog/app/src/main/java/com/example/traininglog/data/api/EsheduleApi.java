package com.example.traininglog.data.api;



import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.ClubCreate;
import com.example.traininglog.data.model.ClubResponse;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.CoachResponse;
import com.example.traininglog.data.model.FCMDevice;
import com.example.traininglog.data.model.FCMMessage;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.HallsResponse;
import com.example.traininglog.data.model.Message;
import com.example.traininglog.data.model.MessageCreate;
import com.example.traininglog.data.model.MessageResponse;
import com.example.traininglog.data.model.MonthsResponse;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.PresenceResponse;
import com.example.traininglog.data.model.PresencesResponse;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.SignUpForCoachCreate;
import com.example.traininglog.data.model.SignUpForCreate;
import com.example.traininglog.data.model.SignUpResponse;
import com.example.traininglog.data.model.SimplePresence;
import com.example.traininglog.data.model.TypesResponse;
import com.example.traininglog.data.model.User;
import com.example.traininglog.data.model.UserResponse;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.data.model.WorkoutResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("user/{user_id}/sign_ups/")
    Single<SignUpResponse> getSignUpsForUser(@Path("user_id") int user_id);

    @GET("club/{club_id}/users/")
    Single<UserResponse> getUsersForClub(@Path("club_id") int club_id);

    @GET("clubs/")
    Single<List<Club>> getClubs();

    @GET("coaches/")
    Single<List<Coach>> getCoaches();

    @GET("coach/{coach_id}/clubs/")
    Single<ClubResponse> getClubsForCoach(@Path("coach_id") int coach_id);

    @GET("buildings/")
    Single<List<Building>> getBuildings();

    @GET("building/{building_id}/clubs/")
    Single<ClubResponse> getClubsForBuilding(@Path("building_id") int building_id);

    @POST("signup/create/")
    Single<SignUp> createSignUp(@Body SignUpForCreate signUp);


    @GET("user/{user_id}/presences/month/{month}/{year}")
    Single<PresencesResponse> getPresencesForMonth(@Path("user_id") int user_id, @Path("month") int month, @Path("year") int year);

    @POST("auth/users/")
    Single<User> registration(@Body AuthUser user);

    @GET("building/{building_id}/halls/")
    Single<HallsResponse> getHallsForBuilding(@Path("building_id") int building_id);

    @GET("hall/{hall_id}/")
    Single<Hall> getHall(@Path("hall_id") int hall_id);

    @GET("user/{user_id}/analysis/types/")
    Single<TypesResponse> getCountForTypes(@Path("user_id") int user_id);

    @GET("user/{user_id}/analysis/{year}/")
    Single<MonthsResponse> getCountForMonth(@Path("user_id") int user_id, @Path("year") int year);

    @GET("user/{user_id}/presences/count/{month}/")
    Single<TypesResponse> getCountForTypesInMonth(@Path("user_id") int user_id, @Path("month") int month);

    @GET("user/{user_id}/messages/")
    Single<MessageResponse> getMessagesForUser(@Path("user_id") int user_id);

    @GET("user/{user_id}/messages/send/")
    Single<MessageResponse> getMessagesFromUser(@Path("user_id") int user_id);

    @POST("search/user/")
    Single<UserResponse> getUsersByName(@Body User name);

    @POST("message/create/")
    Completable createMessage(@Body MessageCreate message);

    @GET("message/{message_id}/")
    Single<Message> getMessage(@Path("message_id") int id);

    @PUT("message/{message_id}/update/")
    Completable updateMessage(@Path("message_id") int id, @Body MessageCreate messageCreate);

    @DELETE("message/{message_id}/delete/")
    Completable deleteMessage(@Path("message_id") int id);

    @PATCH("auth/users/me/")
    Single<User> updateUser(@Body AuthUser authUser);

    @DELETE("signup/{signup_id}/delete/")
    Completable deleteSignup(@Path("signup_id") int signup_id);

    @GET("halls/")
    Single<List<Hall>> getHalls();

    @GET("coach/user{user_id}/")
    Single<CoachResponse> getCoach(@Path("user_id") int user_id);

    @GET("coach/{coach_id}/week_workouts/")
    Single<WorkoutResponse> getWorkoutsForCoach(@Path("coach_id") int coach_id);

    @PUT("workout/{id}/update/")
    Completable updateWorkout(@Path("id") int id, @Body WorkoutForEdit workout);

    @GET("coach/{coach_id}/workouts/{month}/{year}")
    Single<WorkoutResponse> getWorkoutsForMonth(@Path("coach_id") int coach_id, @Path("month") int month, @Path("year") int year);

    @GET("coach/{coach_id}/presences/{day}/{month}/{year}/")
    Single<PresencesResponse> getPresencesForDay(@Path("coach_id") int coach_id, @Path("day") int day, @Path("month") int month, @Path("year") int year);

    @PUT("presence/{id}/update/")
    Completable updatePresence(@Path("id") int id, @Body SimplePresence presence);

    @POST("coach/{coach_id}/analysis/groups/presences/")
    Single<GroupAnalysis> getGroupPresence(@Path("coach_id") int coach_id, @Body GroupAnalysis.DayType type);

    @GET("coach/{coach_id}/analysis/club/{club_id}/workouts/")
    Single<TypesResponse> getCountForTypesForGroup(@Path("coach_id") int coach_id, @Path("club_id") int club_id);

    @POST("coach/{coach_id}/analysis/groups/month/")
    Single<GroupAnalysis> getWorkoutCountForMonth(@Path("coach_id") int coach_id, @Body GroupAnalysis.DayType type);

    @POST("coach/{coach_id}/analysis/groups/presences/month/")
    Single<GroupAnalysis> getPresenceCountForMonth(@Path("coach_id") int coach_id, @Body GroupAnalysis.DayType type);

    @POST("club/create/")
    Completable createClub(@Body ClubCreate clubCreate);


    @POST("workout/create/")
    Completable createWorkout(@Body WorkoutForEdit workout);

    @POST("coach/signup/create/")
    Completable createCoachSignup(@Body SignUpForCoachCreate signUpForCoachCreate);

    @POST("fcmdevice/create/")
    Single<FCMDevice> createFCMDevice(@Body FCMDevice fcmDevice);

    @PATCH("fcmdevice/{id}/update/")
    Single<FCMDevice> updateDevice(@Path("id") int id, @Body FCMDevice fcmDevice);

    @GET("user/{user_id}/fcmdevices/")
    Single<FCMDevice.DeviceResponse> getDevicesForUser(@Path("user_id") int user_id);

    @POST("send_message/")
    Completable sendMessage(@Body FCMMessage fcmMessage);

    @POST("auth/users/reset_password/")
    Completable resetPassword(@Body AuthUser authUser);
}
