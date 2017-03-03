package com.ljwj.ddb.taimian.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 客户资料数据库
 * Created by dell on 2017/2/6.
 */

public class MyDataBaseHeiper extends SQLiteOpenHelper {

    String createTable = "create table clientdata (_id integer primary key autoincrement ,"
            + "userid varchar,"
            + "name varchar,"
            + "sex varchar,"
            + "relation varchar,"
            + "phone integer,"
            + "site varchar,"
            + "state integer,"
            + "time varchar)";

    public MyDataBaseHeiper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
