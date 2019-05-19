package com.seunghyun.firebasetest;

class Item {
    private String id, chat;

    Item(String id, String chat) {
        this.id = id;
        this.chat = chat;
    }

    String getId() {
        return id;
    }

    String getChat() {
        return chat;
    }
}
