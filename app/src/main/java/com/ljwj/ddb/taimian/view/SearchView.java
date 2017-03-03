package com.ljwj.ddb.taimian.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.activity.SearchActivity;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A chang on 2017/3/4.
 */

public class SearchView extends FrameLayout {

    private ViewGroup rootView;
    private TextView edText;
    private ListView lvSearch;
    private long serachTime;
    private TemporaryAdapter serachAdapter;
    private List<ClientBean> list;
    private SoonClientDataDao clientDataDao;


    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData(context);
        initEvent(context);
    }

    private void initEvent(final Context context) {
        edText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SearchActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void initData(Context context) {
       /* serachTime = System.currentTimeMillis();
        list=new ArrayList<>();
        clientDataDao = new SoonClientDataDao(getContext());
        list= clientDataDao.query();
        serachAdapter=new TemporaryAdapter(getContext(),list);
        lvSearch.setAdapter(serachAdapter);
        serachAdapter.notifyDataSetChanged();*/

    }

    private void initView(Context context) {
        rootView = (ViewGroup) View.inflate(context, R.layout.search,null);
        edText = (TextView) rootView.findViewById(R.id.ed_text);
        //lvSearch = (ListView) rootView.findViewById(R.id.lv_view);
        addView(rootView);
    }

}
