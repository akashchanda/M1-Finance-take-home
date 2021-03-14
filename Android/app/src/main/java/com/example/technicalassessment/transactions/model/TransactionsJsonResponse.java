package com.example.technicalassessment.transactions.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing the JSON response from the endpoint with all the transactions.
 */
public class TransactionsJsonResponse {
    @SerializedName("transactions")
    TransactionData[] mTransactions;

    public TransactionData[] getTransactions() {
        return mTransactions;
    }
}
