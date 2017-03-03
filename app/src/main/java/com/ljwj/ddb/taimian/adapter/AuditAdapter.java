package com.ljwj.ddb.taimian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 图纸复核适配器
 * Created by dell on 2017/1/13.
 */
public class AuditAdapter extends BaseAdapter {

    private final Context context;

    public AuditAdapter(Context context){
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
