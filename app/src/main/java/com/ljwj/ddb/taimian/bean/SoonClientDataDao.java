package com.ljwj.ddb.taimian.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ljwj.ddb.taimian.sqlite.MyDataBaseHeiper;

import java.util.ArrayList;
import java.util.List;

/**
 * 临时客户列表数据库操作类
 * Created by dell on 2017/2/7.
 */

public class SoonClientDataDao {
    private static final String CLIENT_DATA="ClientData.db";
    private static final int DB_VERSION=1;
    private final MyDataBaseHeiper myDataBaseHeiper;
    private SQLiteDatabase readableDatabase;

    public SoonClientDataDao(Context context){
        //创建数据库
        myDataBaseHeiper = new MyDataBaseHeiper(context, CLIENT_DATA, null, DB_VERSION);
    }

    //添加方法
    public void insert(String userid, String name, String sex, String relation, String phone, String site, String time, Integer state, Integer type){
        //得到可读的数据库对象
        readableDatabase = myDataBaseHeiper.getReadableDatabase();
        //插入
        String insertSql="insert into ClientData (userid, name, sex, relation, phone, site, time,state,type) values(?,?,?,?,?,?,?,?,?)";
        //执行一条语句
        readableDatabase.execSQL(insertSql, new Object[]{userid, name, sex, relation, phone, site, time,state,type});
        //关闭数据库
        readableDatabase.close();
    }

    //删除方法
    public void delete(String id){
        //可写的数据库
        SQLiteDatabase writableDatabase = myDataBaseHeiper.getWritableDatabase();
        //删除语句
        writableDatabase.delete("ClientData", "userid"+"=?", new String[]{String.valueOf(id)});
        writableDatabase.close();
    }

    //查询数据库全部数据
    public List< ClientBean> query(){
        readableDatabase = myDataBaseHeiper.getWritableDatabase();
        Cursor cursor = readableDatabase.query("ClientData", null,"state=?", new String[]{"0"}, null, null, null, null);
        List<ClientBean> clientBeen = queryClientData(cursor);
        cursor.close();//关闭资源
        readableDatabase.close();//关闭资源
        return clientBeen;
    }

    public List<ClientBean> queryContent(String searchContent) {
        readableDatabase = myDataBaseHeiper.getWritableDatabase();
        Cursor cursor = readableDatabase.query("ClientData", null," name like?", new String[]{"%"+searchContent+"&"}, null, null, null, null);
        List<ClientBean> clientBeen = queryClientData(cursor);
        cursor.close();//关闭资源
        readableDatabase.close();//关闭资源
        return clientBeen;
    }
    @NonNull
    private List<ClientBean> queryClientData(Cursor cursor) {
        //得到一个可写的数据库对象

        ArrayList< ClientBean> list=new ArrayList();
        while (cursor.moveToNext()){
            ClientBean bean = new ClientBean();
            String userid = cursor.getString(cursor.getColumnIndex("userid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String relation = cursor.getString(cursor.getColumnIndex("relation"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String site = cursor.getString(cursor.getColumnIndex("site"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int type =cursor.getInt(cursor.getColumnIndex("type"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            bean.setUserid(userid);
            bean.setState(state);
            bean.setName(name);
            bean.setPhone(phone);
            bean.setRelation(relation);
            bean.setSex(sex);
            bean.setSite(site);
            bean.setDate(time);
            bean.setType(type);
            list.add(bean);
        }

        return list;
    }
}
