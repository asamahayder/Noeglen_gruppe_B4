package com.example.noeglen.data;

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

public class VideoDAO implements IVideoDAO{

    List<String> weekList;
    VideoDTO video;

    public VideoDTO getVideo(String week, String videoName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(week).document(videoName);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                VideoDTO video = documentSnapshot.toObject(VideoDTO.class);
                setVideo(video);
                System.out.println(video.getTitle());
                System.out.println(video.getVideoUrl());
            }
        });
        return video;
    }

    public List<String> getWeekList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Weeks").document("ListOfAllWeeks").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                List<String> weekList = (List<String>) snapshot.get("list");
                setWeekList(weekList);
            }
        });
        return weekList;
    }

    public List<VideoDTO> getAllVideosFromWeek(final String week){
        final List<VideoDTO> videoList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(week).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot: task.getResult()) {
                        VideoDTO videoDTO = snapshot.toObject(VideoDTO.class);
                        videoList.add(videoDTO);
                    }
                }else{
                    System.out.println("Could not fetch videos from: " + week);
                }
            }
        });
        return videoList;
    }

    public void setWeekList(List<String> weekList) {
        this.weekList = weekList;
    }

    public void setVideo(VideoDTO video) {
        this.video = video;
    }
}