package com.example.noeglen.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * Main aktivitetens interface der håndterer når man skal ændre fra et fragment til et andet
 */

public interface IMainActivity {

    void inflateFragment(String tag);
    void inflateFragment(String tag, boolean addToBackStack);
    void setFragment(Fragment f, String tag, boolean addToBackStack, Bundle bundle);
}
