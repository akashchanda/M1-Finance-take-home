package com.example.technicalassessment.transactions.controller;

import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * This is the controller for transactions.
 */
public interface TransactionsController {

    void onTransactionClicked(TransactionData transactionData, int transactionsListPosition);
}

