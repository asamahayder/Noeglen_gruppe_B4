package com.example.noeglen.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.noeglen.R;

public class LoginActivity extends AppCompatActivity {

    EditText insertEmail;
    EditText insertPass;
    TextView register;
    TextView forgottenCode;
    TextView logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        insertEmail = findViewById(R.id.EmailInsert);
        insertPass = findViewById(R.id.PassInsert);
    }
}
