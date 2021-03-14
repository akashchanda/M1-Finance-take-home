package com.example.technicalassessment.transactions.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.technicalassessment.R;

/**
 * This class represents an image of a check.
 */
public class CheckImageDialog extends Dialog {

    private CheckImageDialogInterface mListener;

    private Button mCloseButton;
    private ImageView mImage;
    private Bitmap mImageBitmap;

    public interface CheckImageDialogInterface {
        void onCloseButtonClicked();
    }

    public CheckImageDialog(@NonNull Context context, Bitmap checkImage,
                            CheckImageDialogInterface listener) {
        super(context);
        mImageBitmap = checkImage;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_view_dialog);

        mImage = findViewById(R.id.check_image);
        mImage.setImageBitmap(mImageBitmap);

        mCloseButton = findViewById(R.id.close);
        mCloseButton.setOnClickListener(v -> {
            dismiss();
            if(mListener != null)
                mListener.onCloseButtonClicked();
        });
    }
}
