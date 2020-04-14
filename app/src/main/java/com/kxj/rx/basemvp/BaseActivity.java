package com.kxj.rx.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initLayout(@Nullable Bundle savedInstanceState);

    protected void initViews() {
    }

    protected abstract void initData();


    @SuppressWarnings("SameParameterValue")
    protected <T extends View> T $(@IdRes int viewId) {
        return findViewById(viewId);
    }

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout(savedInstanceState);
        initData();
        initViews();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
