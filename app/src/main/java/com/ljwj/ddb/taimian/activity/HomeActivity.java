package com.ljwj.ddb.taimian.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.fragment.Main_Clientele_Fragment;
import com.ljwj.ddb.taimian.fragment.Main_Task_Fragment;
import com.ljwj.ddb.taimian.fragment.Main_MyData_Fragment;
import com.ljwj.ddb.taimian.fragment.Main_Table_Fragment;
import com.ljwj.ddb.taimian.utils.AndroidBugWorkaround;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;

/**
 * 主页面
 * Created by dell on 2016/12/20.
 */

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private ArrayList<BaseFragments> list;
    private Fragment mFragment=null;
    private RadioGroup home_rg;
    private String TAG="HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        AndroidBugWorkaround.assistActivity(findViewById(R.id.home_ll),40);
        initView();
        initData();
        initListener();
        switchFragment(list.get(0));
    }
    //初始化数据
    private void initData() {
        list=new ArrayList<>();
        //将Fragment添加到集合当中
        list.add(new Main_Task_Fragment());
        list.add(new Main_Clientele_Fragment());
        list.add(new Main_Table_Fragment());
        list.add(new Main_MyData_Fragment());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_fm,list.get(0));
        fragmentTransaction.add(R.id.main_fm,list.get(1));
        fragmentTransaction.add(R.id.main_fm,list.get(2));
        fragmentTransaction.add(R.id.main_fm,list.get(3));

        fragmentTransaction.hide(list.get(1));
        fragmentTransaction.hide(list.get(2));
        fragmentTransaction.hide(list.get(3));
        fragmentTransaction.commit();

    }
    //初始化监听事件
    private void initListener() {

        home_rg.setOnCheckedChangeListener(this);
    }
    //初始化View
    private void initView() {
        home_rg=(RadioGroup)findViewById(R.id.home_rg);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkdId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (checkdId){
            case R.id.home_mission:
                mFragment = list.get(0);
                fragmentTransaction.show(list.get(0));
                fragmentTransaction.hide(list.get(1));
                fragmentTransaction.hide(list.get(2));
                fragmentTransaction.hide(list.get(3));
                fragmentTransaction.commit();

                break;
            case R.id.home_clientele:
                mFragment = list.get(1);
                fragmentTransaction.show(list.get(1));
                fragmentTransaction.hide(list.get(0));
                fragmentTransaction.hide(list.get(2));
                fragmentTransaction.hide(list.get(3));
                fragmentTransaction.commit();
                break;
            case R.id.home_table:
                mFragment = list.get(2);
                fragmentTransaction.show(list.get(2));
                fragmentTransaction.hide(list.get(1));
                fragmentTransaction.hide(list.get(0));
                fragmentTransaction.hide(list.get(3));
                fragmentTransaction.commit();
                break;
            case R.id.hoeme_my:
                mFragment = list.get(3);
                fragmentTransaction.show(list.get(3));
                fragmentTransaction.hide(list.get(1));
                fragmentTransaction.hide(list.get(2));
                fragmentTransaction.hide(list.get(0));
                fragmentTransaction.commit();
                break;
        }
        switchFragment(mFragment);
    }
    /**
     * 切換Fragment
     */
    public void switchFragment(Fragment fragment){
        //FragmentTransaction transaction =this.getSupportFragmentManager().beginTransaction();
        //切换fragment
        // transaction.replace(R.id.main_fm,fragment);
       // transaction.commit();
    }
}
