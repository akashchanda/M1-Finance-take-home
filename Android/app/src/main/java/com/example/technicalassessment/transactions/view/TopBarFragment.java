package com.example.technicalassessment.transactions.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technicalassessment.R;

/**
 * Top bar containing title and {@link ControlsView}.
 */
public class TopBarFragment extends Fragment {

    private static final String TAG = "TopBar";

    private Spinner mSortingOptionsDropdown;

    enum SORT_OPTION {
        NONE,
        DATE_ASCENDING,
        DATE_DESCENDING,
        AMOUNT_ASCENDING,
        AMOUNT_DESCENDING;

        static SORT_OPTION getDefault() {
            return NONE;
        }
    }

    interface TopBarFragmentInterface {
        void onSortOptionSelected(SORT_OPTION sortOption);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_bar, container, false);
        view.setTag(TAG);

        initSortingOptionsDropdown(view);

        return view;
    }

    private void initSortingOptionsDropdown(View view) {
        mSortingOptionsDropdown = view.findViewById(R.id.sorting_options);
        String[] sortingOptions = {
                getString(R.string.sort_date_ascending),
                getString(R.string.sort_date_descending),
                getString(R.string.sort_amount_ascending),
                getString(R.string.sort_amount_descending)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, sortingOptions);
        mSortingOptionsDropdown.setAdapter(adapter);
    }
}
