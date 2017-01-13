package com.target.sharedtransitionplayground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.adapters.MainAdapter;
import com.target.sharedtransitionplayground.view.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.target.sharedtransitionplayground.activities.TargetActivity.TRANSITION_VIEW_NAME;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SourceActivity extends AppCompatActivity implements MainAdapter.ClickListener {

    private Unbinder unbinder;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_list_activityfragment);
        unbinder = ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MainAdapter adapter = new MainAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onItemClick(int index, GlideImageView imageView) {
        String transitionName = ViewCompat.getTransitionName(imageView);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, transitionName);
        Intent intent = new Intent(this, TargetActivity.class)
                .putExtra(TRANSITION_VIEW_NAME, transitionName);
        ActivityCompat.startActivityForResult(this, intent, 24, options.toBundle());
    }
}
