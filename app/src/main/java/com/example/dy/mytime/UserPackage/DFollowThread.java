package com.example.dy.mytime.UserPackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class DFollowThread extends Thread{
    private int userId;

    public DFollowThread(int userId){
        this.userId=userId;
    }

    final  String path="http://119.3.217.215:8081/deleteFollow?myId=";

        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path+Integer.toString(UserId.getInstance().getUserId())+"&targetId="+userId).build();
            try {
                Response response = client.newCall(request).execute();//发送请求
                String result = response.body().string();
                Log.e( "result: ",result);
                FollowController.DFollow_code=Integer.parseInt(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




}
