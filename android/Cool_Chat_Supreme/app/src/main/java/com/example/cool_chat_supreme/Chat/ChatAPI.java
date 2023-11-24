package com.example.cool_chat_supreme.Chat;

import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.ServerEntities.ChatsResponse;
import com.example.cool_chat_supreme.ServerEntities.NewChatResponse;
import com.example.cool_chat_supreme.ServerEntities.UserResponse;
import com.example.cool_chat_supreme.entities.Chat;
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

public class ChatAPI {
    private MutableLiveData<List<Chat>> chats;
    private ChatDao dao;
    Retrofit retrofit;
    ChatServiceAPI chatServiceAPI;
    //  UserApi constructor should get:  MutableLiveData<User> appUser, UserDao dao


    public ChatAPI(MutableLiveData chats, ChatDao dao, String baseUrl) {
        this.chats = chats;
        this.dao = dao;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        chatServiceAPI = retrofit.create(ChatServiceAPI.class);
    }

    // request List of chats for this user
    public void getChats(String token, CompletableFuture<List<Chat>> future) {
        Call<List<ChatsResponse>> call = chatServiceAPI.getChats("bearer " + token);
        call.enqueue(new Callback<List<ChatsResponse>>() {
            @Override
            public void onResponse(Call<List<ChatsResponse>> call, Response<List<ChatsResponse>> response) {
                if (response.isSuccessful()) {
                    List<ChatsResponse> curr = response.body();
                    // create a new list based on the response and set it to be the new list
                    List<Chat> newChats = new ArrayList<>();
                    for (ChatsResponse i : curr) {
                        // set the other user in the chat
                        String actualpic = "";
                        if (i.getUser().getProfilePic().contains(",")) {
                            actualpic = i.getUser().getProfilePic().split(",")[1];
                        } else {
                            actualpic = i.getUser().getProfilePic();
                        }

                        User user = new User(i.getUser().getUsername(), "",
                                i.getUser().getDisplayName(), actualpic);
                        // set the current list of messages to be empty
                        // the real messages will be fetched if the user presses the contact
                        List<Message> messages = new ArrayList<>();
                        Chat addChat = new Chat(i.getId(), user, messages, null);
                        // instantiate only the final message, if such exists
                        if (i.getLastMessage() != null) {
                            Message msg;
                            msg = new Message(user, user,
                                    i.getLastMessage().getCreated(), i.getLastMessage().getContent());
                            addChat.setLastMessage(msg);
                            newChats.add(addChat);
                        } else {
                            newChats.add(addChat);
                        }
                    }
                    future.complete(newChats);
                } else {
                    future.completeExceptionally(new RuntimeException("getChats failed"));
                }
            }

            @Override
            public void onFailure(Call<List<ChatsResponse>> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("getChats failed"));
            }
        });
    }

    // request creation of a new chat to given String username
    public void createChat(String username, String token, CompletableFuture<Chat> future) {
        Call<NewChatResponse> call = chatServiceAPI.createChat(new UserResponse(username, null, null),
                "bearer " + token);
        call.enqueue(new Callback<NewChatResponse>() {
            @Override
            public void onResponse(Call<NewChatResponse> call, Response<NewChatResponse> response) {
                if (response.isSuccessful()) {
                    // parse the response of the server - the new added chat
                    NewChatResponse curr = response.body();
                    String actualpic = "";
                    if (curr.getUser().getProfilePic().contains(",")) {
                        actualpic = curr.getUser().getProfilePic().split(",")[1];
                    } else {
                        actualpic = curr.getUser().getProfilePic();
                    }

                    User user = new User(curr.getUser().getUsername(), "",
                            curr.getUser().getDisplayName(), actualpic);
                    // set the current list of messages to be empty
                    // the real messages will be fetched if the user presses the contact
                    List<Message> messages = new ArrayList<>();
                    Chat addChat = new Chat(curr.getId(), user, messages, null);
                    future.complete(addChat);
                } else {
                    future.completeExceptionally(new RuntimeException("postChat failed"));
                }

            }

            @Override
            public void onFailure(Call<NewChatResponse> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("postChat failed"));
            }
        });
    }

    // request deletion of chat of id int id chat
    public void deleteChat(int id, String token, CompletableFuture<Boolean> future) {
        Call<Void> call = chatServiceAPI.deleteChat(id, "bearer " + token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                future.complete(false);
            }
        });
    }
}

