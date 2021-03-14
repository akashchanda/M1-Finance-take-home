package com.example.technicalassessment.transactions.controller;

import androidx.annotation.NonNull;

import com.example.technicalassessment.transactions.model.TransactionData;
import com.example.technicalassessment.transactions.view.TransactionsView;

/**
 * This is the default implementation of {@link TransactionsController}.
 */
class TransactionsControllerImpl implements TransactionsController {

    private final TransactionsView mViewInterface;

    TransactionsControllerImpl(@NonNull TransactionsView viewInterface) {
        mViewInterface = viewInterface;
    }

    @Override
    public void onTransactionClicked(TransactionData transactionData, int transactionsListPosition) {
        mViewInterface.openDetailedTransactionView(transactionData, transactionsListPosition);
    }
}
