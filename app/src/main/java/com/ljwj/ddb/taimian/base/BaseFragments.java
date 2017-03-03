package com.ljwj.ddb.taimian.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by A chang on 2017/1/15.
 */

public  abstract class BaseFragments  extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), null);
        initView(view);
        initListener();
        initData();
        initRefresh();
        return view;
    }

    protected abstract void initListener();

    protected abstract void initRefresh();

    protected abstract void initData();

    protected abstract void initView(View view);

    public abstract int getLayoutId();

}
