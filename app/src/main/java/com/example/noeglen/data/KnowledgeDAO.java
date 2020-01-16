package com.example.noeglen.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeDAO implements IKnowledgeDAO {

    private KnowledgeDTO knowledgeArticle;
    private List<KnowledgeDTO> listOfKnowledgeArticles;

    private FirebaseFirestore db;
    private DocumentReference dRef;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    @Override
    public KnowledgeDTO getArticle(String collection, String articleTitle) {
        knowledgeArticle = new KnowledgeDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection(collection).document(articleTitle);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                knowledgeArticle = documentSnapshot.toObject(KnowledgeDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
            }
        });

        return knowledgeArticle;
    }

    @Override
    public List<KnowledgeDTO> getListOfArticles(final String collection) {
        listOfKnowledgeArticles = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int i = 0;
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        knowledgeArticle = snapshot.toObject(KnowledgeDTO.class);
                        listOfKnowledgeArticles.add(knowledgeArticle);
                    }
                }
                else {
                    Log.d(TAG, "onComplete: could not fetch articles from " + collection);
                }
            }
        });
        return listOfKnowledgeArticles;
    }
}
