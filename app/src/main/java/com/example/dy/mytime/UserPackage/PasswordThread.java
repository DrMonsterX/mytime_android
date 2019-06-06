package com.example.dy.mytime.UserPackage;

import android.util.Log;

import com.example.dy.mytime.Activity.ChangePasswordActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


//修改密码请求线程
public class PasswordThread extends Thread{
    private String password;
    public PasswordThread(String password){
        this.password=password;
    }

    final String path="http://119.3.217.215:8081/changePassword?userId=";


        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path+Integer.toString(UserId.getInstance().getUserId())+"&password="+Integer.toString(password.hashCode())).build();
            try {
                Response response = client.newCall(request).execute();//发送请求
                String result = response.body().string();
                Log.d(TAG, "result: "+result);

                ChangePasswordActivity.return_code=Integer.parseInt(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






}
