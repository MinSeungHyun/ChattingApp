package com.seunghyun.firebasetest;

class Item {
    private String id, chat, time;

    Item(String id, String chat, String time) {
        this.id = id;
        this.chat = chat;
        this.time = time;
    }

    String getId() {
        return id;
    }

    String getChat() {
        return chat;
    }

    String getTime() {
        return time;
    }
}
