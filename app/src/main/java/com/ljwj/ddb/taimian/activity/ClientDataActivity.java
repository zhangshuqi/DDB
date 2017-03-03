package com.ljwj.ddb.taimian.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.bean.ADDClientBean;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.NewRoomClientDataDao;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;
import com.ljwj.ddb.taimian.bean.LatelyClientDataDao;
import com.ljwj.ddb.taimian.bean.OfficialClientDataDao;
import com.ljwj.ddb.taimian.dialog.QuitDialog;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.CookieInterceptor;
import com.ljwj.ddb.taimian.utils.EventBusOfficialUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;
import com.ljwj.ddb.taimian.utils.ResponseInfoAPI;
import com.ljwj.ddb.taimian.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 客户资料的界面
 * Created by dell on 2017/1/16.
 */

public class ClientDataActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout client_data_details_rl;
    private Button client_data_gomeasure_bt;
    private Intent mIntent;
    private TextView client_data_details_name_tv;
    private TextView measuredate_tv;
    private TextView relation_tv;
    private TextView phone_tv;
    private TextView site_tv;
    private TextView client_data_sex_tv;
    private TextView client_data_state_tv;
    private LinearLayout client_data_ordernumber_ll;
    private Button client_data_uploading_bt;
    private String date, name,phone,site,relation,sex,userid;
    private int intSex, intRelation;
    private String TAG="ClientDataActivity";
    private ImageView client_data_delete_iv;
    private ImageView client_data_break_iv;
    private TextView client_data_userid_tv;
    private RelativeLayout client_data_callup_rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_data_activity);
        initView();
        initListener();
        initdata();
    }

    private void initdata() {
        Intent intent = getIntent();
        name = intent.getStringExtra(Constant.NAME);
        date = intent.getStringExtra(Constant.DATE);
        phone = intent.getStringExtra(Constant.PHONE);
        site = intent.getStringExtra(Constant.SITE);
        sex = intent.getStringExtra(Constant.SEX);

        relation = intent.getStringExtra(Constant.RELATION);
        userid = intent.getStringExtra(Constant.USERID);//用户ID

       // state = intent.getIntExtra(Constant.STATE,0);//获取客户状态
        String sState = intent.getStringExtra(Constant.STATE);
        int iState = Integer.parseInt(sState);
        Log.i(TAG, "initdata: iState"+iState);

        judgeSex(sex);
        judgeRelation(relation);

        if(iState==0){
            //stateFlag等于0就是临时客户
            client_data_state_tv.setText("临时");
        }else if(iState==1){
            client_data_state_tv.setText("测量");
        }else if(iState==2){
            client_data_state_tv.setText("设计");
        }
        client_data_details_name_tv.setText(name);
        measuredate_tv.setText(date);
        relation_tv.setText(relation);
        phone_tv.setText(phone);
        site_tv.setText(site);
        client_data_sex_tv.setText(sex);
        client_data_userid_tv.setText(userid);
    }

    //判断联系类别
    private void judgeRelation(String relation) {
        if(relation.equals("商家")){
            intRelation=1;
        }else if (relation.equals("厂家")){
            intRelation=2;
        }else if (relation.equals("监理")){
            intRelation=3;
        }else {
            intRelation=4;
        }
    }
    //判断性别
    private void judgeSex(String sex) {
        if(sex.equals("男士")){
            intSex=1;
        }else if(sex.equals("女士")){
            intSex=2;
        }
    }
    private void initListener() {
        client_data_details_rl.setOnClickListener(this);
        client_data_gomeasure_bt.setOnClickListener(this);
        client_data_uploading_bt.setOnClickListener(this);
        client_data_delete_iv.setOnClickListener(this);
        client_data_break_iv.setOnClickListener(this);
        client_data_callup_rl.setOnClickListener(this);
    }

    private void initView() {
        client_data_details_rl =(RelativeLayout)findViewById(R.id.client_data_details_rl);
        client_data_ordernumber_ll=(LinearLayout)findViewById(R.id.client_data_ordernumber_ll);
        client_data_gomeasure_bt=(Button)findViewById(R.id.client_data_gomeasure_bt);
        client_data_uploading_bt=(Button)findViewById(R.id.client_data_uploading_bt);
        client_data_details_name_tv=(TextView)findViewById(R.id.client_data_details_name_tv);
        client_data_state_tv=(TextView)findViewById(R.id.client_data_state_tv);
        measuredate_tv=(TextView)findViewById(R.id.measuredate_tv);
        client_data_callup_rl=(RelativeLayout)findViewById(R.id.client_data_callup_rl);
        relation_tv = (TextView)findViewById(R.id.relation_tv);
        phone_tv =(TextView) findViewById(R.id.client_data_phone_tv);
        site_tv=(TextView)findViewById(R.id.site_tv);
        client_data_sex_tv=(TextView)findViewById(R.id.client_data_sex_tv);
        client_data_delete_iv=(ImageView)findViewById(R.id.client_data_delete_iv);
        client_data_break_iv=(ImageView)findViewById(R.id.client_data_break_iv);
        client_data_userid_tv=(TextView)findViewById(R.id.client_data_userid_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.client_data_details_rl:   //订单详情
                mIntent=new Intent(this,OrderdetailActivity.class);
                mIntent.putExtra(Constant.NAME,name);
                startActivity(mIntent);
                break;
            case R.id.client_data_gomeasure_bt:
                mIntent=new Intent(this,GoMeasureActivity.class);
                mIntent.putExtra(Constant.NAME,name);
                mIntent.putExtra(Constant.UID,userid);
                startActivity(mIntent);
                break;
            case R.id.client_data_delete_iv:
                QuitDialog.Builder builder = new QuitDialog.Builder(this);
                builder.setMessage("确定要删除当前客户吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //临时客户数据库删除
                        SoonClientDataDao cd=new SoonClientDataDao(ClientDataActivity.this);
                        cd.delete(client_data_userid_tv.getText().toString());
                        //最近客户数据库删除
                        LatelyClientDataDao ld=new LatelyClientDataDao(ClientDataActivity.this);
                        ld.delete(client_data_userid_tv.getText().toString());
                        //删除测量客户数据库
                        OfficialClientDataDao officialD= new OfficialClientDataDao(ClientDataActivity.this);
                        officialD.delete(client_data_userid_tv.getText().toString());
                        //房间类型数据库
                        NewRoomClientDataDao ncd=new NewRoomClientDataDao(ClientDataActivity.this);
                        ncd.delete(client_data_userid_tv.getText().toString());
                        //发送消息
                        EventBus.getDefault().post(new EventBusUtilsUpdate("1"));

                        Toast.makeText(ClientDataActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
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
            case R.id.client_data_callup_rl:
                String sPhone = phone_tv.getText().toString().trim();
                final Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+sPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.client_data_break_iv:
                finish();
                break;
            case R.id.client_data_uploading_bt:
                boolean isLogin = SPUtils.getLogin(this, Constant.LOGIN, false);
                if(isLogin){
                    //登录状态下才可以上传客户资料
                    Retrofit mRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.URI)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(CookieInterceptor.getOKHttpClien(this))
                            .build();
                    mRetrofit.create(ResponseInfoAPI.class)
                            .getAddClient(intRelation,name,intSex,phone,site,1)
                            .enqueue(new Callback<ADDClientBean>() {
                        @Override
                        public void onResponse(Call<ADDClientBean> call, Response<ADDClientBean> response) {
                            int code = response.code();
                            if(code==200){
                                ADDClientBean body = response.body();
                                int c = body.getC();
                                if(c==0){
                                    //请求成功
                                    ADDClientBean.DBean d = body.getD();
                                    int result = d.getResult();
                                    if(result==1){
                                        int userId = d.getCustomerid(); //长传成功返回客户id
                                        upLoading(userId);
                                        //上传成功之后吧临时客户里面的数据删掉
                                        SoonClientDataDao deleteDao=new SoonClientDataDao(ClientDataActivity.this);
                                        deleteDao.delete(client_data_userid_tv.getText().toString());//删除客户

                                        //修改最近客户数据的数据库
                                        LatelyClientDataDao ubDataDao=new LatelyClientDataDao(ClientDataActivity.this);
                                        String sNewUserId = String.valueOf(userId);
                                        String sUserId = client_data_userid_tv.getText().toString();
                                        //通过用户原来的ID修改客户类型及服务器返回来的ID
                                        ContentValues values = new ContentValues();
                                        values.put("state", 1);//正式客户
                                        values.put("userid", sNewUserId); //数据库更新的正式客户ID
                                        //数据更新
                                        ubDataDao.updata(values,sUserId); //临时客户的ID
                                        //发送消息
                                        EventBus.getDefault().post(new EventBusUtilsUpdate("1"));
                                        //上传成功之后就设置状态为true
                                        SPUtils.putIsCheck(ClientDataActivity.this,Constant.UPLOADINGFLAG,true);
                                        finish();
                                    }else {
                                        Toast.makeText(ClientDataActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                                        //失败则为false
                                        SPUtils.putIsCheck(ClientDataActivity.this,Constant.UPLOADINGFLAG,true);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ADDClientBean> call, Throwable t) {
                            Toast.makeText(ClientDataActivity.this,"网络似乎不太好哦！",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(this,"亲，你还未登录，请先登录！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //上传成功
    private void upLoading(int id) {
        OfficialClientDataDao ocDataDao=new OfficialClientDataDao(this);
        userid = String.valueOf(id);
        ClientBean clientBean=new ClientBean();//创建一个bean类

        clientBean.setUserid(userid);
        clientBean.setName(name);
        clientBean.setSex(sex);
        clientBean.setRelation(relation);
        clientBean.setPhone(phone);
        clientBean.setSite(site);
        clientBean.setDate(date);
        clientBean.setState(1);//1代表正式客户
        //测量用户数据库添加
        ocDataDao.insert(clientBean.getUserid()
                , clientBean.getName()
                , clientBean.getSex()
                , clientBean.getRelation()
                , clientBean.getPhone()
                , clientBean.getSite()
                , clientBean.getDate(),1);

        Log.i(TAG, "upLoading: 上传成功");
        //发送消息
        EventBus.getDefault().post(new EventBusOfficialUtils(clientBean));
    }
}
