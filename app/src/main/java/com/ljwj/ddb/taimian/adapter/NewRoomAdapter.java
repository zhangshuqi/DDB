package com.ljwj.ddb.taimian.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.NewRoomBean;

import java.util.ArrayList;

/**
 * Created by dell on 2017/3/1.
 */

public class NewRoomAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<NewRoomBean> mList;
    private ViewHolder viewHolder;

    public NewRoomAdapter(Context context,ArrayList<NewRoomBean> list) {
        mContext =context;
        mList=list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            //没有缓存过对象
            LayoutInflater from = LayoutInflater.from(mContext);//获取布局填充器
            //填充所需要的布局进去
            convertView = from.inflate(R.layout.newroom_adapter, null);
            initView(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initData(position,viewHolder);
        return convertView;
    }

    private void initView(View view) {

        viewHolder.sTitle = (TextView) view.findViewById(R.id.newroom_kitchen_tv); //标题

    }

    private void initData(int position, ViewHolder viewholder) {

        NewRoomBean nrBean = mList.get(position);
        viewholder.sTitle.setText(nrBean.getmTitle());

    }

    public class ViewHolder {
        public TextView sTitle;

    }

}
