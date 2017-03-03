package com.ljwj.ddb.taimian.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ljwj.ddb.taimian.sqlite.RoomDataBaseHeiper;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间类型数据库操作类
 * Created by dell on 2017/2/7.
 */

public class NewRoomClientDataDao {
    private static final String CLIENT_DATA="NewRoomData.db";
    private static final int DB_VERSION=1;
    private final RoomDataBaseHeiper roomDataBaseHeiper;
    private SQLiteDatabase readableDatabase;
    private String TAG="NewRoomClientDataDao";

    public NewRoomClientDataDao(Context context){
        //创建数据库
        roomDataBaseHeiper =new RoomDataBaseHeiper(context, CLIENT_DATA, null, DB_VERSION);
    }

    //添加方法
    public void insert(String title, String uid, String img){
        //得到可读的数据库对象
        readableDatabase = roomDataBaseHeiper.getReadableDatabase();
        //插入
        String insertSql="insert into roomdata (title, uid, img) values(?,?,?)";
        //执行一条语句
        readableDatabase.execSQL(insertSql, new Object[]{title, uid, img});
        //关闭数据库
        readableDatabase.close();
    }

    //更新数据库
    public void updata(ContentValues values, String userid){
        //可写的数据库
        readableDatabase = roomDataBaseHeiper.getWritableDatabase();

        readableDatabase.update("roomdata",values,"uid"+"=?", new String[] {userid});
    }

    //删除方法
    public void delete(String id){
        //可写的数据库
        SQLiteDatabase writableDatabase = roomDataBaseHeiper.getWritableDatabase();
        //删除语句
        writableDatabase.delete("roomdata", "uid"+"=?", new String[]{String.valueOf(id)});
        writableDatabase.close();
    }

    //查询数据库全部数据
    public List< NewRoomBean> query(String id){
        ArrayList< NewRoomBean> list=new ArrayList();
        Log.i(TAG, "queryData: 当前用户ID----数据库"+id);
        //得到一个可写的数据库对象
        readableDatabase = roomDataBaseHeiper.getReadableDatabase();
        //查询数据库所有数据
        Cursor cursor = readableDatabase.query("roomdata",
                new String[]{"_id", "title", "uid", "img"}, "uid=?", new String[]{id}, null, null, null);
        while (cursor.moveToNext()){
            Log.i(TAG, "queryData: cursor进来了");
            NewRoomBean bean = new NewRoomBean();
            String userid = cursor.getString(cursor.getColumnIndex("title"));
            String name = cursor.getString(cursor.getColumnIndex("uid"));
            String img = cursor.getString(cursor.getColumnIndex("img"));

            bean.setmTitle(userid);
            bean.setmUis(name);
            bean.setmImage(img);
            list.add(bean);
        }
        cursor.close();//关闭资源
        readableDatabase.close();//关闭资源
        return list;
    }
}
