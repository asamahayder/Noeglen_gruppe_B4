package com.example.noeglen.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class DashVidF extends Fragment implements View.OnClickListener {
    private TextView videoDescription;
    private Button returnButton;
    private Button markSeenButton;
    private VideoView videoView;

    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashvid, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = getView().findViewById(R.id.videoView);
        Uri videoURI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/noeglen-18170.appspot.com/o/velkommen%20til%20noeglen.mp4?alt=media&token=ae56401a-2a6c-440b-93c2-8200f02a7cfe");
        videoView.setVideoURI(videoURI);
        videoView.requestFocus();
        videoView.start();
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
