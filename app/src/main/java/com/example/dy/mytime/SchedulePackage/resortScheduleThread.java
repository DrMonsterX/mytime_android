package com.example.dy.mytime.SchedulePackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class resortScheduleThread extends Thread {
    private int scheduleId;
    private int position;
    public resortScheduleThread(int scheduleId,int position){
        this.scheduleId=scheduleId;
        this.position=position;
    }

    final String path="http://119.3.217.215:8081/resortSchedule?scheduleId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+scheduleId+"&position="+position).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
