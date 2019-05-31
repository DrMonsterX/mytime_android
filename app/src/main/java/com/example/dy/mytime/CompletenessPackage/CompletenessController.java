package com.example.dy.mytime.CompletenessPackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletenessController implements ICompleteness {
    private MyDatabaseController controller;
    public CompletenessController(MyDatabaseController myDBC){
        controller=myDBC;
    }

    //更新本周完成度
    public void updateCompleteness(){
        int allTask=0;
        int finishTask=0;
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Task","User");
        Date now=new Date();
        SimpleDateFormat sd=new SimpleDateFormat("w");
        String nowWeek=sd.format(now);
        while(cursor.moveToNext()){
            String stopTime=cursor.getString(cursor.getColumnIndex("FinishTime"));
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
                int finish=cursor.getInt(cursor.getColumnIndex("IsComplete"));
                if(finish==1){
                    finishTask++;
                }
            }
        }
        cursor.close();
        controller.closeDB();
        double result=(double)finishTask/(double)allTask*100;
        int resultCompleteness=(int)result;
        String cmd="update Completeness set WeekCompleteness="+Integer.toString(resultCompleteness)+" where Completeness_id="+Integer.toString(UserId.getInstance().getUserId());
        controller.modify(cmd);
    }

    //获取本周完成度
    public int getWeekCompleteness(){
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Completeness","Completeness");
        cursor.moveToNext();
        int result=cursor.getInt(cursor.getColumnIndex("WeekCompleteness"));
        cursor.close();
        controller.closeDB();
        return result;
    }

    //获取历史完成度
    public int[] getHistoryCompleteness(){
        int[] history=new int[5];
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Completeness","Completeness");
        cursor.moveToNext();
        int value=0;
        value=cursor.getInt(cursor.getColumnIndex("History_one"));
        history[0]=value;
        value=cursor.getInt(cursor.getColumnIndex("History_two"));
        history[1]=value;
        value=cursor.getInt(cursor.getColumnIndex("History_three"));
        history[2]=value;
        value=cursor.getInt(cursor.getColumnIndex("History_four"));
        history[3]=value;
        value=cursor.getInt(cursor.getColumnIndex("History_five"));
        history[4]=value;
        cursor.close();
        controller.closeDB();
        return history;
    }

    //每周更新历史完成度
    public void updateHistory(){
        Cursor userCursor=controller.searchAll("User");
        while (userCursor.moveToNext()){
            int userId=userCursor.getInt(userCursor.getColumnIndex("User_id"));
            Cursor cursor=controller.searchById(userId,"Completeness","Completeness");
            cursor.moveToNext();
            int[] history=new int[5];
            history[0]=cursor.getInt(cursor.getColumnIndex("WeekCompleteness"));
            history[1]=cursor.getInt(cursor.getColumnIndex("History_one"));
            history[2]=cursor.getInt(cursor.getColumnIndex("History_two"));
            history[3]=cursor.getInt(cursor.getColumnIndex("History_three"));
            history[4]=cursor.getInt(cursor.getColumnIndex("History_four"));
            cursor.close();
            String cmd="update Completeness set WeekCompleteness=0,History_one="+Integer.toString(history[0])+",History_two"+Integer.toString(history[1])+
                    ",History_three="+Integer.toString(history[2])+",History_four="+Integer.toString(history[3])+",History_five="+Integer.toString(history[4])+
                    " where Completeness="+Integer.toString(userId);
            controller.modify(cmd);
        }
    }

}
