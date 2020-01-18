package com.example.noeglen.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface IMainActivity {

    void inflateFragment(String tag);
    void inflateFragment(String tag, boolean addToBackStack);
    void setFragment(Fragment f, String tag, boolean addToBackStack, Bundle bundle);
    void visibilityGone();
    void visibilityShow();
}
