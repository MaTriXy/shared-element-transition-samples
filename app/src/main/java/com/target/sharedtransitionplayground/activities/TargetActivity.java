package com.target.sharedtransitionplayground.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.util.ImageUtil;
import com.target.sharedtransitionplayground.view.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TargetActivity extends AppCompatActivity {

    public final static String TRANSITION_VIEW_NAME = "transition_name";

    private Unbinder unbinder;

    @BindView(R.id.image_view)
    GlideImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_detail_activityfragment);
        unbinder = ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Match the detail image view transition name with the original
        ViewCompat.setTransitionName(imageView,
                getIntent().getStringExtra(TRANSITION_VIEW_NAME));

        // Postpone the transition until the detail image thumbnail is loaded
        ActivityCompat.postponeEnterTransition(this);

        // Load the detail thumbnail via Glide
        displayData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            unbinder.unbind();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayData() {
        final int imageViewSize = getResources().getDimensionPixelSize(R.dimen.image_detail_size);
        final String url = ImageUtil.getUrl(imageViewSize, 100);

        imageView.setImageUrl(url, new GlideImageView.LoadListener() {
            @Override
            public void onImageLoaded(GlideImageView imageView) {
                // Finish the transition now that the image is loaded
                scheduleStartPostponedTransition(imageView);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scheduleStartPostponedTransition(final View sharedElement) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }
}
