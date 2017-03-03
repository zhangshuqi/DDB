package com.ljwj.ddb.taimian.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 客户资料数据库
 * Created by dell on 2017/2/6.
 */

public class RoomDataBaseHeiper extends SQLiteOpenHelper {

    String createTable = "create table roomdata (_id integer primary key autoincrement ,"
            + "title varchar,"
            + "uid varchar,"
            + "img varchar)";
    private String TAG="RoomDataBaseHeiper";

    public RoomDataBaseHeiper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建时调用的方法
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新时调用的方法
    }
}
