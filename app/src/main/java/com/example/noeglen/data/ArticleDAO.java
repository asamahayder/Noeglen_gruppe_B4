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

public class ArticleDAO implements IArticleDAO {

    private ArticleDTO article;
    private List<ArticleDTO> listOfArticles;

    private FirebaseFirestore db;
    private DocumentReference dRef;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    @Override
    public ArticleDTO getArticle(String collection, String articleTitle) {
        article = new ArticleDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection(collection).document(articleTitle);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                article = documentSnapshot.toObject(ArticleDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
            }
        });

        return article;
    }

    @Override
    public List<ArticleDTO> getListOfArticles(final String collection) {
        listOfArticles = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int i = 0;
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        article = snapshot.toObject(ArticleDTO.class);
                        listOfArticles.add(article);
                    }
                }
                else {
                    Log.d(TAG, "onComplete: could not fetch articles from " + collection);
                }
            }
        });
        return listOfArticles;
    }
}
