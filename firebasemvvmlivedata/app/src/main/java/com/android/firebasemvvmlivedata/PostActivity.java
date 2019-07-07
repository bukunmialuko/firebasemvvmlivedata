package com.android.firebasemvvmlivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFragment();

    }

    private void initializeFragment() {
        PostFragment postFragment =
                (PostFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (postFragment == null) {
            postFragment = PostFragment.newInstance();
            checkNotNull(getSupportFragmentManager());
            checkNotNull(postFragment);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, postFragment);
            transaction.commit();

        }
    }
}
