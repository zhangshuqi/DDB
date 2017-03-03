package com.ljwj.ddb.taimian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.UserData;
import com.ljwj.ddb.taimian.bean.UserInfo;
import com.ljwj.ddb.taimian.dialog.LoginDialog;
import com.ljwj.ddb.taimian.net.RetrofitManager;
import com.ljwj.ddb.taimian.utils.AddCookiesInterceptor;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.CookieInterceptor;
import com.ljwj.ddb.taimian.utils.ResponseInfoAPI;
import com.ljwj.ddb.taimian.utils.SPUtils;
import com.ljwj.ddb.taimian.utils.SaveCookiesInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 开始主界面注册以及登录
 * Created by A chang on 2017/1/9.
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView start_casual_tv;
    private RelativeLayout start_sign_ll;
    private Intent mIntent;
    private EditText start_number_et;
    private EditText start_password_et;
    private ImageView start_showpassword_iv;
    private String numberString, passwordString;
    private LinearLayout rememberpassword_ll;
    private ImageView start_show_iv;
    private int intFlag = 0;
    private TextView start_login_tv;
    public String TAG = "StartActivity";
    private LoginDialog loginDialog;
    private LinearLayout forgetpassword_ll;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);
        initToken();
        initView();
        initData();
        initListener();
    }

    //免密登录验证
    private void initToken() {
        boolean login = SPUtils.getLogin(this, Constant.LOGIN, false);
        Log.i(TAG, "initToken: 登录状态"+login);
        if(login){
            token = SPUtils.getToken(this, Constant.TOKEN, "");//获取密令

            RetrofitManager.getInstance()
                    .createReq(ResponseInfoAPI.class)
                    .getUserToken(token)
                    .enqueue(new Callback<UserInfo>() {
                        @Override
                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                            int code = response.code();
                            if(code==200){
                                UserInfo body = response.body();
                                int c = body.getC();
                                if(c==0){
                                    //请求成功
                                    UserInfo.DBean d = body.getD();
                                    String newToken = d.getToken();//获取密令
                                    SPUtils.putToken(StartActivity.this,Constant.TOKEN,newToken);//将新获取的密令存进sp
                                    int result = d.getResult();
                                    if(result==1){
                                        getUserInfo();//免密登录成功获取用户会话信息
                                        mIntent = new Intent(StartActivity.this, HomeActivity.class);
                                        mIntent.putExtra(Constant.USER,d);
                                        startActivity(mIntent);
                                        //记录登录状态
                                        SPUtils.putLogin(StartActivity.this,Constant.LOGIN,true);
                                        finish();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<UserInfo> call, Throwable t) {
                            Toast.makeText(StartActivity.this,"没有获取网络连接",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //获取用户会话信息
    private void getUserInfo() {

        Retrofit mmRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CookieInterceptor.getOKHttpClien(this))
                .build();
        mmRetrofit.create(ResponseInfoAPI.class)
                .getUserData()

//        RetrofitManager.getInstance()
//                .createReq(ResponseInfoAPI.class)
//                .getUserData()
                .enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                int code = response.code();
                if(code==200){
                    UserData body = response.body();
                    int c = body.getC();
                    if(c==0){
                        UserData.DBean d = body.getD();
                        int uid = d.getUid(); //用户ID
                        String nickname = d.getNickname();//用户昵称
                        Log.i(TAG, "onResponse: 用户ID，"+uid+"用户昵称："+nickname);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                    Toast.makeText(StartActivity.this,"亲，网络似乎不好哦！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        boolean flag = SPUtils.getIsCheck(this, Constant.FLAG, false);
        intFlag = SPUtils.getInt(this, Constant.INTFLAG, 0);//获取退出时标记记住密码的标记状态
        if (flag) {
            start_show_iv.setVisibility(View.VISIBLE);//显示为记住密码状态
            //有记住密码状态,回显账号密码
            String number = SPUtils.getNumberString(this, Constant.NUMBER, "");
            String password = SPUtils.getNumberString(this, Constant.PASSWORD, "");
            start_number_et.setText(number);
            start_password_et.setText(password);
        }
    }

    private void initListener() {
        start_casual_tv.setOnClickListener(this);
        start_sign_ll.setOnClickListener(this);
        rememberpassword_ll.setOnClickListener(this);
        forgetpassword_ll.setOnClickListener(this);

        start_showpassword_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        start_showpassword_iv.setImageResource(R.drawable.unknown);
                        start_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        start_showpassword_iv.setImageResource(R.drawable.conceal);
                        start_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.start_casual_tv: //随便看看
                SPUtils.putLogin(this,Constant.LOGIN,false);//未登录
                mIntent = new Intent(this, HomeActivity.class);
                startActivity(mIntent);
                finish();
                break;
            case R.id.rememberpassword_ll://是否记住密码
                if (intFlag == 0) {
                    start_show_iv.setVisibility(View.VISIBLE);
                    SPUtils.putIsCheck(this, Constant.FLAG, true); //记住密码
                    intFlag = 1;
                } else {
                    start_show_iv.setVisibility(View.GONE);
                    SPUtils.putIsCheck(this, Constant.FLAG, false); //不记住密码
                    intFlag = 0;
                }
                break;
            case R.id.forgetpassword_ll://忘记密码
                mIntent = new Intent(this, StartPhonesigninForgetPwdActivity.class);
                String number = start_number_et.getText().toString().trim();
                if(number!=null){
                    mIntent.putExtra("number",number);
                }
                startActivity(mIntent);
                break;
            case R.id.start_sign_ll://登录
                numberString = start_number_et.getText().toString().trim();
                passwordString = start_password_et.getText().toString().trim();
                getRequest(numberString, passwordString);
                break;
        }
    }

    private void getRequest(final String numberString, final String passwordString) {

        if (!numberString.isEmpty() && !passwordString.isEmpty()) {
            //账号密码不为空
            loginDialog = new LoginDialog(StartActivity.this);
            loginDialog.setCanceledOnTouchOutside(false);
            loginDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Retrofit网络请求
                    Retrofit mRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.URI)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(CookieInterceptor.setOKHttpClien(StartActivity.this))
                            .build();
                    mRetrofit.create(ResponseInfoAPI.class)
                            .getUserInfo(numberString, passwordString)
                            .enqueue(new Callback<UserInfo>() {
                                @Override
                                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                                    int code = response.code();
                                    if (code == 200) {
                                        UserInfo body = response.body();
                                        int c = body.getC();
                                        if(c==0){
                                            //请求成功
                                            UserInfo.DBean d = body.getD();
                                            int result = d.getResult();//响应码
                                            if (result == 1) {
                                                getUserInfo();//登录成功获取用户会话信息
                                                SPUtils.putNumberString(StartActivity.this,Constant.NUMBER,numberString);//登录成功后保存账号到sp
                                                SPUtils.putNumberString(StartActivity.this,Constant.PASSWORD,passwordString);//登录成功后保存密码到sp
                                                String token = d.getToken();//获取成功后获取的密令
                                                SPUtils.putToken(StartActivity.this,Constant.TOKEN,token);

                                                mIntent = new Intent(StartActivity.this, HomeActivity.class);
                                                mIntent.putExtra(Constant.USER,d);
                                                startActivity(mIntent);
                                                SPUtils.putLogin(StartActivity.this,Constant.LOGIN,true);
                                                loginDialog.dismiss();
                                                finish();

                                            } else if (result == -1) {
                                                Toast.makeText(StartActivity.this, "参数错误", Toast.LENGTH_SHORT).show();
                                                loginDialog.dismiss();
                                            } else if (result == 2) {
                                                Toast.makeText(StartActivity.this, "找不到用户信息", Toast.LENGTH_SHORT).show();
                                                loginDialog.dismiss();
                                            } else if (result == 3) {

                                                Toast.makeText(StartActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                                SPUtils.putIsCheck(StartActivity.this, Constant.FLAG, false); //登录成功后记录为false
                                                loginDialog.dismiss();

                                            } else if (result == 4) {
                                                Toast.makeText(StartActivity.this, "用户已被停用", Toast.LENGTH_SHORT).show();
                                                loginDialog.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserInfo> call, Throwable t) {
                                    Toast.makeText(StartActivity.this, "世界上最遥远的距离就是没有网络！", Toast.LENGTH_SHORT).show();
                                    SPUtils.putIsCheck(StartActivity.this, Constant.FLAG, false); //登录失败记录为false
                                    loginDialog.dismiss();
                                }
                            });
                }
            }, 2000);
        }
        return;
    }

    private void initView() {
        start_casual_tv = (TextView) findViewById(R.id.start_casual_tv);
        start_sign_ll = (RelativeLayout) findViewById(R.id.start_sign_ll);
        start_number_et = (EditText) findViewById(R.id.start_number_et);
        start_password_et = (EditText) findViewById(R.id.start_password_et);
        start_showpassword_iv = (ImageView) findViewById(R.id.start_showpassword_iv);
        rememberpassword_ll = (LinearLayout) findViewById(R.id.rememberpassword_ll);
        start_show_iv = (ImageView) findViewById(R.id.start_show_iv);
        start_login_tv = (TextView) findViewById(R.id.start_login_tv);
        forgetpassword_ll = (LinearLayout) findViewById(R.id.forgetpassword_ll);

        //监听账号密码项的edittext的实时输入状况
        start_number_et.addTextChangedListener(new TextChanged());
        start_password_et.addTextChangedListener(new TextChanged());
    }

    //监听edittext实时内容类
    class TextChanged implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            setLoginButton();
        }
    }

    //设置登录按钮的状态
    private void setLoginButton() {
        String number = start_number_et.getText().toString().trim();
        String password = start_password_et.getText().toString().trim();
        if (!number.isEmpty() && !password.isEmpty()) {
            //账号密码都不为空
            start_login_tv.setTextColor(getResources().getColor(R.color.white));
            start_sign_ll.setBackground(getResources().getDrawable(R.drawable.rectangleyes));
            start_sign_ll.setEnabled(true);//控件激活
            rememberpassword_ll.setEnabled(true);//有账号密码是才可以点击记住密码

        } else {
            //账号密码为空
            start_login_tv.setTextColor(getResources().getColor(R.color.colorGray));
            start_sign_ll.setBackground(getResources().getDrawable(R.drawable.rectanglelogin));
            start_sign_ll.setEnabled(false);//控件关闭
            start_show_iv.setVisibility(View.GONE);
            rememberpassword_ll.setEnabled(false);//没有输入账号密码不可以点击记住密码
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLoginButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.putInt(this, Constant.INTFLAG, intFlag);//退出时标记记住密码的状态
    }
}
