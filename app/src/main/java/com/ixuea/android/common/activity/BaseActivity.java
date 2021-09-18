package com.ixuea.android.common.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 19/9/2021.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Find all view.
     */
    protected void initView() {
    }

    /**
     * Set some style.
     */
    protected void initStyle() {
    }

    /**
     * Set data.
     */
    protected void initData() {

    }

    /**
     * Bind view listener.
     */
    protected void initListener() {
    }

    protected void init() {
        beforeInit();
        initView();
        initStyle();
        initData();
        initListener();
    }

    protected void beforeInit() {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }


    protected FragmentActivity getActivity() {
        return this;
    }


    public void toActivity(Class<?> clazz) {
        toActivity(new Intent(getActivity(), clazz));
    }

    public void toActivity(Intent intent) {
        startActivity(intent);
    }

    public void toActivityAfterFinishThis(Class<?> clazz) {
        toActivity(clazz);
        finish();
    }

    public void toActivityAfterFinishThis(Intent intent) {
        toActivity(intent);
        finish();
    }

    public void toActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


}