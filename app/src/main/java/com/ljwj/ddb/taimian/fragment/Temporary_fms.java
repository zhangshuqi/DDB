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
import com.ljwj.ddb.taimian.activity.AddClientActivity;
import com.ljwj.ddb.taimian.activity.ClientDataActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;
import com.ljwj.ddb.taimian.utils.Constant;
import com.ljwj.ddb.taimian.utils.DialogUtils;
import com.ljwj.ddb.taimian.utils.Dp2PxUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtils;
import com.ljwj.ddb.taimian.utils.EventBusUtilsUpdate;
import com.ljwj.ddb.taimian.view.MySwipeToRefresh;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

import static com.ljwj.ddb.taimian.utils.DialogUtils.createDeleteDialog;

/**
 *临时客户页面的fragment
 */
public class Temporary_fms extends BaseFragments implements AdapterView.OnItemClickListener,View.OnClickListener{
    private Dialog mDeleteDialog;
    private SwipeMenuListView temporay_lv;
    private ArrayList<ClientBean> list;
    private PtrClassicFrameLayout main_refresh_view;
    private boolean flag;
    private String TAG="Temporary_fms";
    private TemporaryAdapter tem;
    private RelativeLayout clientele_addclient;
    private Intent mIntent;
    private SoonClientDataDao clientDataDao;
    private MySwipeToRefresh swipeRefreshLayout;

    @Override
    protected void initListener() {
/*        temporay_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                RetrofitManager instance = RetrofitManager.getInstance();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //判断listview的第一个条目是否为完全可见状态，完全可见状态下flag为true才可以下拉刷新，否则为false不可以刷新
                if(temporay_lv.getChildCount() > 0 && temporay_lv.getFirstVisiblePosition() == 0 && temporay_lv.getChildAt(0).getTop() >= 0){
                    flag=true;
                }else {
                    flag=false;
                }
            }
        });*/
/*
        temporay_lv.setOnTouchListener(new View.OnTouchListener() {//触摸监听事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
*/

        //滑动删除
        temporay_lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        if (mDeleteDialog==null) {
                            ClientBean clientBean = list.get(position);
                            String     userId     = clientBean.getUserid();

                            mDeleteDialog = createDeleteDialog(position, getActivity(), userId,
                                                               new DialogUtils.DeleteDialogInterface() {
                                                                   @Override
                                                                   public void positive() {
                                                                       tem.notifyDataSetChanged();
                                                                   }

                                                                   @Override
                                                                   public void negative() {

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



    //下拉刷新的方法
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



        /*main_refresh_view.setLastUpdateTimeRelateObject(this);
        //下拉刷新的阻力，下拉时，下拉距离和显示头部的距离比例，值越大，则越不容易滑动
        main_refresh_view.setRatioOfHeaderHeightToRefresh(2.5f);
        //返回到刷新的位置
        main_refresh_view.setDurationToClose(200);
        //关闭头部的时间
        main_refresh_view.setDurationToCloseHeader(1000);
        //当下拉到一定距离时，自动刷新（true），显示释放以刷新（false）
        main_refresh_view.setPullToRefresh(false);

        main_refresh_view.setKeepHeaderWhenRefresh(true);
        flag=true;
        main_refresh_view.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //根据listview滑动的的状态来决定是否刷新，当滑动到顶部的时flag为true否则为false，为true的时候才可以下拉刷新

                return flag;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //数据刷新的回调
                main_refresh_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        main_refresh_view.refreshComplete();

                    }
                }, 1500);
            }
        });*/


    }

    @Override
    protected void initData() {
        //注册EventBus
        EventBus.getDefault().register(this);
        //初始化获取数据更新UI
        queryData();

        //设置条目监听事件
        temporay_lv.setOnItemClickListener(this);
        temporay_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(),"长暗响应事件",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        clientele_addclient.setOnClickListener(this);
    }

    private void queryData() {
        list=new ArrayList<>();
        clientDataDao = new SoonClientDataDao(getContext());
        list.addAll( clientDataDao.query());//从数据库获取数据
        //设置适配器
        tem=new TemporaryAdapter(getContext(),list);
        temporay_lv.setAdapter(tem);

        tem.notifyDataSetChanged();

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
                deleteItem.setWidth(Dp2PxUtils.dip2px(getContext(),70));
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
        temporay_lv.setMenuCreator(creator);
    }

    //EventBus接收消息回调的方法
    @Subscribe
    public void onEventMainThread(EventBusUtils event){
        ClientBean clientBean = event.getNewClientBean();

        list.add(clientBean);
        //设置适配器
        tem=new TemporaryAdapter(getContext(),list);
        temporay_lv.setAdapter(tem);

        //刷新适配器
        tem.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventMainThread(EventBusUtilsUpdate event){
        //删除后回调更新UI
        queryData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clientele_addclient:
                mIntent=new Intent(getContext(), AddClientActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View view) {
        temporay_lv=(SwipeMenuListView)view.findViewById(R.id.temporay_lv);
        //添加搜索头文件到listview
        View viewHeader = View.inflate(getContext(), R.layout.head_layout, null);
        temporay_lv.addHeaderView(viewHeader);
        clientele_addclient =(RelativeLayout)viewHeader.findViewById(R.id.clientele_addclient);
//        main_refresh_view=(PtrClassicFrameLayout)view.findViewById(R.id.main_refresh_view);
        swipeRefreshLayout = (MySwipeToRefresh)view.findViewById(R.id.swipe_container);
    }

    //获取条目里面的数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ClientBean clientBean = list.get(position-1);
        mIntent=new Intent(getContext(), ClientDataActivity.class);
        mIntent.putExtra(Constant.NAME,clientBean.getName() );
        mIntent.putExtra(Constant.DATE,clientBean.getDate() );
        mIntent.putExtra(Constant.PHONE,clientBean.getPhone() );
        mIntent.putExtra(Constant.SITE,clientBean.getSite() );
        mIntent.putExtra(Constant.SEX,clientBean.getSex() );
        mIntent.putExtra(Constant.RELATION,clientBean.getRelation() );

        String state = String.valueOf(clientBean.getState());
        mIntent.putExtra(Constant.STATE,state);

        Log.i(TAG, "onItemClick: state"+state);

        mIntent.putExtra(Constant.USERID,clientBean.getUserid());

        startActivity(mIntent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.temporary_fm;
    }
}
