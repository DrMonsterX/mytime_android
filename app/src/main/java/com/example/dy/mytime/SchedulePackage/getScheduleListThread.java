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

public class getScheduleListThread extends Thread {

    private int userId;

    public getScheduleListThread(int userId){
        this.userId=userId;

    }

    final String path="http://119.3.217.215:8081/getScheduleByUser?userId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path + userId).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: " + result);
            try {
                ScheduleController.myScheduleList.clear();
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        int scheduleID = jsonObject.optInt("scheduleId");
                        int position = jsonObject.optInt("position");
                        String scheduleName = jsonObject.optString("scheduleName");
                        String scheduleStartTime = jsonObject.optString("startTime");
                        String scheduleStopTime = jsonObject.optString("finishTime");
                        String scheduleRemark = jsonObject.optString("remark");
                        int scheduleRemind = jsonObject.optInt("isRemind");
                        Schedule new_schedule = new Schedule(scheduleID, position, scheduleName, scheduleStartTime, scheduleStopTime, scheduleRemark, scheduleRemind);
                        ScheduleController.myScheduleList.add(new_schedule);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
