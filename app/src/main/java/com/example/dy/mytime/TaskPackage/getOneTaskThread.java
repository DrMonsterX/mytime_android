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

public class getOneTaskThread extends Thread {

    private int taskId;

    public getOneTaskThread(int taskId) {
        this.taskId = taskId;

    }

    final String path = "http://119.3.217.215:8081/getTaskById?taskId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path + taskId).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: " + result);
            try {
               if(null != result){
                   JSONObject jsonObject =  new JSONObject(result);
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
                   TaskController.task=new_task;
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
