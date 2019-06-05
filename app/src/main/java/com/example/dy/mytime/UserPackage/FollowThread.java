package com.example.dy.mytime.UserPackage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class FollowThread extends Thread {
    private int userId;
    public FollowThread(int userId){
        this.userId=userId;
    }


    final String path="http://119.3.217.215:8081/getFollow?userId=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+userId).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.e( "follow: ",result);
            try {
                FollowController.myFollow.clear();
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject != null){
                        int id = jsonObject.optInt("userId");
                        String name=jsonObject.optString("name");
                        int iconId=jsonObject.optInt("iconId");
                        String password=jsonObject.optString("password");
                        int completenessId=jsonObject.optInt("completenessId");
                        User followed=new User(id,name,iconId,password,completenessId);
                        FollowController.myFollow.add(followed);
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
