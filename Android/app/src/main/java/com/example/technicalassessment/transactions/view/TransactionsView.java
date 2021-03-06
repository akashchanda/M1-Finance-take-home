package com.example.technicalassessment.transactions.view;

import android.graphics.Bitmap;

import com.example.technicalassessment.transactions.model.TransactionData;
import com.example.technicalassessment.transactions.model.TransactionsJsonResponse;

/**
 * This interface provides methods that order changes to the transactions view.
 */
public interface TransactionsView {
    void openDetailedTransactionView(TransactionData transactionData, int transactionsListPosition);
    void openCheckView(TransactionData transactionData, int transactionsListPosition, Bitmap checkImage);
    void updateTransactionsList(TransactionData[] transactionsData);
}
