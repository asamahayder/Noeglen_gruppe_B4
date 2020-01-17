package com.example.noeglen.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noeglen.R;
import com.example.noeglen.data.IUserDAO;
import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.UserDAO;
import com.example.noeglen.data.UserDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText insertEmail;
    EditText insertPass;
    EditText insertLicense;
    TextView registerMenuBtn;
    TextView loginMenuBtn;
    TextView registerSelect;
    TextView loginBtn;
    TextView TVlicense;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    IUserDAO iUserDAO;
    UserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            Intent welcome = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(welcome);
        }


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        insertEmail = findViewById(R.id.EmailInsert);
        insertPass = findViewById(R.id.PassInsert);
        insertLicense = findViewById(R.id.LicenseInsert);
        insertLicense.setVisibility(View.GONE);

        TVlicense = findViewById(R.id.LicenseTV);
        TVlicense.setVisibility(View.GONE);

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

        iUserDAO = new UserDAO();

    }

    @Override
    public void onClick(View v) {

        if (v.equals(registerMenuBtn)) {

            registerMenuBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            loginMenuBtn.setVisibility(View.VISIBLE);
            registerSelect.setVisibility(View.VISIBLE);
            insertPass.setText("");
            insertEmail.setText("");
            insertLicense.setText("");
            insertLicense.setVisibility(View.VISIBLE);
            TVlicense.setVisibility(View.VISIBLE);

        } else if (v.equals(loginMenuBtn)) {

            registerMenuBtn.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            loginMenuBtn.setVisibility(View.GONE);
            registerSelect.setVisibility(View.GONE);
            insertPass.setText("");
            insertEmail.setText("");
            insertLicense.setVisibility(View.GONE);
            TVlicense.setVisibility(View.GONE);

        } else if (v.equals(loginBtn) && registerSelect.getVisibility() == View.GONE) {

            signIn(insertEmail.getText().toString(), insertPass.getText().toString());

        } else if (v.equals(registerSelect) && loginBtn.getVisibility() == View.GONE) {


            licenseCheck(insertLicense.getText().toString());
            if(userDTO.isUsed()){
                createAccount(insertEmail.getText().toString(), insertPass.getText().toString());
            }

        }


    }

    //--- Disse metoder er taget fra Googles firebase preset authentication med lidt ændringer
    //--- https://github.com/firebase/quickstart-android/blob/90389865dc8a64495b1698c4793cd4deecc4d0ee/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/CustomAuthActivity.java#L98-L115
    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);


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
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    private void licenseCheck(final String license) {

        if (!validateForm()) {
            return;
        }

        UserDAO userDAO = new UserDAO();
        userDAO.getLicense2(license, new MyCallBack() {
            @Override
            public void onCallBack(Object object) {
                UserDTO licens = (UserDTO) object;

                if (licens == null) {

                    Toast.makeText(LoginActivity.this, "Ubrulig licens", Toast.LENGTH_SHORT).show();

                } else {

                    if (licens.getUserID().equals("")) {
                        userDTO.setUsed(true);
                    } else {
                        Toast.makeText(LoginActivity.this, "Licens allerede i brug", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        registerSelect.setVisibility(View.GONE);

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
                    progressBar.setVisibility(View.GONE);
                    registerSelect.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = insertEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            insertEmail.setError("Krævet.");
            valid = false;
        } else {
            insertEmail.setError(null);
        }

        String password = insertPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            insertPass.setError("Krævet.");
            valid = false;
        } else {
            insertPass.setError(null);
        }

        if (insertLicense.getVisibility() == View.VISIBLE) {
            String license = insertLicense.getText().toString();
            if (TextUtils.isEmpty(license)) {
                insertLicense.setError("Krævet.");
                valid = false;
            } else {
                insertLicense.setError(null);
            }
        }

        return valid;
    }

    private boolean setChecker(boolean check) {



        return check;
    }


}
//------------------------------------------------------------------------------------------------------------