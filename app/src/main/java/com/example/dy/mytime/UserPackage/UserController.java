package com.example.dy.mytime.UserPackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class UserController implements IGetUser{
    private MyDatabaseController controller;
    public UserController(MyDatabaseController myDBC){
        controller=myDBC;
    }

    //获取目标ID用户
    public User getUser(int userId){
        Cursor cursor=controller.searchById(userId,"User","User");
        if(cursor.moveToNext()){
            User user=new User(cursor.getInt(cursor.getColumnIndex("User_id")),cursor.getString(cursor.getColumnIndex("UserName")),
                    cursor.getInt(cursor.getColumnIndex("Icon_id")),cursor.getString(cursor.getColumnIndex("Password")),
                    cursor.getInt(cursor.getColumnIndex("Completeness_id")));
            cursor.close();
            controller.closeDB();
            return user;
        }
        cursor.close();
        controller.closeDB();
        return null;
    }

    //获取用户本周完成度
    public int getWeekCompleteness(int userId){
        Cursor cursor=controller.searchById(userId,"Completeness","Completeness");
        cursor.moveToNext();
        int result=cursor.getInt(cursor.getColumnIndex("WeekCompleteness"));
        return result;
    }

}
