package com.target.sharedtransitionplayground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.fragments.FragmentContainerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_activity_to_activity)
    public void didClickActivityToActivity() {
        startActivity(new Intent(this, SourceActivity.class));
    }

    @OnClick(R.id.btn_fragment_to_fragment)
    public void didClickFragmentToFragment() {
        startActivity(new Intent(this, FragmentContainerActivity.class));
    }
}