package com.example.technicalassessment.transactions.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class represents the data for a transaction.
 */
public class TransactionData {

    private static final String TAG = "TransactionData";

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

    public Date getDate() {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getDefault());
        try {
            return format.parse(mDate);
        }
        catch(ParseException e) {
            Log.w(TAG, "couldn't parse date; " + e.getMessage());
            return null;
        }
    }

    public String getDateReadable() {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getDefault());
        try {
            Date date = format.parse(mDate);
            if(date == null) {
                Log.w(TAG, "null date parsed");
                return "";
            }
            String pattern = "MM/dd/yyyy";
            SimpleDateFormat requiredFormat = new SimpleDateFormat(pattern, Locale.US);
            return requiredFormat.format(date);
        }
        catch(ParseException e) {
            Log.w(TAG, "couldn't parse date; " + e.getMessage());
            return "";
        }
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

    public static class TransactionDateComparator implements Comparator<TransactionData> {
        @Override
        public int compare(TransactionData transactionData1, TransactionData transactionData2) {
            Date date1 = transactionData1.getDate();
            Date date2 = transactionData2.getDate();
            if(date1 == null || date2 == null) {
                Log.w(TAG, "error getting date objects during comparator operation");
                return 0;
            }
            return date1.compareTo(date2);
        }
    }

    public static class TransactionAmountComparator implements Comparator<TransactionData> {
        @Override
        public int compare(TransactionData transactionData1, TransactionData transactionData2) {
            Float float1 = transactionData1.getAmount();
            if(!transactionData1.isCredit())
                float1 *= -1f;
            Float float2 = transactionData2.getAmount();
            if(!transactionData2.isCredit())
                float2 *= -1f;
            return float1.compareTo(float2);
        }
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "mId='" + mId + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mAmount=" + mAmount +
                ", mIsCredit=" + mIsCredit +
                ", mDescription='" + mDescription + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
