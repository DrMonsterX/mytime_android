package com.example.dy.mytime.TaskPackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class resortTaskThread extends  Thread {
    private int taskId;
    private int position;

    //重定义构造函数，获取id
    public resortTaskThread(int taskId,int position){
        this.taskId=taskId;
        this.position=position;

    }

    final String path="http://119.3.217.215:8081/resortTask?taskId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+taskId+"&position="+position).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
