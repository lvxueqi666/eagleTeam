package com.example.qiqi.xianwan.homeadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String name = "temp.db";//数据库名字
    private static Integer version = 1;//数据库版本号

    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");//创建表名
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
