package com.ljwj.ddb.taimian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.base.BaseFragments;

/**
 * 台面之家页面
 * Created by dell on 2016/12/21.
 */

public class Main_Table_Fragment extends BaseFragments implements View.OnClickListener{
    private RelativeLayout main_table_tongxun_rl;
    private RelativeLayout main_table_zixun_rl;
    private RelativeLayout main_table_video_rl;

    @Override
    protected void initListener() {
        main_table_tongxun_rl.setOnClickListener(this);
        main_table_zixun_rl.setOnClickListener(this);
        main_table_video_rl.setOnClickListener(this);
    }

    @Override
    protected void initRefresh() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        main_table_tongxun_rl =(RelativeLayout)view.findViewById(R.id.main_table_tongxun_rl);
        main_table_zixun_rl =(RelativeLayout)view.findViewById(R.id.main_table_zixun_rl);
        main_table_video_rl =(RelativeLayout)view.findViewById(R.id.main_table_video_rl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_table_fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_table_tongxun_rl:
                Toast.makeText(getContext(),"通讯",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_table_zixun_rl:
                Toast.makeText(getContext(),"资讯",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_table_video_rl:
                Toast.makeText(getContext(),"视频",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
