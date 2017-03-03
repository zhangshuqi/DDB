package com.ljwj.ddb.taimian.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.ClientDataActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.LatelyClientDataDao;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.EventBusUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * 我的任务页面
 * Created by dell on 2016/12/20.
 */

public class Main_Task_Fragment extends BaseFragments implements View.OnClickListener{
    private RelativeLayout task_rl;
    private ListView maintask_lv;
    private ArrayList<ClientBean> list;
    private TemporaryAdapter tem;
    private Intent mIntent;
    private LatelyClientDataDao latelyDataDao;
    private String TAG="Main_Task_Fragment";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.task_rl:
                Toast.makeText(getContext(),"使用宝典",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void initListener() {
        task_rl.setOnClickListener(this);

        maintask_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ClientBean clientBean = list.get(position );
                mIntent=new Intent(getContext(), ClientDataActivity.class);
                mIntent.putExtra(Constant.NAME,clientBean.getName() );
                mIntent.putExtra(Constant.DATE,clientBean.getDate() );
                mIntent.putExtra(Constant.PHONE,clientBean.getPhone() );
                mIntent.putExtra(Constant.SITE,clientBean.getSite() );
                mIntent.putExtra(Constant.SEX,clientBean.getSex() );
                mIntent.putExtra(Constant.RELATION,clientBean.getRelation() );

                String state = String.valueOf(clientBean.getState());
                mIntent.putExtra(Constant.STATE,state);
                mIntent.putExtra(Constant.USERID,clientBean.getUserid());

                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void initRefresh() {

    }

    @Override
    protected void initData() {
        //初始化加载数据
        queryData();
    }

    private void queryData() {
        list=new ArrayList<>();
        latelyDataDao = new LatelyClientDataDao(getContext());
        list.addAll(latelyDataDao.query());//从数据库获取数据
            //设置适配器数据
            tem=new TemporaryAdapter(getContext(),list);
            maintask_lv.setAdapter(tem);
            //刷新数据
            tem.notifyDataSetChanged();
    }

    @Override
    protected void initView(View view) {
        task_rl =(RelativeLayout)view.findViewById(R.id.task_rl);
        maintask_lv=(ListView)view.findViewById(R.id.maintask_lv);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    //EventBus接收消息回调的方法
    @Subscribe
    public void onEventMainThread(EventBusUtils event){
        //添加客户后回调的方法
        Log.i(TAG, "onEventMainThread: 我的任务页面添加后回调的方法");
        queryData();
    }

    @Subscribe
    public void onEventMainThread(EventBusUtilsUpdate event){
        //删除后回调更新UI
        Log.i(TAG, "onEventMainThread: 我的额任务页面删除以及更新的回调");
        queryData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_task_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除EventBus
        EventBus.getDefault().unregister(this);
    }
}