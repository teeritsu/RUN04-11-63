package com.example.apprunner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrganizerAPI {

    @POST("add_event")
    Call<ResultQuery> insertEvent(@Body EventFormDB body);

    @GET("login/{email}/{password}")
    Call<ResultQuery> login(@Path("email") String username, @Path("password") String password);

    @POST("register")
    Call<ResultQuery> insertRegister(@Body  userDB body);

    @GET("show_event")
    Call<List<UserListResponse>> getUserName(@Query("status") int type);

    @GET("search_event/{status}/{name_event}")
    Call<List<UserListResponse>> search_event(@Path("status") int status,@Path("name_event") String name_event);
}

