package com.example.dy.mytime.DatabasePackage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseController {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public MyDatabaseController(MyDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }



//搜索本地登录数据
    public Cursor searchId()
    {
        db=dbHelper.getWritableDatabase();
        String cmd="select * from User";
        Cursor cursor=db.rawQuery(cmd,null);
        return cursor;
    }





    //修改数据库表信息
    public void modify(String command)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(command);
        db.close();
    }





    public void deleteId(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String cmd="delete from User";
        db.execSQL(cmd);
        db.close();
    }


    public void closeDB(){
        db.close();
    }
}
