package com.example.dy.mytime.SchedulePackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class ModifyScheduleThread extends Thread {
    private  int scheduleId;
    private String scheduleName;
    private String startTime;
    private String stopTime;
    private String remark;
    private int remind;
    public ModifyScheduleThread(int scheduleId,String schduleName,String startTime,String stopTime,String remark,int remind){
        this.scheduleId=scheduleId;
        this.scheduleName=schduleName;
        this.startTime=startTime;
        this.stopTime=stopTime;
        this.remark=remark;
        this.remind=remind;
    }
    final String path="http://119.3.217.215:8081/modifySchedule?scheduleId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+scheduleId+"&scheduleName="+scheduleName+"&startTime="+startTime+"&stopTime="+stopTime+"&remark="+remark+"&remind="+remind).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
