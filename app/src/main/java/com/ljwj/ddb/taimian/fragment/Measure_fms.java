package com.ljwj.ddb.taimian.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.ClientDataActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.OfficialClientDataDao;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.EventBusOfficialUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


/**
 * 测量阶段fragment
 */
public class Measure_fms extends BaseFragments {
    private ListView measure_lv;
    private TemporaryAdapter temAdapter;
    private String TAG="Measure_fms";
    private ArrayList<ClientBean> list;
    private OfficialClientDataDao offDataDao;
    private Intent mIntent;
    private int clientBean;
    private TemporaryAdapter tem;

    @Override
    protected void initListener() {
        queryData();

        measure_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    //初始化数据库数据
    private void queryData() {
        list=new ArrayList<>();
        offDataDao=new OfficialClientDataDao(getContext());
        list.addAll( offDataDao.query());//从数据库获取数据
        //设置适配器数据
        temAdapter =new TemporaryAdapter(getContext(),list);
        measure_lv.setAdapter(temAdapter);
        //刷新数据
        temAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRefresh() {

    }

    @Override
    protected void initData() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //初始化刷新数据
        queryData();
    }

    //EventBus接收消息回调的方法
    @Subscribe
    public void onEventMainThread(EventBusOfficialUtils event){
        //由临时客户转测量客户回调
       // queryData();
        ClientBean newClientBean = event.getNewClientBean();

        list.add(newClientBean);
        //设置适配器
        tem=new TemporaryAdapter(getContext(),list);
        measure_lv.setAdapter(tem);

        //刷新适配器
        tem.notifyDataSetChanged();

    }

    @Subscribe
    public void onEventMainThread(EventBusUtilsUpdate event){
        //删除后回调更新UI
        queryData();
    }

    @Override
    protected void initView(View view) {
        measure_lv=(ListView)view.findViewById(R.id.measure_lv);
    }

    @Override
    public int getLayoutId() {
        return R.layout.measure_fm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除EventBus
        EventBus.getDefault().unregister(this);
    }
}
