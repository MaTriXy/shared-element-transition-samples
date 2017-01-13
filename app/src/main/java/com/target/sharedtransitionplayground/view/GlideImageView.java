package com.target.sharedtransitionplayground.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class GlideImageView extends ImageView {

    public interface LoadListener {

        void onImageLoaded(GlideImageView imageView);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GlideImageView(Context context) {
        super(context);
    }

    private LoadListener loadListener;
    private String url;

    private final SimpleTarget<GlideDrawable> loadTarget = new SimpleTarget<GlideDrawable>() {

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
            setImageDrawable(resource);
            if (loadListener != null) {
                loadListener.onImageLoaded(GlideImageView.this);
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setImageUrl(url, loadListener);
    }

    public void setImageUrl(@Nullable String url, @Nullable LoadListener loadListener) {
        this.url = url;
        this.loadListener = loadListener;

        if (url == null) {
            setImageDrawable(null);
            return;
        } else if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
            // Wait for measurement
            return;
        }

        Glide.with(getContext())
                .load(url)
                .crossFade()
                .into(loadTarget);
    }
}