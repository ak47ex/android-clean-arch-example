package com.suenara.exampleapp.presentation.view.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class AutoLoadImageView extends ImageView {

    private static final String BASE_IMG_NAME = "image_";

    private String imageUrl = null;
    private int imagePlaceHolderResId = -1;

    public AutoLoadImageView(Context context) {
        super(context);
    }

    public AutoLoadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLoadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.imagePlaceHolderResId = imagePlaceHolderResId;
        savedState.imageUrl = imageUrl;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        imagePlaceHolderResId = savedState.imagePlaceHolderResId;
        imageUrl = savedState.imageUrl;
        setImageUrl(imageUrl);
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;

        if (imageUrl != null) {
            loadImageFromUrl(imageUrl);
        } else {
            loadImagePlaceHolder();
        }
    }

    private void loadImageFromUrl(final String imageUrl) {
        RequestCreator requestCreator = Picasso.get().load(imageUrl);
        if (imagePlaceHolderResId != -1) {
            requestCreator.placeholder(imagePlaceHolderResId);
        }
        requestCreator.fit().centerInside().into(this);
    }

    private void loadBitmap(final Bitmap bitmap) {
        ((Activity)getContext()).runOnUiThread(() -> {
            AutoLoadImageView.this.setImageBitmap(bitmap);
        });
    }

    private void loadImagePlaceHolder() {
        if (imagePlaceHolderResId == -1) {
            return;
        }

        ((Activity)getContext()).runOnUiThread(() -> {
            AutoLoadImageView.this.setImageResource(AutoLoadImageView.this.imagePlaceHolderResId);
        });
    }

    private static class SavedState extends BaseSavedState {
        int imagePlaceHolderResId;
        String imageUrl;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.imagePlaceHolderResId = in.readInt();
            this.imageUrl = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(imagePlaceHolderResId);
            out.writeString(imageUrl);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
