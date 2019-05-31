package com.example.dy.mytime.SchedulePackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.UserPackage.UserId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScheduleController implements IGetSchedule{
    private MyDatabaseController controller;
    public ScheduleController(MyDatabaseController myDBC){
        controller=myDBC;
    }

    //获取目标天所有日程
    public ArrayList<Schedule> getScheduleByDay(String date){
        ArrayList<Schedule> myScheduleList=new ArrayList<>();
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Schedule","User");
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        //目标日时间戳
        long aimDate=0;
        try {
            aimDate=sd.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext())
        {
            String scheduleDate=cursor.getString(cursor.getColumnIndex("StartTime"));
            //日程时间戳
            long scheduleTime=0;
            try {
                scheduleTime=sd.parse(scheduleDate).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(scheduleTime==aimDate)
            {
                Schedule mySchedule=new Schedule(cursor.getInt(cursor.getColumnIndex("Schedule_id")),cursor.getInt(cursor.getColumnIndex("Position")),
                                cursor.getString(cursor.getColumnIndex("ScheduleName")),cursor.getString(cursor.getColumnIndex("StartTime")),
                                cursor.getString(cursor.getColumnIndex("FinishTime")),cursor.getString(cursor.getColumnIndex("Remark")),
                                cursor.getInt(cursor.getColumnIndex("IsRemind")));
                myScheduleList.add(mySchedule);
            }
        }
        cursor.close();
        controller.closeDB();
        return myScheduleList;
    }

    //按照ScheduleId获取日程
    public Schedule getScheduleById(int scheduleId){
        Cursor cursor=controller.searchById(scheduleId,"Schedule","Schedule");
        if(cursor.moveToNext()){
            Schedule mySchedule=new Schedule(cursor.getInt(cursor.getColumnIndex("Schedule_id")),cursor.getInt(cursor.getColumnIndex("Position")),
                    cursor.getString(cursor.getColumnIndex("ScheduleName")),cursor.getString(cursor.getColumnIndex("StartTime")),
                    cursor.getString(cursor.getColumnIndex("FinishTime")),cursor.getString(cursor.getColumnIndex("Remark")),
                    cursor.getInt(cursor.getColumnIndex("IsRemind")));
            cursor.close();
            controller.closeDB();
            return mySchedule;
        }
        cursor.close();
        controller.closeDB();
        return null;
    }

    //获取目标日schedule个数
    public int getDBPosition(String startTime){
        int count=0;
        ArrayList<Schedule> myScheduleList=new ArrayList<>();
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Schedule","User");
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        //目标日时间戳
        long aimDate=0;
        try {
            aimDate=sd.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {
            String scheduleDate = cursor.getString(cursor.getColumnIndex("StartTime"));
            //日程时间戳
            long scheduleTime = 0;
            try {
                scheduleTime = sd.parse(scheduleDate).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (scheduleTime == aimDate) {
                count++;
            }
        }
        cursor.close();
        controller.closeDB();
        return count;
    }

    public String getShareCode(int scheduleId){
        Schedule schedule=getScheduleById(scheduleId);
        String result="$"+schedule.getscheduleName()+"$"+schedule.getscheduleStartTime()+"$"+schedule.getscheduleStopTime()+"$"+schedule.getscheduleRemark()
                +"$"+Integer.toString(schedule.getscheduleRemind())+"$";
        return result;
    }
}
