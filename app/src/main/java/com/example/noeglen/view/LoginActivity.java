package com.example.noeglen.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noeglen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText insertEmail;
    EditText insertPass;
    TextView register;
    TextView forgottenCode;
    TextView logIn;
    TextView registerMenuBtn;
    TextView loginMenuBtn;
    TextView registerSelect;
    TextView loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        insertEmail = findViewById(R.id.EmailInsert);
        insertPass = findViewById(R.id.PassInsert);

        registerSelect = findViewById(R.id.RegisterTV);
        loginBtn = findViewById(R.id.LoginTV);

        loginMenuBtn = findViewById(R.id.LoginBtn);
        registerMenuBtn = findViewById(R.id.RegBtn);

        // Skift ud med hinanden
        registerSelect.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerMenuBtn.setOnClickListener(this);
        loginMenuBtn.setOnClickListener(this);

        registerSelect.setVisibility(View.GONE);
        loginMenuBtn.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            System.out.println(currentUser.getDisplayName());
        }

    }

    @Override
    public void onClick(View v) {

        if(v.equals(registerMenuBtn)){

            registerMenuBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            loginMenuBtn.setVisibility(View.VISIBLE);
            registerSelect.setVisibility(View.VISIBLE);
            insertPass.setText("");
            insertEmail.setText("");

        } else if (v.equals(loginMenuBtn)){

            registerMenuBtn.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            loginMenuBtn.setVisibility(View.GONE);
            registerSelect.setVisibility(View.GONE);
            insertPass.setText("");
            insertEmail.setText("");

        } else if (v.equals(loginBtn) && registerSelect.getVisibility() == View.GONE){

            signIn(insertEmail.getText().toString(), insertPass.getText().toString());

        } else if (v.equals(registerSelect) && loginBtn.getVisibility() == View.GONE){

            createAccount(insertEmail.getText().toString(), insertPass.getText().toString());

        }

    }

    //--- Disse metoder er taget fra Googles firebase preset authentication med lidt ændringer
    //--- https://github.com/firebase/quickstart-android/blob/90389865dc8a64495b1698c4793cd4deecc4d0ee/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/CustomAuthActivity.java#L98-L115
    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            System.out.println("failure");
                        }

                    }
                });
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("success");
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            System.out.println("failure");
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = insertEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            insertEmail.setError("Required.");
            valid = false;
        } else {
            insertEmail.setError(null);
        }

        String password = insertPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            insertPass.setError("Required.");
            valid = false;
        } else {
            insertPass.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }
}
        //------------------------------------------------------------------------------------------------------------



