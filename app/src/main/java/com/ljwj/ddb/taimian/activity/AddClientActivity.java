package com.ljwj.ddb.taimian.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.provider.ContactsContract.Data;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;
import com.ljwj.ddb.taimian.bean.LatelyClientDataDao;
import com.ljwj.ddb.taimian.utils.DialogUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新建临时客户
 * Created by dell on 2017/1/7.
 */
public class AddClientActivity extends AppCompatActivity implements View.OnClickListener,OnDateSetListener {

    private ImageView switch_iv;
    private RelativeLayout switch_rl;
    private int flag = 0;
    private EditText clientle_addclient_name_et;
    private TextView clientle_addclient_sex_et;
    private TextView clientle_addclient_relation_et;
    private EditText clientle_addclient_call_et;
    private EditText clientle_addclient_site_et;
    private EditText clientle_addclient_measuretime_et;
    private TextView centleman_tv;
    private TextView lady_tv;
    private View sex_inflate;
    private Dialog mDialog;
    private Window dialogWindow;
    private View relationfalg_inflate;
    private TextView merchant_tv;
    private TextView manufacturers_tv;
    private TextView supervisor_tv;
    private TextView owner_tv;
    private String mSex;
    private TextView clientle_addclient_done_tv;
    private String TAG = "AddClientActivity";
    private SoonClientDataDao insertDao;
    private ImageView clientle_addclient_goback_iv;
    private String nameString;
    private String sexString;
    private String relationString;
    private String callString;
    private String siteString;
    private String measuretimeString;
    private String userid;
    private LatelyClientDataDao latelyDataDao;
    private TimePickerDialog timeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientle_addclient_activity);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        //监听滑动开关的开关事件
        switch_rl.setOnClickListener(this);
        clientle_addclient_sex_et.setOnClickListener(this);
        clientle_addclient_relation_et.setOnClickListener(this);
        centleman_tv.setOnClickListener(this);
        lady_tv.setOnClickListener(this);
        merchant_tv.setOnClickListener(this);
        manufacturers_tv.setOnClickListener(this);
        supervisor_tv.setOnClickListener(this);
        owner_tv.setOnClickListener(this);
        clientle_addclient_done_tv.setOnClickListener(this);
        clientle_addclient_goback_iv.setOnClickListener(this);
        clientle_addclient_measuretime_et.setOnClickListener(this);
    }

    private void initData() {
        //初始化开关是关闭状态的图标
        switch_iv.setImageResource(R.drawable.close);
    }

    private void initView() {
        switch_iv = (ImageView) findViewById(R.id.switch_iv);
        switch_rl = (RelativeLayout) findViewById(R.id.switch_rl);
        //返回
        clientle_addclient_goback_iv = (ImageView) findViewById(R.id.clientle_addclient_goback_iv);
        //完成
        clientle_addclient_done_tv = (TextView) findViewById(R.id.clientle_addclient_done_tv);
        //客户姓名
        clientle_addclient_name_et = (EditText) findViewById(R.id.clientle_addclient_name_et);
        //性别
        clientle_addclient_sex_et = (TextView) findViewById(R.id.clientle_addclient_sex_et);
        //联系类别
        clientle_addclient_relation_et = (TextView) findViewById(R.id.clientle_addclient_relation_et);
        //联系电话
        clientle_addclient_call_et = (EditText) findViewById(R.id.clientle_addclient_call_et);
        //客户地址
        clientle_addclient_site_et = (EditText) findViewById(R.id.clientle_addclient_site_et);
        //测量时间
        clientle_addclient_measuretime_et = (EditText) findViewById(R.id.clientle_addclient_measuretime_et);
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialogWindow = mDialog.getWindow();//获取当前Activity所在的窗体
        //性别布局
        sex_inflate = LayoutInflater.from(this).inflate(R.layout.sex_dialog, null);
        centleman_tv = (TextView) sex_inflate.findViewById(R.id.centleman_tv);
        lady_tv = (TextView) sex_inflate.findViewById(R.id.lady_tv);
        //联系类别布局
        relationfalg_inflate = LayoutInflater.from(this).inflate(R.layout.relationfalg_dialog, null);
        merchant_tv = (TextView) relationfalg_inflate.findViewById(R.id.merchant_tv);
        manufacturers_tv = (TextView) relationfalg_inflate.findViewById(R.id.manufacturers_tv);
        supervisor_tv = (TextView) relationfalg_inflate.findViewById(R.id.supervisor_tv);
        owner_tv = (TextView) relationfalg_inflate.findViewById(R.id.owner_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clientle_addclient_done_tv://完成
                //获取EditText输入的内容
                getClientData();
                if (nameString.isEmpty() || sexString.isEmpty() || relationString.isEmpty() || callString.isEmpty() || siteString.isEmpty() || measuretimeString.isEmpty()) {
                    //为空
                    Toast.makeText(this, "请填写当前页面资料！", Toast.LENGTH_SHORT).show();
                    break;
                }
                //flag==1说明flag标志是保存到通讯录状态
                if(flag==1){
                    addContact(nameString,callString);
                }
                //创建数据库操作类
                insertDao = new SoonClientDataDao(this);
                //创建最近客户数据库操作类
              //  latelyDataDao=new LatelyClientDataDao(this);
                //将填写好的数据插入到数据库当中
                long l = System.currentTimeMillis();//获取毫秒值作为ID
                userid = String.valueOf(l);
                ClientBean clientBean=new ClientBean();//创建一个bean类
                clientBean.setUserid(userid); //用户ID
                clientBean.setName(nameString);//姓名
                clientBean.setSex(sexString);//性别
                clientBean.setRelation(relationString);//类别
                clientBean.setPhone(callString);//电话
                clientBean.setSite(siteString);//地址
                clientBean.setDate(measuretimeString);//测量时间
                clientBean.setState(0);//刚添加的客户状态都为0
                clientBean.setType(1);
              //0代表的状态是临时客户
                //临时客户数据库插入
                insertDao.insert(clientBean.getUserid()
                        , clientBean.getName()
                        , clientBean.getSex()
                        , clientBean.getRelation()
                        , clientBean.getPhone()
                        , clientBean.getSite()
                        , clientBean.getDate(),0,1);
                //发送消息
                EventBus.getDefault().post(new EventBusUtils(clientBean));

                finish();
                break;
            case R.id.switch_rl:
                isSwitch();
                break;
            case R.id.clientle_addclient_sex_et:
                //性别布局填充给dialog
                mDialog.setContentView(sex_inflate);
                getDialog();
                break;
            case R.id.centleman_tv: //男士
                //设置参数
                mSex = centleman_tv.getText().toString().trim();
                sexSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.lady_tv:  //女士
                //设置参数
                mSex = lady_tv.getText().toString().trim();
                sexSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.clientle_addclient_relation_et:
                //填充联系类别布局给dialog
                mDialog.setContentView(relationfalg_inflate);
                getDialog();
                break;
            case R.id.merchant_tv: //商家
                mSex = merchant_tv.getText().toString().trim();
                ralationfalgSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.manufacturers_tv://厂家
                mSex = manufacturers_tv.getText().toString().trim();
                ralationfalgSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.supervisor_tv://监理
                mSex = supervisor_tv.getText().toString().trim();
                ralationfalgSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.owner_tv://业主
                mSex = owner_tv.getText().toString().trim();
                ralationfalgSetText(mSex);
                mDialog.dismiss();
                break;
            case R.id.clientle_addclient_measuretime_et:
/*
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        clientle_addclient_measuretime_et.setText(year+"-"+month+"-"+dayOfMonth);
                    }
                },2017,01,01).show();*/
                if (timeDialog==null) {
                    timeDialog = DialogUtils.createTimeDialog(this, this);
                }
                timeDialog.show(getSupportFragmentManager(), "all");
                break;

            case R.id.clientle_addclient_goback_iv://返回
                //这里做一个弹窗提示，如果判断当前页面没填写资料弹窗提示
                finish();
                break;
        }
    }

    //获取客户资料
    private void getClientData() {
        nameString = clientle_addclient_name_et.getText().toString().trim(); //姓名
        sexString = clientle_addclient_sex_et.getText().toString().trim();   //性别
        relationString = clientle_addclient_relation_et.getText().toString().trim(); //联系类别
        callString = clientle_addclient_call_et.getText().toString().trim(); //联系电话
        siteString = clientle_addclient_site_et.getText().toString().trim();  //客户地址
        measuretimeString = clientle_addclient_measuretime_et.getText().toString().trim();  //测量时间
    }

    private void sexSetText(String mSex) {
        clientle_addclient_sex_et.setText(mSex);
    }

    private void ralationfalgSetText(String mSex) {
        clientle_addclient_relation_et.setText(mSex);
    }

    private void getDialog() {
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 40;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }

    private void isSwitch() {
        if (flag == 0) {
            //保存状态后,联系人添加到手机通讯录里面
            getClientData();
            if (nameString.isEmpty() || sexString.isEmpty() || relationString.isEmpty() || callString.isEmpty() || siteString.isEmpty() || measuretimeString.isEmpty()) {
                //为空
                Toast.makeText(this, "请填写当前页面资料！", Toast.LENGTH_SHORT).show();
                return;
            }
            switch_iv.setImageResource(R.drawable.open);
            flag = 1;
        } else {
            //关闭
            switch_iv.setImageResource(R.drawable.close);
            flag = 0;
        }
    }

    // 添加联系人信息的方法
    public void addContact(String name, String phoneNumber) {
        // 创建一个空的ContentValues
        ContentValues values = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        // 向联系人URI添加联系人名字
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        // 电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        getContentResolver().insert(Data.CONTENT_URI, values);
        values.clear();

        Toast.makeText(this, "添加联系人成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        clientle_addclient_measuretime_et.setText(text);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1=new Date(time);
        return format.format(d1);
    }
}
