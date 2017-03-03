package com.ljwj.ddb.taimian.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.dialog.QuitDialog;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.SPUtils;

/**
 * 个人资料页面
 * Created by dell on 2017/1/9.
 */
public class MyDataActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView my_data_back_iv;
    private Button my_data_return_bt;
    public Intent mIntent;
    private static final String COOKIE_PREF = "cookies_prefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_data_activity);
        initView();
        initListener();
    }

    private void initListener() {
        my_data_back_iv.setOnClickListener(this);
        my_data_return_bt.setOnClickListener(this);
    }

    private void initView() {
        my_data_back_iv =(ImageView)findViewById(R.id.my_data_back_iv);
        my_data_return_bt = (Button) findViewById(R.id.my_data_return_bt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_data_back_iv:

                finish();
                break;
            case R.id.my_data_return_bt:
                QuitDialog.Builder builder = new QuitDialog.Builder(this);
                builder.setMessage("确定要退出当前账号吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mIntent=new Intent(MyDataActivity.this,StartActivity.class);
                        startActivity(mIntent);
                        SPUtils.putLogin(MyDataActivity.this, Constant.LOGIN,false);
                        SharedPreferences sp = getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit(); //清除COOKIE_PREF表单的信息，并提交
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                break;
        }
    }
}
