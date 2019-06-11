package com.example.dy.mytime.CompletenessPackage;

import android.util.Log;

import com.example.dy.mytime.UserPackage.UserId;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class updateCompleteThread extends Thread {
    private int completeness;

    public updateCompleteThread(int completeness) {
        this.completeness=completeness;
    }


    final  String path="http://119.3.217.215:8081/updateComplete?userId=";

        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path+Integer.toString(UserId.getInstance().getUserId())+"&weekCompleteness="+Integer.toString(completeness)).build();
            try {
                Response response = client.newCall(request).execute();//发送请求
                String result = response.body().string();
                Log.d(TAG, "result: "+result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
