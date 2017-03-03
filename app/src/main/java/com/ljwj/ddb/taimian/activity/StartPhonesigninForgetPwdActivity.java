package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.utils.Constant;

/**
 * 忘记密码界面
 * Created by dell on 2017/1/10.
 */
public class StartPhonesigninForgetPwdActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView forgwepwd_iv;
    private EditText forgetpwd_phone_et;
    private EditText forgetpwd_code_et;
    private TextView forgetpwd_getcode_tv;
    private EditText forgetpwd_imgcode_et;
    private EditText forgetpwd_repeatpwd_et;
    private ImageView forgetpwd_showpwd_iv;
    private Button forgetpwd_resetpwd_bt;
    private String TAG="StartPhonesigninForgetPwdActivity";
    private Handler handler;
    private int count;
    private ImageView forgetpwd_imgcode_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_phonesignin_forgetpwd_layout);
        initView();
        initListener();
        initData();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int arg1 = msg.arg1;
                if(arg1==60){
                    forgetpwd_getcode_tv.setText("重新获取("+arg1+"s)");
                    forgetpwd_getcode_tv.setTextColor(getResources().getColor(R.color.blue));
                    forgetpwd_getcode_tv.setEnabled(true);
                }else {
                    forgetpwd_getcode_tv.setText("获取("+arg1+"s)");
                    forgetpwd_getcode_tv.setTextColor(getResources().getColor(R.color.colorGray));
                    forgetpwd_getcode_tv.setEnabled(false);
                }
            }
        };
    }

    private void initData() {
        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        if(number!=null){
            forgetpwd_phone_et.setText(number);
        }
    }

    private void initListener() {
        forgwepwd_iv.setOnClickListener(this);
        forgetpwd_phone_et.setOnClickListener(this);
        forgetpwd_code_et.setOnClickListener(this);
        forgetpwd_getcode_tv.setOnClickListener(this);
        forgetpwd_imgcode_et.setOnClickListener(this);
        forgetpwd_repeatpwd_et.setOnClickListener(this);
        forgetpwd_resetpwd_bt.setOnClickListener(this);
        forgetpwd_imgcode_iv.setOnClickListener(this);
        forgetpwd_showpwd_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        forgetpwd_showpwd_iv.setImageResource(R.drawable.unknown);
                        forgetpwd_repeatpwd_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        forgetpwd_showpwd_iv.setImageResource(R.drawable.conceal);
                        forgetpwd_repeatpwd_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgwepwd_iv:
                finish();
                break;
            case R.id.forgetpwd_phone_et:
                Toast.makeText(this,"手机号码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgetpwd_code_et:
                Toast.makeText(this,"验证码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgetpwd_getcode_tv:
                count=1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int x=60;count<=x;count++){
                            Message msg=new Message();
                            msg.arg1=count;
                            handler.sendMessage(msg);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.forgetpwd_imgcode_iv:

                getNetImagview();
                break;
            case R.id.forgetpwd_imgcode_et:
                Toast.makeText(this,"图形验证码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgetpwd_repeatpwd_et:
                Toast.makeText(this,"重新输入密码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgetpwd_resetpwd_bt:
                Toast.makeText(this,"重置密码",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getNetImagview(){
        //获取图形验证码
        Glide.with(this).load(Constant.URI+Constant.URIIMAGCODE).diskCacheStrategy(DiskCacheStrategy.NONE).into(forgetpwd_imgcode_iv);
    }

    private void initView() {
        forgwepwd_iv=(ImageView)findViewById(R.id.forgwepwd_iv);//返回
        forgetpwd_phone_et=(EditText)findViewById(R.id.forgetpwd_phone_et);//输入手机号码
        forgetpwd_code_et=(EditText)findViewById(R.id.forgetpwd_code_et);//输入验证码
        forgetpwd_getcode_tv=(TextView)findViewById(R.id.forgetpwd_getcode_tv);//获取短信验证码
        forgetpwd_imgcode_et=(EditText)findViewById(R.id.forgetpwd_imgcode_et);//获取图形验证码
        forgetpwd_repeatpwd_et=(EditText)findViewById(R.id.forgetpwd_repeatpwd_et);//重复输入密码
        forgetpwd_showpwd_iv=(ImageView)findViewById(R.id.forgetpwd_showpwd_iv);//显示密码
        forgetpwd_resetpwd_bt=(Button)findViewById(R.id.forgetpwd_resetpwd_bt);//重置密码
        forgetpwd_imgcode_iv =(ImageView)findViewById(R.id.forgetpwd_imgcode_iv);
    }
}
