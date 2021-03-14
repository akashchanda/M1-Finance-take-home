package com.example.technicalassessment.transactions.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technicalassessment.R;

/**
 * This activity is the main activity of the app. Its purpose is to encompass all UI elements
 * that enable a user to view and edit their financial transactions.
 */
public class TransactionsActivity extends AppCompatActivity implements TransactionsView {

    private static final String TAG = "TransactionsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
    }
}
