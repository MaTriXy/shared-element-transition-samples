package com.target.sharedtransitionplayground.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.adapters.MainAdapter;
import com.target.sharedtransitionplayground.util.DetailsTransition;
import com.target.sharedtransitionplayground.view.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SourceFragment extends Fragment implements MainAdapter.ClickListener {

    private Unbinder unbinder;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.source_list_activityfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        Context context = getActivity();
        MainAdapter adapter = new MainAdapter(context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int index, GlideImageView imageView) {
        showTargetFragment(imageView);
    }

    public void showTargetFragment(GlideImageView forImageView) {
        String transitionName = ViewCompat.getTransitionName(forImageView);
        TargetFragment fragment = TargetFragment.create(transitionName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showTargetFragmentLollipop(fragment, forImageView);
            return;
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showTargetFragmentLollipop(TargetFragment fragment, GlideImageView forImageView) {
        fragment.setSharedElementEnterTransition(new DetailsTransition());
        fragment.setEnterTransition(new Fade());
        setExitTransition(new Fade());
        fragment.setSharedElementReturnTransition(new DetailsTransition());

        String transitionName = ViewCompat.getTransitionName(forImageView);
        getFragmentManager()
                .beginTransaction()
                .addSharedElement(forImageView, transitionName)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
