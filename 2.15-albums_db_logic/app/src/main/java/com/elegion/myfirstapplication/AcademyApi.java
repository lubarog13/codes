package com.elegion.myfirstapplication;

import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Comment;
import com.elegion.myfirstapplication.model.Song;
import com.elegion.myfirstapplication.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marat.taychinov
 */

public interface AcademyApi {

    @POST("registration")
    Completable registration(@Body User user);

    @GET("user")
    Single<User> auth(@Header("Authorization") String credentials);

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("albums/{id}")
    Single<Album> getAlbum(@Path("id") int id);

    @GET("albums/{id}/comments")
    Single<List<Comment>> getComments(@Path("id") int id);

    @GET("songs")
    Call<List<Song>> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @POST("comments")
    Completable postComment(@Body help_class helpClass);
}
