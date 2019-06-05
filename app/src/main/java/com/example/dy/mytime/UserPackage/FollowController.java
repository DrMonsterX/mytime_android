package com.example.dy.mytime.UserPackage;

import android.database.Cursor;
import android.util.Log;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class FollowController extends UserController implements IFollow {

    public static ArrayList<User> myFollow=new ArrayList<>();
    public static int DFollow_code;
//    private MyDatabaseController controller;
    public FollowController(){
        super();
    }

    //获取关注列表
    public ArrayList<User> getFollow(int userId){

        //执行线程获取关注的列表
        Thread thread=new FollowThread(userId);
        thread.start();
        try
        {
            thread.join();//等待子线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return myFollow;
    }

    //关注好友
    public void followUser(final int userId){
        final  String path="http://119.3.217.215:8081/followUser?myId=";

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(path+Integer.toString(UserId.getInstance().getUserId())+"&targetId="+userId).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.d(TAG, "result: "+result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //取消关注
    public void deleteFollow(int userId){
        //执行取消关注的子线程
        Thread thread=new DFollowThread(userId);
        thread.start();
        try
        {
            thread.join();//等待子线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    //获取排名
    public ArrayList<User> getRank(){
        Comparator comparator=new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(getWeekCompleteness(o1.getUserID())>getWeekCompleteness(o2.getUserID())){
                    return -1;
                }
                else
                    return 1;
            }
        };
        ArrayList<User> myRank=new ArrayList<>();
        myRank.add(getUser(UserId.getInstance().getUserId()));
        myRank.addAll(getFollow(UserId.getInstance().getUserId()));
        Collections.sort(myRank,comparator);
        return myRank;
    }
}
