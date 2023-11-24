package com.example.cool_chat_supreme.ServerEntities;

public class MessageRequest {
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageRequest(String msg) {
        this.msg = msg;
    }
}
