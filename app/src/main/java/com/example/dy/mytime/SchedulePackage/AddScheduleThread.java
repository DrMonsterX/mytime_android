package com.example.dy.mytime.SchedulePackage;

import android.util.Log;

import com.example.dy.mytime.UserPackage.RegistController;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class AddScheduleThread extends Thread {
    private int userId;
    private int position;
    private String scheduleName;
    private String scheduleStartTime;
    private String scheduleStopTime;
    private String scheduleRemark;
    private int scheduleRemind;

    public AddScheduleThread(int userId,int position,String scheduleName,String scheduleStartTime,String scheduleStopTime,String scheduleRemark,int scheduleRemind){
        this.userId=userId;
        this.position=position;
        this.scheduleName=scheduleName;
        this.scheduleStartTime=scheduleStartTime;
        this.scheduleStopTime=scheduleStopTime;
        this.scheduleRemark=scheduleRemark;
        this.scheduleRemind=scheduleRemind;
    }

    final String path="http://119.3.217.215:8081/addSchedule?userId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+userId+"&position="+position+"&scheduleName="+scheduleName+"&startTime="+scheduleStartTime+"&stopTime="+scheduleStopTime+"&remark="+scheduleRemark+"&remind="+scheduleRemind).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
