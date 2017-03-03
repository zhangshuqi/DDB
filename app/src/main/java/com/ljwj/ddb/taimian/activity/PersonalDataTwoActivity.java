package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;

import org.jetbrains.annotations.Nullable;

/**
 * 第二个,个人资料页面
 * Created by dell on 2017/1/10.
 */
public class PersonalDataTwoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView personal_data_two_next;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldata_two_layout);
        initView();
        initListener();
    }

    private void initListener() {
        personal_data_two_next.setOnClickListener(this);
    }

    private void initView() {
        personal_data_two_next=(TextView)findViewById(R.id.personal_data_two_next);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_data_two_next:
                //注：内容为空不能进入下一步
                Toast.makeText(this,"第二个,个人资料页面",Toast.LENGTH_SHORT).show();
                mIntent=new Intent(this,SkillsSettingsOneActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
