package com.ljwj.ddb.taimian.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.adapter.NewRoomAdapter;
import com.ljwj.ddb.taimian.bean.NewRoomBean;
import com.ljwj.ddb.taimian.bean.NewRoomClientDataDao;
import com.ljwj.ddb.taimian.utils.AndroidBugWorkaround;
import com.ljwj.ddb.taimian.utils.Constant;

import java.util.ArrayList;

/**
 * 进入测量界面
 * Created by dell on 2017/1/19.
 */
public class GoMeasureActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout gomesaure_create_ll;
    private ImageView gomesaure_upload_lv;
    private ImageView gomeasure_back_iv;
    private TextView gomeasure_name_tv;
    private Dialog mDialog;
    private Window dialogWindow;
    private View measurec_inflate;
    private TextView kitchen_tv, toilet_tv, dining_tv, livingroom_tv, mainbedroom_tv, minbedroom_tv, child_tv, study_tv, balcony_tv, hall_tv, onther_tv;
    private ListView gomeasure_lv;
    private String title;
    private NewRoomAdapter newAdapter;
    private ArrayList<NewRoomBean> list;
    private String uid,name;
    private NewRoomClientDataDao mcd;
    private NewRoomClientDataDao roomDataDao;
    private String TAG="GoMeasureActivity";
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gomeasure_layout);
        AndroidBugWorkaround.assistActivity(findViewById(R.id.gomeasure_ll),40);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra(Constant.NAME);
        uid = intent.getStringExtra(Constant.UID);
        if(name!=null){
            gomeasure_name_tv.setText(name);
        }

        queryData(uid);//初始化查询数据库数据
    }

    private void initListener() {
        gomesaure_create_ll.setOnClickListener(this);
        gomesaure_upload_lv.setOnClickListener(this);
        gomeasure_back_iv.setOnClickListener(this);
        kitchen_tv.setOnClickListener(this);
        toilet_tv.setOnClickListener(this);
        dining_tv.setOnClickListener(this);
        livingroom_tv.setOnClickListener(this);
        mainbedroom_tv.setOnClickListener(this);
        minbedroom_tv.setOnClickListener(this);
        child_tv.setOnClickListener(this);
        study_tv.setOnClickListener(this);
        balcony_tv.setOnClickListener(this);
        hall_tv.setOnClickListener(this);
        onther_tv.setOnClickListener(this);

        gomeasure_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewRoomBean newRoomBean = list.get(position);
                String uid = newRoomBean.getmUis();
                String title = newRoomBean.getmTitle();

                mIntent=new Intent(GoMeasureActivity.this,NewRoomActivity.class);
                mIntent.putExtra(Constant.NAME,name);
                mIntent.putExtra(Constant.TITLE,title);
                mIntent.putExtra(Constant.UID,uid);
                startActivity(mIntent);

            }
        });
    }

    private void initView() {
        gomeasure_back_iv=(ImageView)findViewById(R.id.newrooms_backs_iv);
        gomesaure_create_ll=(LinearLayout)findViewById(R.id.gomesaure_create_ll);
        gomesaure_upload_lv=(ImageView)findViewById(R.id.newroom_upload_lv);
        gomeasure_name_tv =(TextView)findViewById(R.id.newroom_name_tv);
        gomeasure_lv=(ListView)findViewById(R.id.gomeasure_lv);

        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialogWindow = mDialog.getWindow();//获取当前Activity所在的窗体

        //获取房间类型布局
        measurec_inflate = LayoutInflater.from(this).inflate(R.layout.measurec_newroom_dialog, null);
        //设置到dialog
        mDialog.setContentView(measurec_inflate);

        kitchen_tv =(TextView) measurec_inflate.findViewById(R.id.kitchen_tv);//厨房
        toilet_tv =(TextView) measurec_inflate.findViewById(R.id.toilett_tv);//卫生间
        dining_tv =(TextView) measurec_inflate.findViewById(R.id.dining_tv);//餐厅
        livingroom_tv =(TextView) measurec_inflate.findViewById(R.id.livingroom_tv);//客厅
        mainbedroom_tv =(TextView) measurec_inflate.findViewById(R.id.mainbedroom_tv);//主卧室
        minbedroom_tv =(TextView) measurec_inflate.findViewById(R.id.minbedroom_tv);//次卧室
        child_tv =(TextView) measurec_inflate.findViewById(R.id.child_tv);//儿童房
        study_tv =(TextView) measurec_inflate.findViewById(R.id.study_tv);//书房
        balcony_tv =(TextView) measurec_inflate.findViewById(R.id.balcony_tv);//阳台
        hall_tv =(TextView) measurec_inflate.findViewById(R.id.hall_tv);//门厅
        onther_tv =(TextView) measurec_inflate.findViewById(R.id.onther_tv);//其他
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gomesaure_create_ll:
                getDialog();//弹出dialog
                break;
            case R.id.newroom_upload_lv:
                Toast.makeText(this,"上传资料",Toast.LENGTH_SHORT).show();
                break;
            case R.id.newrooms_backs_iv:
                finish();
                break;
            default:
                title = ((TextView)v).getText().toString().trim();
                mcd=new NewRoomClientDataDao(this);
                NewRoomBean nrb=new NewRoomBean();
                nrb.setmTitle(title);
                nrb.setmUis(uid);
                nrb.setmImage(null);
                //插入数据库
                mcd.insert(nrb.getmTitle(),nrb.getmUis(),nrb.getmImag());
                Log.i(TAG, "queryData: 当前用户ID----插入数据"+uid);
                queryData(uid);

                mDialog.dismiss();
                break;
        }
    }

    private void queryData(String id) {
        Log.i(TAG, "queryData: 当前用户ID----插入数据"+id);
        list=new ArrayList<>();
        roomDataDao = new NewRoomClientDataDao(this);
        list.addAll( roomDataDao.query(id));//从数据库获取数据
        //设置适配器
        newAdapter=new NewRoomAdapter(this,list);
        gomeasure_lv.setAdapter(newAdapter);

        newAdapter.notifyDataSetChanged();
    }

    private void getDialog() {
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }
}
