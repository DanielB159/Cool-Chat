package com.example.cool_chat_supreme.Message;

import com.example.cool_chat_supreme.ServerEntities.MessageRequest;
import com.example.cool_chat_supreme.ServerEntities.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServiceAPI {

    @GET("Chats/{id}/Messages")
    Call<List<MessageResponse>> getMessages(@Path("id") int id, @Header("authorization") String authorization);

    @POST("Chats/{id}/Messages")
    Call<MessageResponse> sendMessage(@Path("id") int id, @Body MessageRequest msg, @Header("authorization") String authorization);  // THE BODY IS INCORRECT
}
