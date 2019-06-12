package com.example.dy.mytime.TaskPackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class ChangeTaskThread extends Thread {

    private int taskId;
    private String taskName;
    private String startTime;
    private String stopTime;
    private int remind;
    private String tag;
    private String remark;
    //重定义构造函数，获取id
    public ChangeTaskThread(int taskId,String taskName,String startTime,String stopTime,int remind,String tag,String remark){
        this.taskId=taskId;
        this.taskName=taskName;
        this.startTime=startTime;
        this.stopTime=stopTime;
        this.remind=remind;
        this.tag=tag;
        this.remark=remark;



    }

    final String path="http://119.3.217.215:8081/modifyTask?taskId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+taskId+"&taskName="+taskName+"&startTime="+startTime+"&stopTime="+stopTime+"&remark="+remark+"&remind="+remind+"&tag="+tag).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
