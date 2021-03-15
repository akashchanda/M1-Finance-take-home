package com.example.technicalassessment.transactions.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technicalassessment.R;
import com.example.technicalassessment.transactions.controller.TransactionsController;
import com.example.technicalassessment.transactions.controller.TransactionsControllerFactory;
import com.example.technicalassessment.transactions.model.CheckImageDialog;
import com.example.technicalassessment.transactions.model.TransactionData;

import java.util.Objects;

/**
 * This fragment contains the transactions list.
 */
public class TransactionsListFragment extends Fragment implements TransactionListAdapter.OnTransactionClickListener {

    private static final String TAG = "TransactionsListFragment";

    private TransactionsController mController;

    private RecyclerView mRecyclerView;
    private TransactionListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransactionData[] mDataSet = new TransactionData[0];

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
    }

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
        mAdapter = new TransactionListAdapter(mDataSet, this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.refreshTransactions();
    }

    void openDetailedTransactionView(TransactionData transactionData, int transactionsListPosition) {
        String userDescription = mController.getUserDescription(transactionData.getId(), getActivity());
        DetailedTransactionDialog detailedTransactionDialog = new DetailedTransactionDialog(
                Objects.requireNonNull(getContext()), transactionData.getDescription(),
                userDescription, transactionData.getAmount(), transactionData.getDateReadable(),
                new DetailedTransactionDialog.DetailedTransactionDialogInterface() {
            @Override
            public void onCloseButtonClicked() {
                //do nothing
            }

            @Override
            public void onUserDescriptionChanged(CharSequence charSequence) {
                mController.saveUserDescription(transactionData.getId(), charSequence, getActivity());
            }
        });
        detailedTransactionDialog.show();
    }

    void openCheckView(TransactionData transactionData, int transactionsListPosition, Bitmap checkImage) {
        Activity activity = getActivity();
        if(activity != null)
            activity.runOnUiThread(() -> {
                final CheckImageDialog checkImageDialog = new CheckImageDialog(Objects.requireNonNull(getContext()),
                        checkImage,
                        new CheckImageDialog.CheckImageDialogInterface() {
                            @Override
                            public void onCloseButtonClicked() {
                                //do nothing
                            }
                        });
                checkImageDialog.show();
            });
        else
            Log.w(TAG, "activity is null");
    }

    void updateTransactionsList(TransactionData[] transactionsData) {
        Activity activity = getActivity();
        if(activity != null)
            activity.runOnUiThread(() -> {
                mDataSet = transactionsData;
                mAdapter.setDataSet(mDataSet);
            });
        else
            Log.w(TAG, "activity is null");
    }

    /**
     * Initialize the transactions list.
     */
    private void initDataSet() {
        mController.refreshTransactions();
    }

    @Override
    public void onTransactionClick(TransactionData transactionData, int position) {
        mController.onTransactionClicked(transactionData, position);
    }

    public TransactionData[] getDataSet() {
        return mDataSet;
    }
}
