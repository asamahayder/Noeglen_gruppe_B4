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
import androidx.fragment.app.FragmentTransaction;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.YoutubePlayer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

public class DashVidF extends Fragment implements View.OnClickListener {
    private TextView videoTitle;
    private TextView videoDescription;
    private Button returnButton;
    private Button markSeenButton;
    //private YouTubePlayerView youTubePlayerView;

    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youTubePlayerFragment.initialize("AIzaSyB1ZHv40LuyAjJ7ygFNU7ImVVEUTTsf0uw", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo("kyci1wyxpOc");
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,youTubePlayerFragment).commit();
         */
        return inflater.inflate(R.layout.fragment_dashvid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoTitle = getView().findViewById(R.id.videoTitle);
        videoDescription = getView().findViewById(R.id.videoDescription);
        returnButton = getView().findViewById(R.id.returnButton);
        markSeenButton = getView().findViewById(R.id.markSeenButton);
        //youTubePlayerView = getView().findViewById(R.id.youtubePlayerView);

        //TODO this is just for testing. get video from intent
        VideoDTO videoDTO = new VideoDTO("Velkommen til nøglen", "kyci1wyxpOc", false);

        //TODO get video from intent
        //YoutubePlayer youtubePlayer = new YoutubePlayer();
        //youtubePlayer.initYoutubeVideo(videoDTO.getTitle(), youTubePlayerView);

        videoTitle.setText(videoDTO.getTitle());

        //TODO this is just for testing. get description value from video
        videoDescription.setText("Denne video giver dig en introduktion til hvad denne app handler om.");

        returnButton.setOnClickListener(this);



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
