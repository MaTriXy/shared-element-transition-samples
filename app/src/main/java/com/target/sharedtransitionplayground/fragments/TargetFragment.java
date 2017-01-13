package com.target.sharedtransitionplayground.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.util.ImageUtil;
import com.target.sharedtransitionplayground.view.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TargetFragment extends Fragment {

    private final static String TRANSITION_VIEW_NAME = "transition_name";

    public static TargetFragment create(String transitionName) {
        TargetFragment fragment = new TargetFragment();
        Bundle args = new Bundle();
        args.putString(TRANSITION_VIEW_NAME, transitionName);
        fragment.setArguments(args);
        return fragment;
    }

    private Unbinder unbinder;

    @BindView(R.id.image_view)
    GlideImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destination_detail_activityfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        ViewCompat.setTransitionName(imageView, getArguments().getString(TRANSITION_VIEW_NAME));
        displayData();
    }

    private void displayData() {
        final int imageViewSize = getResources().getDimensionPixelSize(R.dimen.image_detail_size);
        final String url = ImageUtil.getUrl(imageViewSize, 100);

        imageView.setImageUrl(url, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
