package com.example.noeglen.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface IMainActivity {

    void inflateFragment(String tag);
    void setFragment(Fragment f, String tag, boolean addToBackStack, Bundle bundle);
}
