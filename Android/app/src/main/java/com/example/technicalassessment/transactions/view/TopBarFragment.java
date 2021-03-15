package com.example.technicalassessment.transactions.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technicalassessment.R;
import com.example.technicalassessment.transactions.controller.TransactionsController;
import com.example.technicalassessment.transactions.controller.TransactionsControllerFactory;

/**
 * Top bar containing title and {@link ControlsView}.
 */
public class TopBarFragment extends Fragment {

    private static final String TAG = "TopBar";

    private TransactionsController mController;
    private TopBarFragmentInterface mListener;

    private Spinner mSortingOptionsDropdown;
    private String[] mSortingOptions;

    interface TopBarFragmentInterface {
        void onSortOptionSelected(TransactionsController.SORT_OPTION sortOption);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if(activity instanceof TransactionsView)
            mController = TransactionsControllerFactory.getInstance().getTransactionsController((TransactionsView) activity);
        else {
            Log.e(TAG, "fragment is not used inside an activity that implements TransactionsView");
            mController = null;
        }

        if(activity instanceof TopBarFragmentInterface) {
            mListener = (TopBarFragmentInterface) activity;
        }
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
        mSortingOptions = new String[] {
                getString(R.string.none),
                getString(R.string.sort_date_ascending),
                getString(R.string.sort_date_descending),
                getString(R.string.sort_amount_ascending),
                getString(R.string.sort_amount_descending)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, mSortingOptions);
        mSortingOptionsDropdown.setAdapter(adapter);
        mSortingOptionsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                TransactionsController.SORT_OPTION sortOptionChosen = getSortOptionFromString(selected);
                if(mListener != null)
                    mListener.onSortOptionSelected(sortOptionChosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    private TransactionsController.SORT_OPTION getSortOptionFromString(String string) {
        if(string == null || string.isEmpty())
            return TransactionsController.SORT_OPTION.NONE;
        int matchingOption = -1;
        for(int index = 0; index < mSortingOptions.length; index++) {
            if(mSortingOptions[index].equals(string)) {
                matchingOption = index;
                break;
            }
        }
        if(matchingOption == -1)
            return TransactionsController.SORT_OPTION.NONE;
        return TransactionsController.SORT_OPTION.values()[matchingOption];
    }
}
