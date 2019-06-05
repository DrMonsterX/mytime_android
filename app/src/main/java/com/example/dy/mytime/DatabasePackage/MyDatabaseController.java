package com.example.dy.mytime.DatabasePackage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseController {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public MyDatabaseController(MyDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }

    //以id搜索所有信息
    public Cursor searchById(int id,String tablename,String target)
    {
        db=dbHelper.getWritableDatabase();
        String cmd="select * from "+tablename+" where "+target+"_id="+Integer.toString(id);
        Cursor cursor=db.rawQuery(cmd,null);
        return cursor;
    }

//搜索本地登录数据
    public Cursor searchId()
    {
        db=dbHelper.getWritableDatabase();
        String cmd="select * from User";
        Cursor cursor=db.rawQuery(cmd,null);
        return cursor;
    }

    //插入用户id
    public void InsertID(String userId){

    }




    //获取当前表内的所有项
    public Cursor searchAll(String tablename){
        db=dbHelper.getWritableDatabase();
        String cmd="select * from "+tablename;
        Cursor cursor=db.rawQuery(cmd,null);
        return cursor;
    }

    //获取当前表内符合要求的所有项
    public Cursor searchByTarget(String tablename,String targetName1,String targetResult1,String targetName2,String targetResult2){
        db=dbHelper.getWritableDatabase();
        String cmd="select * from "+tablename+" where "+targetName1+"="+targetResult1+" and "+targetName2+"="+targetResult2;
        Cursor cursor=db.rawQuery(cmd,null);
        return cursor;
    }

    //获取一个表的当前行数
    public int getCount(String tablename)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String all="select * from "+tablename;
        Cursor cursor=db.rawQuery(all,null);
        int num=0;
        if (cursor.moveToFirst()){
            num=cursor.getCount();
        }
        System.out.println(num);
        cursor.close();
        db.close();
        return num;
    }

    //获取符合某条件的当前行数
    public int getCount(String tablename,String targetName,String targetResult)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String all="select * from "+tablename+" where "+targetName+"="+targetResult;
        Cursor cursor=db.rawQuery(all,null);
        int num=0;
        if (cursor.moveToFirst()){
            num=cursor.getCount();
        }
        System.out.println(num);
        cursor.close();
        db.close();
        return num;
    }

    //修改数据库表信息
    public void modify(String command)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(command);
        db.close();
    }

    //获取目标表目标列的最大值
    public int getMax(String tablename,String target)
    {
        db=dbHelper.getWritableDatabase();
        String cmd="select max("+target+"_id) from "+tablename;
        Cursor cursor=db.rawQuery(cmd,null);
        cursor.moveToNext();
        int max=cursor.getInt(cursor.getColumnIndex("max("+target+"_id)"));
        cursor.close();
        db.close();
        return max;
    }

    //以Id为依据删除某一项
    public void deleteById(String table_name,String keyword,int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String cmd="delete from "+table_name+" where "+keyword+"="+Integer.toString(id);
        db.execSQL(cmd);
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
