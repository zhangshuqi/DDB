package com.ljwj.ddb.taimian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.ClientBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 临时用户的适配器
 * Created by dell on 2017/1/7.
 */

public class TemporaryAdapter extends BaseAdapter {
    private List<ClientBean> mList;
    private final String TAG="TemporaryAdapter";

    private Context context;
    private ViewHolder viewholder;

    public TemporaryAdapter(Context context, List<ClientBean> list) {
        this.context=context;
        mList=list;
    }

    @Override
    public int getCount() {
        //返回条目个数
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
        viewholder = null;
        if(convertView==null){
            viewholder=new ViewHolder();
            //没有缓存过对象
            LayoutInflater from = LayoutInflater.from(context);//获取布局填充器
            //填充所需要的布局进去
            convertView = from.inflate(R.layout.temporary_adapter, null);
            initView(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        initData(position,viewholder);
            return convertView;
    }

    private void initData(int position, ViewHolder viewholder) {

            ClientBean clientBean = mList.get(position );
            viewholder.sName.setText(clientBean.getName());
        String name = clientBean.getName();
        Log.i(TAG, "TemporaryAdapterinitData: 姓名："+name);

            viewholder.sDate.setText(clientBean.getDate());
        String sDate = clientBean.getDate();
        Log.i(TAG, "TemporaryAdapterinitData: 日期："+sDate);

            viewholder.relation.setText(clientBean.getRelation());
        String relation = clientBean.getRelation();
        Log.i(TAG, "TemporaryAdapterinitData: 类别关系："+relation);

            viewholder.phone.setText(clientBean.getPhone());
        String phone = clientBean.getPhone();
        Log.i(TAG, "TemporaryAdapterinitData: 电话："+phone);

            viewholder.sSite.setText(clientBean.getSite());
        String sSite = clientBean.getSite();
        Log.i(TAG, "TemporaryAdapterinitData: 地址："+sSite);

            viewholder.sSex.setText(clientBean.getSex());
        String sSex = clientBean.getSex();
        Log.i(TAG, "TemporaryAdapterinitData: 性别："+sSex);

        int state = clientBean.getState();
        if(state==0){
            viewholder.sState.setText("临时客户");
        }else if (state==1){
            viewholder.sState.setText("测量客户");
        }else if (state==2){
            viewholder.sState.setText("设计阶段");
        }
    }

    private void initView(View view) {
        viewholder.ivHead = (ImageView) view.findViewById(R.id.temporary_head_iv); //头像
        viewholder.sName= (TextView) view.findViewById(R.id.temporary_name_tv); //姓名
        viewholder.relation =(TextView)view.findViewById(R.id.temporary_relation_tv); //类别
        viewholder.phone=(TextView)view.findViewById(R.id.temporary_phone_tv); //电话
        viewholder.sSite = (TextView) view.findViewById(R.id.temporary_site_tv); //地址
        viewholder.sDate = (TextView) view.findViewById(R.id.temporary_date_tv); //日期
        viewholder.sState = (TextView) view.findViewById(R.id.temporary_state_tv);//状态
        viewholder.sSex = (TextView) view.findViewById(R.id.temporary_sex_tv);//性别
        viewholder.sUserId = (TextView) view.findViewById(R.id.temporary_userid_tv);//用户ID
    }

    public class ViewHolder {
        public ImageView ivHead;
        public TextView sName;
        public TextView relation;
        public TextView sSex;
        public TextView phone;
        public TextView sDate;
        public TextView sSite;
        public TextView sState;
        public TextView sUserId;
    }
}
