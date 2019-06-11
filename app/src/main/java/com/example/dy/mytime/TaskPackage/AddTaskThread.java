package com.example.dy.mytime.TaskPackage;

import android.util.Log;

import com.example.dy.mytime.CompletenessPackage.CompletenessController;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class AddTaskThread extends Thread {
    private int userId;
    private String taskName;
    private int position;
    private String taskStartTime;
    private String taskStopTime;
    private String tagNum;
    private String taskRemark;
    private int taskRemind;
    //重定义构造函数，获取id
    public AddTaskThread(int userId,int position,String taskName, String taskStartTime, String taskStopTime,String tagNum, String taskRemark,int taskRemind){
        this.userId=userId;
        this.position=position;
        this.taskName=taskName;
        this.taskStartTime=taskStartTime;
        this.taskStopTime=taskStopTime;
        this.tagNum=tagNum;
        this.taskRemark=taskRemark;
        this.taskRemind=taskRemind;

    }

    final String path="http://119.3.217.215:8081/addTask?userId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+userId+"&position="+position+"&taskName="+taskName+"&startTime="+taskStartTime+"&stopTime="+taskStopTime+"&remark="+taskRemark+"&remind="+taskRemind+"&tag="+tagNum).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
