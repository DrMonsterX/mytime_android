package com.example.dy.mytime.SchedulePackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.UserPackage.UserId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScheduleController implements IGetSchedule {

    public static ArrayList<Schedule> myScheduleList = new ArrayList<>();
    public static Schedule schedule= null;
    public ScheduleController() {

    }

    //获取目标天所有日程
    public ArrayList<Schedule> getScheduleByDay(String date) {
        ArrayList<Schedule> todayScheduleList = new ArrayList<>();
        searchById(UserId.getInstance().getUserId());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        //目标日时间戳
        long aimDate = 0;
        try {
            aimDate = sd.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<myScheduleList.size();i++){
            long scheduleTime = 0;
            try {
                scheduleTime = sd.parse(myScheduleList.get(i).getscheduleStartTime()).getTime();
            }catch (ParseException e){
                e.printStackTrace();
            }

            if(scheduleTime==aimDate){
                todayScheduleList.add(myScheduleList.get(i));
            }
        }

        return todayScheduleList;
    }

    //按照ScheduleId获取日程
    public Schedule getScheduleById(int scheduleId) {
        Thread thread = new getScheduleByIdThread(scheduleId);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    //获取目标日schedule个数
    public int getDBPosition(String startTime) {
        int count = 0;
        searchById(UserId.getInstance().getUserId());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        //目标日时间戳
        long aimDate = 0;
        try {
            aimDate = sd.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<myScheduleList.size();i++){
            long scheduleTime = 0;
            try {
                scheduleTime = sd.parse(myScheduleList.get(i).getscheduleStartTime()).getTime();
            }catch (ParseException e){
                e.printStackTrace();
            }

            if(scheduleTime==aimDate){
                count++;
            }
        }

        return count;
    }

    public String getShareCode(int scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        String result = "$" + schedule.getscheduleName() + "$" + schedule.getscheduleStartTime() + "$" + schedule.getscheduleStopTime() + "$" + schedule.getscheduleRemark()
                + "$" + Integer.toString(schedule.getscheduleRemind()) + "$";
        return result;
    }

    private void searchById(int userId) {
        Thread thread = new getScheduleListThread(userId);
        thread.start();  //执行登录验证线程
        try {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
