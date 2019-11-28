package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.YoutubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class DashVidF extends Fragment implements View.OnClickListener {
    private TextView videoTitle;
    private TextView videoDescription;
    private Button returnButton;
    private Button markSeenButton;
    private YouTubePlayerView youTubePlayerView;

    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashvid, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*super.onViewCreated(view, savedInstanceState);
        videoTitle = getView().findViewById(R.id.videoTitle);
        videoDescription = getView().findViewById(R.id.videoDescription);
        returnButton = getView().findViewById(R.id.returnButton);
        markSeenButton = getView().findViewById(R.id.markSeenButton);
        youTubePlayerView = getView().findViewById(R.id.youtubePlayerView);

        //TODO this is just for testing. get video from intent
        VideoDTO videoDTO = new VideoDTO("Velkommen til nøglen", "AP7BdohPhMY", false);

        //TODO get video from intent
        YoutubePlayer youtubePlayer = new YoutubePlayer();
        youtubePlayer.initYoutubeVideo(videoDTO.getTitle(), youTubePlayerView);

        videoTitle.setText(videoDTO.getTitle());

        //TODO this is just for testing. get description value from video
        videoDescription.setText("Denne video giver dig en introduktion til hvad denne app handler om.");

        returnButton.setOnClickListener(this);*/



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        if (view == returnButton){
            iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }
    }
}
