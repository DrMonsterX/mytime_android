package com.example.dy.mytime.CompletenessPackage;

import android.database.Cursor;
import android.util.Log;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;
import com.example.dy.mytime.UserPackage.UserId;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class CompletenessController  {
    public static int completeness;
    private int new_completeness;
    public static int[] his_com=new int[5];
    public CompletenessController(){

    }

    //更新本周完成度
    public void updateCompleteness(){
        int allTask=0;
        int finishTask=0;
        TaskController taskController=new TaskController();
        ArrayList<Task> tasks = taskController.getAllTask();
        Date now=new Date();
        SimpleDateFormat sd=new SimpleDateFormat("w");
        String nowWeek=sd.format(now);
       for(int i=0;i<tasks.size();i++){
            String stopTime=tasks.get(i).gettaskStopTime();
            SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
            Date task = new Date();
            try {
                task=sd1.parse(stopTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String stopWeek=sd.format(task);
            if(nowWeek.equals(stopWeek)){
                allTask++;
                boolean finish=tasks.get(i).gettaskFinish();
                if(finish){
                    finishTask++;
                }
            }
        }
        double result=(double)finishTask/(double)allTask*100;
        new_completeness=(int)result;
        Thread thread=new updateCompleteThread(new_completeness);
        thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    //获取本周完成度
    public int getWeekCompleteness(){
        Thread thread=new getCompleteThread(UserId.getInstance().getUserId());
        thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return completeness;
    }

    //获取历史完成度
    public int[] getHistoryCompleteness(){
        Thread thread=new getHistoryThread(UserId.getInstance().getUserId());
        thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return his_com;
    }



}
