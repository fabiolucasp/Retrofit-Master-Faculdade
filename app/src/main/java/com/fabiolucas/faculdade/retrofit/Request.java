package com.fabiolucas.faculdade.retrofit;

import com.fabiolucas.faculdade.data.model.Albums;
import com.fabiolucas.faculdade.data.model.Comments;
import com.fabiolucas.faculdade.data.model.Photos;
import com.fabiolucas.faculdade.data.model.Posts;
import com.fabiolucas.faculdade.data.model.Todos;
import com.fabiolucas.faculdade.data.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Request {

    @GET("posts")
    Call<List<Posts>> getPosts();

    @GET("comments")
    Call<List<Comments>> getComments();

    @GET("albums")
    Call<List<Albums>> getAlbums();

    @GET("photos")
    Call<List<Photos>> getPhotos();

    @GET("todos")
    Call<List<Todos>> getTodos();

    @GET("users")
    Call<List<Users>> getUsers();

}
