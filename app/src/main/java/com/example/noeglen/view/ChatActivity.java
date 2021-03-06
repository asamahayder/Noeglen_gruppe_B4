package com.example.noeglen.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noeglen.R;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        backBtn = findViewById(R.id.btn_stop_chat);

        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

/*
Lavet af gruppe B4 for ComeBack
Kursus: 62550 62550 Brugerinteraktion og udvikling på mobile enheder
Medlemmer af gruppen:
Simon Andersen (s185083), Asama Hayder(s185099), Jákup Viljam Dam(s185095), Christoffer Adrian Detlef(s185117) & Thaer Almalla(s170727)
*/