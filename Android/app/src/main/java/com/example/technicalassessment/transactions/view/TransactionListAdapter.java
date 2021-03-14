package com.example.technicalassessment.transactions.view;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technicalassessment.R;
import com.example.technicalassessment.transactions.model.TransactionData;

/**
 * Adapter for the transactions list.
 */
public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private static final String TAG = "TransactionListAdapter";

    private TransactionData[] mDataSet;
    private final OnTransactionClickListener mTransactionClickListener;

    public TransactionListAdapter(TransactionData[] dataSet, OnTransactionClickListener listener) {
        mDataSet = dataSet;
        mTransactionClickListener = listener;
    }

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionData transactionData, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mDescription;
        private final TextView mDate;
        private final TextView mAmount;
        private final OnTransactionClickListener mTransactionClickListener;

        public ViewHolder(@NonNull View itemView, OnTransactionClickListener transactionClickListener) {
            super(itemView);

            mDescription = itemView.findViewById(R.id.transaction_description);
            mDate = itemView.findViewById(R.id.transaction_date);
            mAmount = itemView.findViewById(R.id.transaction_amount);
            mTransactionClickListener = transactionClickListener;
        }

        public TextView getDescriptionTextView() {
            return mDescription;
        }

        public TextView getDateTextView() {
            return mDate;
        }

        public TextView getAmountTextView() {
            return mAmount;
        }

        public OnTransactionClickListener getTransactionClickListener() {
            return mTransactionClickListener;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_row_item, parent, false);
        return new ViewHolder(view, mTransactionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        setViewHolderDescriptionField(holder, position);
        setViewHolderDateField(holder, position);
        setViewHolderAmountField(holder, position);
        final OnTransactionClickListener listener = holder.getTransactionClickListener();
        if(listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onTransactionClick(mDataSet[position], position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public void setDataSet(TransactionData[] dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    /**
     * Set the description field of the {@link ViewHolder} at the given position.
     * @param holder The {@link ViewHolder} whose field to change.
     * @param position The position of the data in the {@link TransactionListAdapter#mDataSet}.
     */
    private void setViewHolderDescriptionField(@NonNull ViewHolder holder, int position) {
        String description;
        try {
            description = getDescription(position);
        }
        catch(IllegalArgumentException e) {
            Log.w(TAG, "couldn't get description for position " + position);
            description = "";
        }
        holder.getDescriptionTextView().setText(description);
    }

    /**
     * Set the date field of the {@link ViewHolder} at the given position.
     * @param holder The {@link ViewHolder} whose field to change.
     * @param position The position of the data in the {@link TransactionListAdapter#mDataSet}.
     */
    private void setViewHolderDateField(@NonNull ViewHolder holder, int position) {
        String date;
        try {
            date = getDate(position);
        }
        catch(IllegalArgumentException e) {
            Log.w(TAG, "couldn't get date for position " + position);
            date = "";
        }
        holder.getDateTextView().setText(date);
    }

    /**
     * Set the amount field of the {@link ViewHolder} at the given position.
     * @param holder The {@link ViewHolder} whose field to change.
     * @param position The position of the data in the {@link TransactionListAdapter#mDataSet}.
     */
    private void setViewHolderAmountField(@NonNull ViewHolder holder, int position) {
        float amount;
        try {
            amount = getAmount(position);
        }
        catch(IllegalArgumentException e) {
            Log.w(TAG, "couldn't get amount for position " + position);
            amount = 0f;
        }
        String format = holder.itemView.getContext().getString(R.string.amount_format);
        holder.getAmountTextView().setText(String.format(format, amount));
    }

    /**
     * @param position The transaction's position in the {@link TransactionListAdapter#mDataSet}.
     * @return The description for the transaction at the given position.
     */
    private String getDescription(int position) {
        if(position < 0 || position >= mDataSet.length)
            throw new IllegalArgumentException("invalid position");
        return mDataSet[position].getDescription();
    }

    /**
     * @param position The transaction's position in the {@link TransactionListAdapter#mDataSet}.
     * @return The date for the transaction at the given position.
     */
    private String getDate(int position) {
        if(position < 0 || position >= mDataSet.length)
            throw new IllegalArgumentException("invalid position");
        return mDataSet[position].getDate();
    }

    /**
     * @param position The transaction's position in the {@link TransactionListAdapter#mDataSet}.
     * @return The amount for the transaction at the given position.
     */
    private float getAmount(int position) {
        if(position < 0 || position >= mDataSet.length)
            throw new IllegalArgumentException("invalid position");
        return mDataSet[position].getAmount();
    }

}
