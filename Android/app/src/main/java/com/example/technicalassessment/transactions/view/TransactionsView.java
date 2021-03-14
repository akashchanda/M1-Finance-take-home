package com.example.technicalassessment.transactions.view;

import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * This interface provides methods that order changes to the transactions view.
 */
public interface TransactionsView {
    void openDetailedTransactionView(TransactionData transactionData, int transactionsListPosition);
}
