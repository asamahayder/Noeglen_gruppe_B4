package com.example.noeglen.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserDAO implements IUserDAO{

    private UserDTO license;
    private FirebaseFirestore db;
    private DocumentReference dRef;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public UserDTO getLicense(String collection, String authLicense) {

        license = new UserDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection(collection).document(authLicense);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                license = documentSnapshot.toObject(UserDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
                System.out.println(license.getUserID());
                System.out.println(license.getLicense());
            }
        });


        System.out.println(license.getLicense());
        System.out.println(license.getUserID());
        return license;
    }

    public void getLicense2(String authLicense, final MyCallBack myCallBack) {

        license = new UserDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection("License").document(authLicense);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                license = documentSnapshot.toObject(UserDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
                myCallBack.onCallBack(license);
            }
        });

        dRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myCallBack.onCallBack(null);
            }
        });

    }
}
