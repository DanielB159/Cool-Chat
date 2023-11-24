package com.example.cool_chat_supreme.Message;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.LoginActivity;
import com.example.cool_chat_supreme.ServerEntities.MessageRequest;
import com.example.cool_chat_supreme.ServerEntities.MessageResponse;
import com.example.cool_chat_supreme.entities.Message;
import com.example.cool_chat_supreme.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private MutableLiveData<List<Message>> messageList;
    private MessageDao dao;
    Retrofit retrofit;
    MessageServiceAPI messageServiceAPI;
    //  UserApi constructor should get:  MutableLiveData<User> appUser, UserDao dao
    public MessageAPI(MutableLiveData<List<Message>> messageList, MessageDao dao, String baseUrl) {
        this.dao = dao;
        this.messageList = messageList;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        messageServiceAPI = retrofit.create(MessageServiceAPI.class);
    }

    // request messages from chat of id int id
    public void getMessages(int id, String token, CompletableFuture<List<Message>> future) {
        Call<List<MessageResponse>> call = messageServiceAPI.getMessages(id, "bearer " + token);
        call.enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful()) {
                    List<MessageResponse> curr = response.body();
                    List<Message> newMessages = new ArrayList<Message>();
                    for (MessageResponse i : curr) {
                        newMessages.add(new Message(
                                new User(i.getSender().getUsername(), null, null, null),
                                null, i.getCreated(), i.getContent())); // maybe change created
                    }
                    future.complete(newMessages);
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
            }
        });
    }

    // post new message
    public void sendMessage(int id, String content, String token, CompletableFuture<Void> future) {
        Call<MessageResponse> call = messageServiceAPI.sendMessage(id, new MessageRequest(content), "bearer " + token);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse curr = response.body();
                    Message newMessage = new Message(
                            new User(curr.getSender().getUsername(), null,
                                    curr.getSender().getDisplayName(), curr.getSender().getProfilePic()),
                            null, curr.getCreated(), curr.getContent());
                    dao.insert(newMessage);
                    future.complete(null);
                } else {
                    future.completeExceptionally(new RuntimeException("Failed sending message"));
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("Failed sending message"));
            }
        });
    }
}
