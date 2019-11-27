package com.example.noeglen.View.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.noeglen.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Denne klasse indeholder to måder at lave en login funktionalitet vha. google. Dene ene med onActivityResult kan både skabe brugere og login
// på samme tid, hvilket er smart men også kan virke forvirrende. Den anden kan bare skabe brugere og måske logger den også automatisk ind bagefter.

public class ActivitySignUp extends AppCompatActivity {

    //metode 2
    //private FirebaseAuth mAuth;

    //hører til metode 1
    //private boolean loggedin;

    //for trying to get videolist
    private List<String> videoIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tryGetListFromFireStore();


        //hører til metode 2
        //mAuth = FirebaseAuth.getInstance();

        //hører til metode 1
        /*// Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                1);*/
    }

    //hører til metode 1. Dette er hvad jeg går efter
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                loggedin = true;

            } else {
                //TODO ijplement better handling
                loggedin = false;
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                System.out.println("OH no");
            }
        }

        if (loggedin){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put("lastName", "Hayder");
            db.collection("users").document("asamahayder").set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("#####################the transfer was a success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("########################the transfer was NOT a success");
                }
            });
        }
    }*/

    //hører til metode to
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String email = "randommail@random.random";
        String password = "randompassword";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("#############user created succesfully");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("#################could not create user");
                            Toast.makeText(ActivitySignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });

        //Todo implement this
        //updateUI(currentUser);
    }*/


    public void tryGetListFromFireStore(){
        List<String> videoIDs;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("videos").document("videolist");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("document exist");
                        DocumentSnapshot documentSnapshot = task.getResult();
                        setVideoIDs ((List<String>) documentSnapshot.get("videoIDs"));
                        System.out.println("size of array: " + getVideoIDs().size());
                        System.out.println("Here is the content:");
                        for (int i = 0; i < getVideoIDs().size(); i++) {
                            System.out.println(getVideoIDs().get(i));
                        }
                    } else {
                        System.out.println("no such document");
                    }
                } else {
                    System.out.println("failed: " + task.getException());
                }
            }
        });
    }


    public List<String> getVideoIDs() {
        return videoIDs;
    }

    public void setVideoIDs(List<String> videoIDs) {
        this.videoIDs = videoIDs;
    }
}
