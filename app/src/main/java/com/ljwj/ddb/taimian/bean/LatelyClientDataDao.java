package com.ljwj.ddb.taimian.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ljwj.ddb.taimian.sqlite.MyDataBaseHeiper;

import java.util.ArrayList;
import java.util.List;

/**
 * 最近客户数据库操作类
 * Created by dell on 2017/2/7.
 */

public class LatelyClientDataDao {
    private static final String CLIENT_DATA="ClientData.db";
    private static final int DB_VERSION=1;
    private final MyDataBaseHeiper myDataBaseHeiper;
    private SQLiteDatabase mReadableDatabase;
    private SQLiteDatabase mWritableDatabase;

    public LatelyClientDataDao(Context context){
        //创建数据库
        myDataBaseHeiper = new MyDataBaseHeiper(context, CLIENT_DATA, null, DB_VERSION);
    }

    //添加方法
    public void insert(String userid, String name,String sex, String relation,String phone,String site,String time, Integer state){
        //得到可读的数据库对象
        mReadableDatabase = myDataBaseHeiper.getReadableDatabase();
        //插入
        String insertSql="insert into ClientData (userid, name, sex, relation, phone, site, time,state) values(?,?,?,?,?,?,?,?)";
        //执行一条语句
        mReadableDatabase.execSQL(insertSql, new Object[]{userid, name, sex, relation, phone, site, time,state});
        //关闭数据库
        mReadableDatabase.close();
    }

    //删除方法
    public void delete(String id){
        //可写的数据库
        mWritableDatabase = myDataBaseHeiper.getWritableDatabase();
        //删除语句
        mWritableDatabase.delete("ClientData", "userid"+"=?", new String[]{String.valueOf(id)});
        mWritableDatabase.close();
    }

    //更新数据库
    public void updata(ContentValues values, String userid){
        //可写的数据库
        mWritableDatabase = myDataBaseHeiper.getWritableDatabase();

        mWritableDatabase.update("ClientData",values,"userid"+"=?", new String[] {userid});
    }

    //查询数据库全部数据
    public List< ClientBean> query(){
        ArrayList< ClientBean> list=new ArrayList();

        //得到一个可写的数据库对象
        mReadableDatabase = myDataBaseHeiper.getWritableDatabase();
        //查询数据库所有数据
        Cursor cursor = mReadableDatabase.query("ClientData", null,null, null, null, null, null, null);
        while (cursor.moveToNext()){
            ClientBean bean = new ClientBean();
            String userid = cursor.getString(cursor.getColumnIndex("userid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String relation = cursor.getString(cursor.getColumnIndex("relation"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String site = cursor.getString(cursor.getColumnIndex("site"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            bean.setUserid(userid);
            bean.setState(state);
            bean.setName(name);
            bean.setPhone(phone);
            bean.setRelation(relation);
            bean.setSex(sex);
            bean.setSite(site);
            bean.setDate(time);
            list.add(bean);
        }
        cursor.close();//关闭资源
        mReadableDatabase.close();//关闭资源
        return list;
    }
}
