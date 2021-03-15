package com.example.technicalassessment.transactions.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.technicalassessment.transactions.model.TransactionData;
import com.example.technicalassessment.transactions.model.TransactionsJsonResponse;
import com.example.technicalassessment.transactions.view.TransactionsView;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * This is the default implementation of {@link TransactionsController}.
 */
class TransactionsControllerImpl implements TransactionsController {

    private static final String TAG = "TransactionsControllerImpl";
    private static final String TRANSACTIONS_DATA_ENDPOINT = "https://m1-technical-assessment-data.netlify.app/transactions-v1.json";

    private final TransactionsView mViewInterface;

    TransactionsControllerImpl(@NonNull TransactionsView viewInterface) {
        mViewInterface = viewInterface;
    }

    @Override
    public void onTransactionClicked(TransactionData transactionData, int transactionsListPosition) {
        if(transactionData.isCredit() && transactionData.getImageUrl() != null) {
            AsyncImageDownloadTask imageDownloadTask = new AsyncImageDownloadTask(transactionData,
                    transactionsListPosition, mViewInterface);
            imageDownloadTask.execute();
        }
        else if(!transactionData.isCredit())
            mViewInterface.openDetailedTransactionView(transactionData, transactionsListPosition);
    }

    @Override
    public void refreshTransactions() {
        new AsyncTransactionsDataDownloadTask(mViewInterface).execute();
    }

    static class AsyncImageDownloadTask extends AsyncTask<Void, Void, Void> {

        private TransactionData mTransactionData;
        private int mTransactionsListPosition;
        private TransactionsView mViewInterface;

        private AsyncImageDownloadTask(TransactionData transactionData, int transactionsListPosition,
                                       TransactionsView viewInterface) {
            mTransactionData = transactionData;
            mTransactionsListPosition = transactionsListPosition;
            mViewInterface = viewInterface;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Bitmap imageBitmap = getImageBitmap(mTransactionData.getImageUrl());
                mViewInterface.openCheckView(mTransactionData, mTransactionsListPosition, imageBitmap);
            }
            catch(Exception e) {
                Log.w(TAG, "couldn't get bitmap for given image: " + mTransactionData.getImageUrl() + " message: " + e);
            }
            return null;
        }

        private Bitmap getImageBitmap(String imageUrl) throws IOException {
            Bitmap bitmap = null;
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            try (InputStream is = connection.getInputStream();
                 BufferedInputStream bis = new BufferedInputStream(is)){
                bitmap = BitmapFactory.decodeStream(bis);
                return bitmap;
            }
        }
    }

    static class AsyncTransactionsDataDownloadTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "AsyncTransactionsDataDownloadTask";

        private TransactionsView mViewInterface;

        private AsyncTransactionsDataDownloadTask(TransactionsView viewInterface) {
            mViewInterface = viewInterface;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                /*
                REST getter implementation from https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
                 */
                URL endpoint = new URL(TRANSACTIONS_DATA_ENDPOINT);
                HttpsURLConnection myConnection = (HttpsURLConnection) endpoint.openConnection();
                if(myConnection.getResponseCode() == 200) {
                    StringBuilder responseBuffer = new StringBuilder();
                    try(InputStream responseBody = myConnection.getInputStream();
                        BufferedReader responseBodyReader = new BufferedReader(
                                new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
                        String responseLine;
                        while((responseLine = responseBodyReader.readLine()) != null) {
                            responseBuffer.append(responseLine);
                        }
                    }

                    //TODO remove
                    Log.d(TAG, responseBuffer.toString());

                    Gson gson = new Gson();
                    TransactionsJsonResponse transactionsData = gson.fromJson(responseBuffer.toString(), TransactionsJsonResponse.class);

                    myConnection.disconnect();

                    mViewInterface.updateTransactionsList(transactionsData);

                    return null;
                }
                else {
                    Log.w(TAG, "couldn't get latest transactions data. Https response invalid: " +
                            myConnection.getResponseCode());
                    return null;
                }
            }
            catch(Exception e) {
                Log.w(TAG, "couldn't get latest transactions data. Error message: " + e.getMessage());
            }

            return null;
        }
    }
}
