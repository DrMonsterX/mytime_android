package com.example.dy.mytime.SchedulePackage;

import android.util.Log;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;

public class AddScheduleController extends ScheduleController implements IAddSchedule {
    public AddScheduleController(){
        super();

    }
    //添加日程
    public void addSchedule(String scheduleName, String scheduleStartTime, String scheduleStopTime, String scheduleRemark,int scheduleRemind){
        int userId=UserId.getInstance().getUserId();
        int position=getDBPosition(scheduleStartTime);
        Log.e("schedule",userId+"&position="+position+"&scheduleName="+scheduleName+"&startTime="+scheduleStartTime+"&stopTime="+scheduleStopTime+"&remark="+scheduleRemark+"&remind="+scheduleRemind);
        Thread thread = new AddScheduleThread(userId,position,scheduleName,scheduleStartTime,scheduleStopTime,scheduleRemark,scheduleRemind);
        thread.start();  //执行线程
        try {
            thread.join();//等待子线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //通过分享码添加日程
    public void addScheduleByString(String str){
        int index = str.indexOf("$");
        if(index==-1)
            return;
        String name=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String startTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String stopTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String remark=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        int remindNum=Integer.parseInt(str.substring(0,index));
        addSchedule(name,startTime,stopTime,remark,remindNum);
    }
}
