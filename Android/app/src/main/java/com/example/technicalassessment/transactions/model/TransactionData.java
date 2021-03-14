package com.example.technicalassessment.transactions.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the data for a transaction.
 */
public class TransactionData {

    @SerializedName("id")
    protected String mId;
    @SerializedName("date")
    protected String mDate;
    @SerializedName("amount")
    protected float mAmount;
    @SerializedName("isCredit")
    protected boolean mIsCredit;
    @SerializedName("description")
    protected String mDescription;
    @SerializedName("imageUrl")
    protected String mImageUrl;

    public TransactionData() {
    }

    public TransactionData(String id, String date, float amount, boolean isCredit, String description, String imageUrl) {
        mId = id;
        mDate = date;
        mAmount = amount;
        mIsCredit = isCredit;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float amount) {
        mAmount = amount;
    }

    public boolean isCredit() {
        return mIsCredit;
    }

    public void setIsCredit(boolean isCredit) {
        mIsCredit = isCredit;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
