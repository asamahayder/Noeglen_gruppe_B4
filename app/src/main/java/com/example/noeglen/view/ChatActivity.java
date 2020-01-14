package com.example.noeglen.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noeglen.R;
import com.scaledrone.lib.Message;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;

public class ChatActivity extends AppCompatActivity implements RoomListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void onOpen(Room room) {

    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {

    }

    @Override
    public void onMessage(Room room, Message message) {

    }
}
