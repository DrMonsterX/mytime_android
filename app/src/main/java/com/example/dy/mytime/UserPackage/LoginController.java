package com.example.dy.mytime.UserPackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class LoginController extends UserController implements ILogin {
    private MyDatabaseController controller;
    public LoginController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //检查用户登录信息
    public int checkLogin(int user_id,String password){
        Cursor cursor=controller.searchById(user_id,"User","User");
        if(cursor.getCount()==0) {
            //用户ID无效
            cursor.close();
            controller.closeDB();
            return 2;
        }
        cursor.moveToNext();
        User user=new User(cursor.getInt(cursor.getColumnIndex("User_id")),cursor.getString(cursor.getColumnIndex("UserName")),
                cursor.getInt(cursor.getColumnIndex("Icon_id")),cursor.getString(cursor.getColumnIndex("Password")),
                cursor.getInt(cursor.getColumnIndex("Completeness_id")));
        if(user.getPassword().equals(password))
        {
            //登录成功
            cursor.close();
            controller.closeDB();
            return 0;
        }
        //密码错误
        cursor.close();
        controller.closeDB();
        return 1;
    }
}
