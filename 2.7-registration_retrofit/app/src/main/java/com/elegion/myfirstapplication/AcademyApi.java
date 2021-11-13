package com.elegion.myfirstapplication;

import com.elegion.myfirstapplication.model.Album;
import com.elegion.myfirstapplication.model.Albums;
import com.elegion.myfirstapplication.model.Song;
import com.elegion.myfirstapplication.model.Songs;
import com.elegion.myfirstapplication.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by marat.taychinov
 */

public interface AcademyApi {

    @POST("registration")
    Call<Void> registration(@Body User user);

    //Для исправления вылета именно Object
    @GET("user")
    Call<Object> user(@Header("Authorization") String credential);

    @GET("albums")
    Call<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<Songs> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);
}
