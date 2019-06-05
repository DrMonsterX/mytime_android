package com.example.dy.mytime.SchedulePackage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class getScheduleByIdThread extends Thread{
    private int schedule_id;

    public getScheduleByIdThread(int schedule_id){
        this.schedule_id=schedule_id;

    }

    final String path="http://119.3.217.215:8081/getScheduleById?scheduleId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path + schedule_id).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: " + result);
            ScheduleController.schedule=null;
            if(result!=null){
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int scheduleID = jsonObject.optInt("scheduleId");
                    int position = jsonObject.optInt("position");
                    String scheduleName = jsonObject.optString("scheduleName");
                    String scheduleStartTime = jsonObject.optString("startTime");
                    String scheduleStopTime = jsonObject.optString("finishTime");
                    String scheduleRemark = jsonObject.optString("remark");
                    int scheduleRemind = jsonObject.optInt("isRemind");
                    Schedule new_schedule = new Schedule(scheduleID, position, scheduleName, scheduleStartTime, scheduleStopTime, scheduleRemark, scheduleRemind);
                    ScheduleController.schedule=new_schedule;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
