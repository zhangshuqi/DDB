package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;

/**
 * 手机注册界面
 * Created by A chang on 2017/1/9.
 */
public class StartPhoneLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView start_phone_login_tv;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_phonelogin_layout);
        initView();
        initListener();
    }

    private void initListener() {
        start_phone_login_tv.setOnClickListener(this);
    }

    private void initView() {
        start_phone_login_tv =(TextView)findViewById(R.id.start_phone_login_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_phone_login_tv:
                mIntent=new Intent(this,StartPhonesigninActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
