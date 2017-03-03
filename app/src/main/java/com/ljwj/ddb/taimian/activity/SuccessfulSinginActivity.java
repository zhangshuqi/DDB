package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ljwj.ddb.taimian.R;

/**
 * 首次成功登录后，完善审核资料页面
 * Created by dell on 2017/1/10.
 */
public class SuccessfulSinginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button successful_yes_bt;
    private Button successful_no_bt;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful_singin_layout);
        initView();
        initListener();
    }

    private void initListener() {
        successful_no_bt.setOnClickListener(this);
        successful_yes_bt.setOnClickListener(this);
    }

    private void initView() {
        successful_no_bt=(Button)findViewById(R.id.successful_no_bt);
        successful_yes_bt= (Button)findViewById(R.id.successful_yes_bt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.successful_no_bt:
                mIntent=new Intent(this,HomeActivity.class);
                startActivity(mIntent);
                break;
            case R.id.successful_yes_bt:
                mIntent=new Intent(this,PersonalDataActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
