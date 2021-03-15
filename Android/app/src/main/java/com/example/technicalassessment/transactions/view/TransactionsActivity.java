package com.example.technicalassessment.transactions.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technicalassessment.R;
import com.example.technicalassessment.transactions.controller.TransactionsController;
import com.example.technicalassessment.transactions.controller.TransactionsControllerFactory;
import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * This activity is the main activity of the app. Its purpose is to encompass all UI elements
 * that enable a user to view and edit their financial transactions.
 */
public class TransactionsActivity extends AppCompatActivity implements TransactionsView, TopBarFragment.TopBarFragmentInterface {

    private static final String TAG = "TransactionsActivity";

    TransactionsController mController;
    TransactionsListFragment mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mController = TransactionsControllerFactory.getInstance().getTransactionsController(TransactionsActivity.this);
        mListFragment = (TransactionsListFragment) getSupportFragmentManager().findFragmentById(R.id.transaction_fragment);
    }

    @Override
    public void openDetailedTransactionView(TransactionData transactionData, int transactionsListPosition) {
        mListFragment.openDetailedTransactionView(transactionData, transactionsListPosition);
    }

    @Override
    public void openCheckView(TransactionData transactionData, int transactionsListPosition, Bitmap checkImage) {
        mListFragment.openCheckView(transactionData, transactionsListPosition, checkImage);
    }

    @Override
    public void updateTransactionsList(TransactionData[] transactionsData) {
        mListFragment.updateTransactionsList(transactionsData);
    }

    @Override
    public void onSortOptionSelected(TransactionsController.SORT_OPTION sortOption) {
        TransactionData[] transactionsData = mListFragment.getDataSet();
        mController.sortTransactions(transactionsData, sortOption);
    }
}
