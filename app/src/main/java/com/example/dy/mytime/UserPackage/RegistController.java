package com.example.dy.mytime.UserPackage;

import android.util.Log;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class RegistController extends UserController implements IRegist {

    public static int  num;
    public RegistController(){
        super();
    }

    //用户注册
    public int register(String name,int iconid,String code) {
        //执行注册线程
        Thread thread=new RegistTread(name,iconid,code);
        thread.start();
        try
        {
            thread.join();//等待注册线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return num;
    }
}
