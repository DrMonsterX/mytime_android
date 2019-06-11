package com.example.dy.mytime.TaskPackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class changeNodeThread extends Thread {

    private int nodeId;
    private int finishNum;

    //重定义构造函数，获取id
    public changeNodeThread(int nodeId,int finishNum){
        this.nodeId=nodeId;
        this.finishNum=finishNum;

    }

    final String path="http://119.3.217.215:8081/finishNode?nodeId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+nodeId+"&finishNum="+finishNum).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
