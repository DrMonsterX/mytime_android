package com.example.dy.mytime.TaskPackage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class getTaskByIdThread extends Thread {

    private int userId;
    private String tags;

    public getTaskByIdThread(int userId, String tags) {
        this.userId = userId;
        this.tags = tags;

    }

    final String path = "http://119.3.217.215:8081/getTaskByTag?userId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path + userId + "&tag="+tags).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: " + result);
            try {
                TaskController.TaskByTag.clear();
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        int taskID = jsonObject.optInt("taskId");
                        String taskName = jsonObject.optString("taskName");
                        String taskStartTime = jsonObject.optString("startTime");
                        String taskStopTime = jsonObject.optString("finishTime");
                        String taskRemark = jsonObject.optString("remark");
                        int taskRemind = jsonObject.optInt("remind");
                        int taskFinished = jsonObject.optInt("isComplete");
                        String taskTag = jsonObject.optString("tag");
                        int taskPosition = jsonObject.optInt("position");

                        Task new_task = new Task(taskID, taskName, taskStartTime, taskStopTime, taskRemark, taskRemind, taskFinished, taskTag, taskPosition);
                        TaskController.TaskByTag.add(new_task);
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
