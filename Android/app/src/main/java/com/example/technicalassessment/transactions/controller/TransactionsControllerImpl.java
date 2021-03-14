package com.example.technicalassessment.transactions.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.technicalassessment.transactions.model.TransactionData;
import com.example.technicalassessment.transactions.view.TransactionsView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This is the default implementation of {@link TransactionsController}.
 */
class TransactionsControllerImpl implements TransactionsController {

    private static final String TAG = "TransactionsControllerImpl";

    private final TransactionsView mViewInterface;

    TransactionsControllerImpl(@NonNull TransactionsView viewInterface) {
        mViewInterface = viewInterface;
    }

    @Override
    public void onTransactionClicked(TransactionData transactionData, int transactionsListPosition) {
        if(transactionData.isCredit() && transactionData.getImageUrl() != null) {
            AsyncImageDownloadTask imageDownloadTask = new AsyncImageDownloadTask(transactionData,
                    transactionsListPosition, transactionData.getImageUrl());
            imageDownloadTask.execute();
        }
        else if(!transactionData.isCredit())
            mViewInterface.openDetailedTransactionView(transactionData, transactionsListPosition);
    }



    class AsyncImageDownloadTask extends AsyncTask<Void, Void, Void> {

        private TransactionData mTransactionData;
        private int mTransactionsListPosition;
        private String mImageUrl;

        private AsyncImageDownloadTask(TransactionData transactionData, int transactionsListPosition,
                                       String imageUrl) {
            mTransactionData = transactionData;
            mTransactionsListPosition = transactionsListPosition;
            mImageUrl = imageUrl;
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
}
