package com.ljwj.ddb.taimian.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.base.BaseFragments;
import com.ljwj.ddb.taimian.utils.SPUtils;
import java.util.ArrayList;

/**
 * 客户列表页面
 */
public class Main_Clientele_Fragment extends BaseFragments implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rg_select;
    private ArrayList<BaseFragments> mListitem;
    public int num;

    @Override
    protected void initListener() {
        rg_select.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (checkedId){
            case R.id.rb_temporary:
                fragmentTransaction.show(mListitem.get(0));
                fragmentTransaction.hide(mListitem.get(1));
                fragmentTransaction.hide(mListitem.get(2));
                fragmentTransaction.commit();
                num = 0;
                break;
            case R.id.rb_measure:
                fragmentTransaction.show(mListitem.get(1));
                fragmentTransaction.hide(mListitem.get(0));
                fragmentTransaction.hide(mListitem.get(2));
                fragmentTransaction.commit();
                num = 1;
                break;
            case R.id.rb_design:
                fragmentTransaction.show(mListitem.get(2));
                fragmentTransaction.hide(mListitem.get(1));
                fragmentTransaction.hide(mListitem.get(0));
                fragmentTransaction.commit();
                num = 2;
                break;
        }
        //保存当前的状态
        SPUtils.putInt(getContext(), "fm", num);
    }

    @Override
    protected void initRefresh() {

    }

    @Override
    protected void initData() {
        mListitem = new ArrayList<>();
        //将4个fragment保存到集合里面
        mListitem.add(new Temporary_fms());
        mListitem.add(new Measure_fms());
        mListitem.add(new Design_fms());

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.addview_fl,mListitem.get(0));
        fragmentTransaction.add(R.id.addview_fl,mListitem.get(1));
        fragmentTransaction.add(R.id.addview_fl,mListitem.get(2));

        fragmentTransaction.hide(mListitem.get(1));
        fragmentTransaction.hide(mListitem.get(2));
        fragmentTransaction.commit();
    }

    @Override
    protected void initView(View view) {
        rg_select = (RadioGroup) view.findViewById(R.id.select_rg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_client_fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        //fragment结束后保存当前的状态
        SPUtils.putInt(getContext(), "fm", 0);
    }
}