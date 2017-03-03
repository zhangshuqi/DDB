package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;

import org.jetbrains.annotations.Nullable;

/**
 * 个人资料填写页面
 * Created by dell on 2017/1/10.
 */
public class PersonalDataActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView personal_data_skip;
    private TextView personal_data_next;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldata_layout);
        initView();
        initListener();
    }

    private void initListener() {
        personal_data_skip.setOnClickListener(this);
        personal_data_next.setOnClickListener(this);
    }

    private void initView() {
        personal_data_skip =(TextView)findViewById(R.id.personal_data_skip);
        personal_data_next=(TextView)findViewById(R.id.personal_data_next);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_data_skip:
                //不设置个人资料直接跳转到技能设置页面
                mIntent=new Intent(this,SkillsSettingsOneActivity.class);
                startActivity(mIntent);
                break;
            case R.id.personal_data_next:
                //注，填完内容才可以下一步
                mIntent=new Intent(this,PersonalDataTwoActivity.class);
                startActivity(mIntent);

                break;
        }
    }
}
