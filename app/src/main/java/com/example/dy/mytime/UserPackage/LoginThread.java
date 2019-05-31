package com.example.dy.mytime.UserPackage;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
    //新增登录线程类，用于处理登录验证的id,并将返回的相关结果赋值给user
public class LoginThread extends Thread {

        private int user_id;
    //重定义构造函数，获取id
    public LoginThread(int user_id){
        this.user_id=user_id;
    }
     final String path ="http://119.3.217.215:8081/checkLogin?userId=";

    public void run()  {
            Log.e("user id: ", Integer.toString(user_id));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path+user_id).build();
            try {
                Response response = client.newCall(request).execute();//发送请求
                String result = response.body().string();
                Log.d(TAG, "result: "+result);
                            if(result!=null){
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int id = jsonObject.optInt("userId");
                    String name=jsonObject.optString("name");
                    int iconid=jsonObject.optInt("iconId");
                    String password=jsonObject.optString("password");
                    int completenessId=jsonObject.optInt("completenessId");
                    User user=new User(id,name,iconid,password,completenessId);
                    LoginController.user=user;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            else {
                LoginController.user=null;
            }

            } catch (IOException e) {
                e.printStackTrace();
            }




    }




}
