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

public class getNodeByIdThread extends Thread {
    private int taskId;

    public getNodeByIdThread(int taskId) {
       this.taskId=taskId;

    }

    final String path = "http://119.3.217.215:8081/getNodeByTaskId?taskId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path + taskId).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: " + result);
            try {
                TaskController.allNode.clear();
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null) {
                        int nodeID = jsonObject.optInt("nodeId");
                        String nodeName = jsonObject.optString("nodeName");
                        String nodeTime = jsonObject.optString("nodeTime");
                        int isComplete = jsonObject.optInt("isComplete");

                        Node new_node = new Node(nodeID,nodeName,nodeTime,isComplete);
                        TaskController.allNode.add(new_node);
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
