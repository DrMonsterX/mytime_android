package com.example.dy.mytime.CompletenessPackage;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class getHistoryThread extends Thread {
    private int user_id;
    //重定义构造函数，获取id
    public getHistoryThread(int user_id){
        this.user_id=user_id;
    }
    final String path ="http://119.3.217.215:8081/getHistory?userId=";

    public void run()  {
        Log.e("user id: ", Integer.toString(user_id));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+user_id).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);
            if(result!=null){
                if (result.contains("["))
                {
                    result = result.replace("[", ",");
                }
                if (result.contains("]"))
                {
                    result = result.replace("]", ",");
                }
                String[] history=result.split(",");
                for(int i=0;i<5;i++){
                    CompletenessController.his_com[i]=Integer.parseInt(history[i+1]);
                }
            }
            else {
                for(int i=0;i<5;i++){
                    CompletenessController.his_com[i]=0;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}
