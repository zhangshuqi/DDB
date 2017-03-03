package com.ljwj.ddb.taimian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 测量用户的适配器
 * Created by dell on 2017/1/7.
 */

public class MeasureAdapter extends BaseAdapter {

    private Context context;

    public MeasureAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
