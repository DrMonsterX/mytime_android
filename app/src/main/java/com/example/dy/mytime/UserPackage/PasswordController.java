package com.example.dy.mytime.UserPackage;

import android.util.Log;

import com.example.dy.mytime.Activity.ChangePasswordActivity;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class PasswordController extends UserController implements IChangePassword {

    public PasswordController(){
        super();

    }

    //修改密码
    public void changePassword(String newPassword){


        //执行修改密码线程
        Thread thread=new PasswordThread(newPassword);
        thread.start();
        try
        {
            thread.join();//等待修改密码线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }



}
