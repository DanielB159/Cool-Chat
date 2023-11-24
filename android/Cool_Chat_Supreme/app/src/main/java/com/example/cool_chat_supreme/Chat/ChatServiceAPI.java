package com.example.cool_chat_supreme.Chat;

import com.example.cool_chat_supreme.ServerEntities.ChatResponse;
import com.example.cool_chat_supreme.ServerEntities.ChatsResponse;
import com.example.cool_chat_supreme.ServerEntities.NewChatResponse;
import com.example.cool_chat_supreme.ServerEntities.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatServiceAPI {

    @GET("Chats")
    Call<List<ChatsResponse>> getChats(@Header("authorization") String authorization);

    @POST("Chats")
    Call<NewChatResponse> createChat(@Body UserResponse user, @Header("authorization") String authorization);

    @GET("Chats/{id}")
    Call<ChatResponse> getChat(@Path("id") int id, @Header("authorization") String authorization);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Path("id") int id, @Header("authorization") String authorization);
}
