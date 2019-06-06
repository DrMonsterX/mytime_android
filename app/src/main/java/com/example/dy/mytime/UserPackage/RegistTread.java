package com.example.dy.mytime.UserPackage;

import android.util.Log;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static android.content.ContentValues.TAG;



//注册用的线程
public class RegistTread extends Thread{

    private String name;
    private int iconId;
    private String code;

//重定义构造函数，获取name,iconId,code等参数
    public RegistTread (String name,int iconid,String code){
        this.name=name;
        this.iconId=iconid;
        this.code=code;
    }

     final String path="http://119.3.217.215:8081/regist?name=";

    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path+name+"&iconId="+iconId+"&password="+Integer.toString(code.hashCode())).build();
        try {
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body().string();
            Log.d(TAG, "result: "+result);
            RegistController.num=Integer.parseInt(result);//传递参数num给RegistController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
