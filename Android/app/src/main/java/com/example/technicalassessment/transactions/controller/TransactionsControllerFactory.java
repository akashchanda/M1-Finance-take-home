package com.example.technicalassessment.transactions.controller;

import androidx.annotation.NonNull;

import com.example.technicalassessment.transactions.view.TransactionsView;

import java.util.HashMap;

public class TransactionsControllerFactory {
    private static TransactionsControllerFactory sInstance = new TransactionsControllerFactory();

    private final HashMap<TransactionsView, TransactionsController> mControllers;

    private TransactionsControllerFactory() {
        mControllers = new HashMap<>();
    }

    public static TransactionsControllerFactory getInstance() {
        if(sInstance == null)
            sInstance = new TransactionsControllerFactory();
        return sInstance;
    }
    public TransactionsController getTransactionsController(@NonNull TransactionsView viewInterface) {
        if(mControllers.containsKey(viewInterface))
            return mControllers.get(viewInterface);
        TransactionsControllerImpl controller = new TransactionsControllerImpl(viewInterface);
        mControllers.put(viewInterface, controller);
        return controller;
    }
}
