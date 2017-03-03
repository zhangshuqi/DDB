package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.utils.Constant;

/**
 * 客户订单详情页面
 * Created by dell on 2017/1/17.
 */
public class OrderdetailActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView orderdetail_done_tv;
    private TextView orderdetail_name_tv;
    private ImageView orderdetail_back_iv;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail_layout);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mIntent = getIntent();
        String name = mIntent.getStringExtra(Constant.NAME);
        orderdetail_name_tv.setText(name);
    }

    private void initListener() {
        orderdetail_done_tv.setOnClickListener(this);
        orderdetail_back_iv.setOnClickListener(this);
    }

    private void initView() {
        orderdetail_done_tv=(TextView)findViewById(R.id.orderdetail_done_tv);//完成
        orderdetail_name_tv=(TextView)findViewById(R.id.orderdetail_name_tv);//名称
        orderdetail_back_iv=(ImageView)findViewById(R.id.orderdetail_back_iv);//返回
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orderdetail_done_tv:
                //跳转之前要做判断，文本框如果没有输入内容提示输入内容再进入下一步


                break;
            case R.id.orderdetail_back_iv:
                //返回
                finish();
                break;
        }
    }
}
