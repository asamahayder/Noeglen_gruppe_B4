package com.example.noeglen.logic;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class YoutubePlayer {

    private String devKey = "AIzaSyB1ZHv40LuyAjJ7ygFNU7ImVVEUTTsf0uw";

    public YoutubePlayer() {
    }

    public void initYoutubeVideo(final String videoID, YouTubePlayerView youTubePlayerView){
        youTubePlayerView.initialize(devKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                System.out.println("something went wrong with the initialization of the youtube video player");
            }
        });
    }

    public void initVideoThumbNail(final String videoID, YouTubeThumbnailView youTubeThumbnailView){
        youTubeThumbnailView.initialize(devKey, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                System.out.println("youtube thumbnail initialized succesfully");
                youTubeThumbnailLoader.setVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                System.out.println("something went wrong in the initialization of the youtube thumbnail");
            }
        });
    }
}
