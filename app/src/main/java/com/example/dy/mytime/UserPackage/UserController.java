package com.example.dy.mytime.UserPackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class UserController implements IGetUser{
    private MyDatabaseController controller;
    public UserController(){
//        controller=myDBC;
    }

    //获取目标ID用户
    public User getUser(int userId){
        return LoginController.user;
    }

    //获取用户本周完成度
    public int getWeekCompleteness(int userId){
        Cursor cursor=controller.searchById(userId,"Completeness","Completeness");
        cursor.moveToNext();
        int result=cursor.getInt(cursor.getColumnIndex("WeekCompleteness"));
        return result;
    }

}
