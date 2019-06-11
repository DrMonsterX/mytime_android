package com.example.dy.mytime.CompletenessPackage;

import android.util.Log;

import com.example.dy.mytime.UserPackage.LoginController;
import com.example.dy.mytime.UserPackage.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class getCompleteThread extends Thread{


    private int user_id;
    //重定义构造函数，获取id
    public getCompleteThread(int user_id){
        this.user_id=user_id;
    }
    final String path ="http://119.3.217.215:8081/getComplete?userId=";

    public void run()  {
        Log.e("user id: ", Integer.toString(user_id));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+user_id).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "complete: "+result);
            if(null != result){
               CompletenessController.completeness=Integer.parseInt(result);
            }
            else {
                CompletenessController.completeness=0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
