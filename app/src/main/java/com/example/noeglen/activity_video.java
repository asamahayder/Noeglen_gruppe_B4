package com.example.noeglen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.YoutubePlayer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

public class activity_video extends YouTubeBaseActivity implements View.OnClickListener {
    private TextView videoTitle;
    private TextView videoDescription;
    private Button returnButton;
    private Button markSeenButton;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoTitle = findViewById(R.id.videoTitle);
        videoDescription = findViewById(R.id.videoDescription);
        returnButton = findViewById(R.id.returnButton);
        markSeenButton = findViewById(R.id.markSeenButton);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);

        //TODO this is just for testing. get video from intent
        VideoDTO videoDTO = new VideoDTO("Velkommen til nøglen", "kyci1wyxpOc", false);

        //TODO get video from intent
        YoutubePlayer youtubePlayer = new YoutubePlayer();
        youtubePlayer.initYoutubeVideo(videoDTO.getVideoID(), youTubePlayerView);

        videoTitle.setText(videoDTO.getTitle());

        //TODO this is just for testing. get description value from video
        videoDescription.setText("Denne video giver dig en introduktion til hvad denne app handler om.");

        returnButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == returnButton){
            //iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }
    }
}
