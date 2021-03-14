package com.example.technicalassessment.transactions.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.technicalassessment.R;

/**
 * This class represents a dialog which shows the details of a transaction.
 */
class DetailedTransactionDialog extends Dialog {
    private TextView mDescription;
    private TextView mUserDescription;
    private TextView mAmount;
    private TextView mDate;
    private Button mCloseButton;
    private final String mStartingDescription;
    private final String mStartingUserDescription;
    private final float mStartingAmount;
    private final String mStartingDate;

    private DetailedTransactionDialogInterface mListener;

    interface DetailedTransactionDialogInterface {
        void onCloseButtonClicked();
        void onUserDescriptionChanged(); //TODO call only when out of focus
    }

    DetailedTransactionDialog(@NonNull Context context, String description, String userDescription,
                              float amount, String date, DetailedTransactionDialogInterface listener) {
        super(context);
        mListener = listener;
        mStartingDescription = description;
        mStartingUserDescription = userDescription;
        mStartingAmount = amount;
        mStartingDate = date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detailed_transaction_dialog);
        mDescription = findViewById(R.id.description);
        mUserDescription = findViewById(R.id.user_description);
        mAmount = findViewById(R.id.amount);
        mDate = findViewById(R.id.date);

        initFields();

        mCloseButton = findViewById(R.id.close);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(mListener != null)
                    mListener.onCloseButtonClicked();
            }
        });

        //TODO text changed logic and call onUserDescChanged
    }

    private void initFields() {
        if(mStartingDescription != null)
            mDescription.setText(mStartingDescription);
        if(mStartingUserDescription != null)
            mUserDescription.setText(mStartingUserDescription);
        mAmount.setText(String.format(getContext().getString(R.string.amount_format), mStartingAmount));
        if(mStartingDate != null)
            mDate.setText(mStartingDate);
    }
}
