package com.example.cool_chat_supreme.User;

import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.ServerEntities.UserResponse;
import com.example.cool_chat_supreme.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private MutableLiveData<List<User>> appUsers;
    private UserDao dao;
    Retrofit retrofit;
    UserServiceAPI userServiceAPI;

    //  UserApi constructor should get:  MutableLiveData<User> appUser, UserDao dao
    public UserAPI(MutableLiveData<List<User>> appUsers, UserDao dao, String baseUrl) {;
        this.appUsers = appUsers;
        this.dao = dao;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
    }

    public void getUser(String username, String token, CompletableFuture<User> future,
                        String firebaseToken) {
        Call<UserResponse> call =
                userServiceAPI.getUser(username, "bearer " + token, firebaseToken);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    // if the request succeeded, complete the future with the user
                    UserResponse userResponse = response.body();
                    String actualpic = "";
                    if (userResponse.getProfilePic().contains(",")) {
                        actualpic = userResponse.getProfilePic().split(",")[1];
                    } else {
                        actualpic = userResponse.getProfilePic();
                    }
                    User user = new User(userResponse.getUsername(),
                            "",
                            userResponse.getDisplayName(),
                            actualpic);
                    future.complete(user);
                } else {
                    future.completeExceptionally(new RuntimeException("Failed getting User"));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("Failed getting User"));
            }
        });
    }



    public void addUser(User user, CompletableFuture<String> future) {

        Call<Void> call = userServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code()==200) {
                    future.complete("succeeded"); // complete the future with the response Body
                }
                else if (response.code()==409)   {
                    future.complete("User Already Exists");
                }
                else {
                    future.completeExceptionally(new RuntimeException("Failed Creating User"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("Failed Creating User"));
            }
        });
    }

    public void getToken(User user, CompletableFuture<String> future) {
//        CompletableFuture<String> future = new CompletableFuture<>();

        Call<String> call = userServiceAPI.getToken(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body()); // complete the future with the response Body
                } else {
                    future.completeExceptionally(new RuntimeException("Login Failed"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
//        return future;

    }

////
}

