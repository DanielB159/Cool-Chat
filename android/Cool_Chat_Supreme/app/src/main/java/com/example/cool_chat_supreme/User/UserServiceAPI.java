package com.example.cool_chat_supreme.User;

import com.example.cool_chat_supreme.ServerEntities.UserResponse;
import com.example.cool_chat_supreme.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceAPI {

    @GET("Users/{username}")
    Call<UserResponse> getUser(@Path("username") String username,
                               @Header("authorization") String authorization,
                               @Header("firebaseToken") String firebaseToken);

    @POST("Users")
    Call<Void> createUser(@Body User user);

    @POST("Tokens")
    Call<String> getToken(@Body User user);
}
