package com.example.technicalassessment.transactions.controller;

import android.app.Activity;

import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * This is the controller for transactions.
 */
public interface TransactionsController {

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

    void onTransactionClicked(TransactionData transactionData, int transactionsListPosition);
    void refreshTransactions();
    void sortTransactions(TransactionData[] transactions, SORT_OPTION sortOptionChosen);
    String getUserDescription(String transactionId, Activity context);
    void saveUserDescription(String transactionId, CharSequence userDescription, Activity context);
}

