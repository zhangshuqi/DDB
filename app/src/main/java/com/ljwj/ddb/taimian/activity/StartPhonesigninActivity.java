package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;

/**
 * 手机登录界面
 * Created by dell on 2017/1/10.
 */
public class StartPhonesigninActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView start_phonesignin_forget_pwd;
    private Intent mIntent;
    private Button start_phonesignin_bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_phonesignin_layout);
        initView();
        initListener();
    }

    private void initListener() {
        start_phonesignin_forget_pwd.setOnClickListener(this);
        start_phonesignin_bt.setOnClickListener(this);
    }

    private void initView() {
        start_phonesignin_forget_pwd =(TextView)findViewById(R.id.start_phonesignin_forget_pwd);
        start_phonesignin_bt=(Button)findViewById(R.id.start_phonesignin_bt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_phonesignin_forget_pwd:
                mIntent=new Intent(this,StartPhonesigninForgetPwdActivity.class);
                startActivity(mIntent);
                break;
            case R.id.start_phonesignin_bt:
                //这里登录只是暂时用的，以后还要请求网络这边成功才进去
                mIntent=new Intent(this,SuccessfulSinginActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
