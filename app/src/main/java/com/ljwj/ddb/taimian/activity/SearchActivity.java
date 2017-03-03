package com.ljwj.ddb.taimian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.ljwj.ddb.taimian.R;
import com.ljwj.ddb.taimian.adapter.TemporaryAdapter;
import com.ljwj.ddb.taimian.bean.ClientBean;
import com.ljwj.ddb.taimian.bean.SoonClientDataDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A chang on 2017/3/4.
 */
public class SearchActivity  extends Activity{

    private ListView lvContent;
    private EditText edSearch;
    private long serachTime;
    private TemporaryAdapter serachAdapter;
    private List<ClientBean> list;
    private SoonClientDataDao clientDataDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        lvContent = (ListView) findViewById(R.id.lv_content);
        edSearch = (EditText) findViewById(R.id.ed_text);

    }

    private void initData() {
        serachTime = System.currentTimeMillis();
        list=new ArrayList<>();
        clientDataDao = new SoonClientDataDao(SearchActivity.this);
       // list= clientDataDao.query();
        serachAdapter=new TemporaryAdapter(this,list);

        lvContent.setAdapter(serachAdapter);

    }

    private void initEvent() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                long time = System.currentTimeMillis();
                if (time-serachTime>5000){
                    clientDataDao = new SoonClientDataDao(SearchActivity.this);
                    list= clientDataDao.query();
                    serachAdapter=new TemporaryAdapter(SearchActivity.this,list);
                    lvContent.setAdapter(serachAdapter);
                    serachAdapter.notifyDataSetChanged();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            }
        });
    }
}
