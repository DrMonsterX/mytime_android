package com.example.dy.mytime.UserPackage;

import android.database.Cursor;

import com.example.dy.mytime.CompletenessPackage.CompletenessController;
import com.example.dy.mytime.CompletenessPackage.getCompleteThread;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class UserController implements IGetUser{
    private MyDatabaseController controller;
    public UserController(){
//        controller=myDBC;
    }

    //获取目标ID用户
    public User getUser(int userId){
        LoginController.user=null;
        Thread thread=new LoginThread(userId);
        thread.start();  //执行登录验证线程
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return LoginController.user;
    }

    //获取用户本周完成度
    public int getWeekCompleteness(int userId){
        Thread thread=new getCompleteThread(userId);
        thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return CompletenessController.completeness;
    }

}
