package com.example.technicalassessment.transactions.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technicalassessment.R;
import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * This fragment contains the transactions list.
 */
public class TransactionsListFragment extends Fragment {

    private static final String TAG = "TransactionsListFragment";

    private RecyclerView mRecyclerView;
    private TransactionListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransactionData[] mDataSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataSet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);
        view.setTag(TAG);

        mRecyclerView = view.findViewById(R.id.transactions_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TransactionListAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    /**
     * Initialize the transactions list.
     */
    private void initDataSet() {
        //TODO change to live data
        mDataSet = createFakeDataSet();
    }

    //TODO remove this method or move it to mock object
    private TransactionData[] createFakeDataSet() {
        TransactionData one = new TransactionData("0", "2020-11-16T08:10:12:001Z", 11.78f, true, "Check #1234", "SOME URL ENDPOINT FOR A CHECK IMAGE");
        TransactionData two = new TransactionData("0", "2020-11-18T08:08:12:001Z", 21.34f, false, "Kwik-E-Mart", "null");
        return new TransactionData[] {one, two};
    }

}
