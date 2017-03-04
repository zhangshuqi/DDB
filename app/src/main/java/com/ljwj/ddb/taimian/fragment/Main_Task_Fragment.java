package com.ljwj.ddb.taimian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.ClientDataActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.LatelyClientDataDao;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.DialogUtils;
import com.ljwj.ddb.taimian.utils.Dp2PxUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;
import com.ljwj.ddb.taimian.view.MySwipeToRefresh;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.ljwj.ddb.taimian.utils.DialogUtils.createDeleteDialog;

/**
 * 我的任务页面
 * Created by dell on 2016/12/20.
 */

public class Main_Task_Fragment extends BaseFragments implements View.OnClickListener{
    private RelativeLayout task_rl;
    private SwipeMenuListView maintask_lv;
    private ArrayList<ClientBean> list;
    private TemporaryAdapter tem;
    private Intent mIntent;
    private LatelyClientDataDao latelyDataDao;
    private String TAG="Main_Task_Fragment";
    private MySwipeToRefresh swipeRefreshLayout;
    private Dialog           mDeleteDialog;

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
                mIntent.putExtra(Constant.TYPE,clientBean.getType());
                startActivity(mIntent);
            }
        });

        //滑动删除
        maintask_lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        if (mDeleteDialog==null) {
                            ClientBean clientBean = list.get(position);
                            String     userId     = clientBean.getUserid();
                            int state = clientBean.getState();
                            mDeleteDialog = createDeleteDialog(state, getActivity(), userId,
                                                               new DialogUtils.DeleteDialogInterface() {
                                                                   @Override
                                                                   public void positive() {
                                                                       tem.notifyDataSetChanged();
                                                                       mDeleteDialog=null;
                                                                   }

                                                                   @Override
                                                                   public void negative() {
                                                                       mDeleteDialog=null;
                                                                   }
                                                               });

                        }
                        mDeleteDialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Override
    protected void initRefresh() {
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //数据刷新的回调
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        // TODO Auto-generated method stub
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 1500);

            }
        });
    }

    @Override
    protected void initData() {
        //初始化加载数据
        queryData();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                                                     0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(Dp2PxUtils.dip2px(getContext(), 70));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };
        // set creator
        maintask_lv.setMenuCreator(creator);
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
        maintask_lv=(SwipeMenuListView)view.findViewById(R.id.maintask_lv);
        swipeRefreshLayout = (MySwipeToRefresh)view.findViewById(R.id.swipeRefreshLayout);
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