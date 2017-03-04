package com.ljwj.ddb.taimian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.ClientDataActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.OfficialClientDataDao;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.DialogUtils;
import com.ljwj.ddb.taimian.utils.Dp2PxUtils;
import com.ljwj.ddb.taimian.utils.EventBusOfficialUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;
import com.ljwj.ddb.taimian.view.MySwipeToRefresh;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.ljwj.ddb.taimian.utils.DialogUtils.createDeleteDialog;


/**
 * 测量阶段fragment
 */
public class Measure_fms extends BaseFragments {
    private Dialog            mDeleteDialog;
    private SwipeMenuListView measure_lv;
    private TemporaryAdapter  temAdapter;
    private String TAG="Measure_fms";
    private ArrayList<ClientBean> list;
    private OfficialClientDataDao offDataDao;
    private Intent                mIntent;
    private int                   clientBean;
    private TemporaryAdapter      tem;
    private MySwipeToRefresh      swipeRefreshLayout;

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
                mIntent.putExtra(Constant.TYPE,clientBean.getType());
                startActivity(mIntent);
            }
        });

        //滑动删除
        measure_lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {


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
        measure_lv.setMenuCreator(creator);
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
        if (event.getUpdate().equals("1"))
        queryData();
    }

    @Override
    protected void initView(View view) {
        measure_lv=(SwipeMenuListView)view.findViewById(R.id.measure_lv);
        swipeRefreshLayout = (MySwipeToRefresh)view.findViewById(R.id.swipeRefreshLayout);
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
