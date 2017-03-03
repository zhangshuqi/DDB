package com.ljwj.ddb.taimian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.MyDataActivity;
import com.ljwj.ddb.taimian.activity.StartActivity;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.UserInfo;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.SPUtils;

/**
 * 我的主页面
 * Created by dell on 2016/12/21.
 */

public class Main_MyData_Fragment extends BaseFragments implements View.OnClickListener{

    private RelativeLayout fm_home_my_rl;
    private LinearLayout mydata_yeslogin_ll; //登录成功的
    private LinearLayout maydata_nologin_ll; //未登录的
    private String TAG="Main_MyData_Fragment";
    private Button mydata_login_bt;
    private Intent mIntent;
    private TextView mydata_name_tv;
    private TextView mydata_idnumber_tv;
    private RelativeLayout mydata_newsw_tv;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mydata_rl:
                mIntent=new Intent(getContext(),MyDataActivity.class);
                startActivity(mIntent);
                break;
            case R.id.mydata_login_bt:
                mIntent=new Intent(getContext(), StartActivity.class);
                startActivity(mIntent);
                getActivity().finish();
                break;
            case R.id.mydata_newsw_tv:
                Toast.makeText(getContext(),"更新",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void initListener() {
        fm_home_my_rl.setOnClickListener(this);
        mydata_login_bt.setOnClickListener(this);

        mydata_newsw_tv.setOnClickListener(this);
    }

    @Override
    protected void initRefresh() {

    }

    @Override
    protected void initData() {
        boolean isLogin = SPUtils.getLogin(getContext(), Constant.LOGIN, false);
        Log.i(TAG, "initData: 回来了"+isLogin);
        if(isLogin){
            //显示已登录的界面
            mydata_yeslogin_ll.setVisibility(View.VISIBLE);
            maydata_nologin_ll.setVisibility(View.GONE);

            FragmentActivity activity = getActivity();
            Intent intent = activity.getIntent();

            UserInfo.DBean users=(UserInfo.DBean)intent.getSerializableExtra(Constant.USER);
            String nickname = users.getNickname();
            int userid = users.getUserid();
            mydata_name_tv.setText(nickname);
            mydata_idnumber_tv.setText(userid+"");

        }else {
            //显示未登录的界面
            mydata_yeslogin_ll.setVisibility(View.GONE);
            maydata_nologin_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: 回来了============");
        boolean login = SPUtils.getLogin(getContext(), Constant.LOGIN, false);
        if(login){
            mydata_yeslogin_ll.setVisibility(View.VISIBLE);
            maydata_nologin_ll.setVisibility(View.GONE);
        }else {
            mydata_yeslogin_ll.setVisibility(View.GONE);
            maydata_nologin_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView(View view) {
        fm_home_my_rl = (RelativeLayout)view.findViewById(R.id.mydata_rl);
        mydata_yeslogin_ll = (LinearLayout)view.findViewById(R.id.mydata_yeslogin_ll);
        maydata_nologin_ll = (LinearLayout)view.findViewById(R.id.maydata_nologin_ll);
        mydata_login_bt =(Button)view.findViewById(R.id.mydata_login_bt);
        mydata_name_tv =(TextView)view.findViewById(R.id.mydata_name_tv);
        mydata_idnumber_tv =(TextView)view.findViewById(R.id.mydata_idnumber_tv);
        mydata_newsw_tv=(RelativeLayout)view.findViewById(R.id.mydata_newsw_tv);

    }

    @Override
    public int getLayoutId() {
        return R.layout.main_mydata_fragment;
    }
}