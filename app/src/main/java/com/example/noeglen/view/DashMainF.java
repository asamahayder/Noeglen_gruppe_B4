package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DashMainF extends Fragment implements View.OnClickListener {

    private IMainActivity iMain;
    private ImageView iVidDash;
    private TextView tVidDash1, tVidDash2;
    private String fragmentTag;

    private static final String TAG = "DASHMAIN";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashmain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        iVidDash = getView().findViewById(R.id.iDashVid);
        iVidDash.setOnClickListener(this);



        tVidDash1 = getView().findViewById(R.id.tDashVid1);
        tVidDash1 = getView().findViewById(R.id.tDashVid2);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iDashVid:
                fragmentTag = getString(R.string.fragment_dashvidmain);
                break;
        }
        iMain.inflateFragment(fragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}
