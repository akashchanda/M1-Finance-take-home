package com.example.technicalassessment.transactions.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technicalassessment.R;

/**
 * Top bar containing title and {@link ControlsView}.
 */
public class TopBarFragment extends Fragment {

    private static final String TAG = "TopBar";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_bar, container, false);
        view.setTag(TAG);

        //TODO get controls view

        return view;
    }
}
