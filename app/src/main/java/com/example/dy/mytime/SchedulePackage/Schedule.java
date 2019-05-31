package com.example.dy.mytime.SchedulePackage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private int scheduleID;
    private int position;
    private String scheduleName;
    private String scheduleStartTime;
    private String scheduleStopTime;
    private String scheduleRemark;
    private int scheduleRemind;



    public Schedule(int scheduleID,String scheduleName, String scheduleStartTime, String scheduleStopTime) {
        this.scheduleID = scheduleID;
        this.scheduleName = scheduleName;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleStopTime = scheduleStopTime;
        this.scheduleRemark = "";
    }

    public Schedule(int scheduleID,String scheduleName, String scheduleStartTime, String scheduleStopTime, String scheduleRemark) {
        this.scheduleID = scheduleID;
        this.scheduleName = scheduleName;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleStopTime = scheduleStopTime;
        this.scheduleRemark = scheduleRemark;
    }

    public Schedule(int scheduleID,int position,String scheduleName, String scheduleStartTime, String scheduleStopTime, String scheduleRemark,int scheduleRemind) {
        this.scheduleID = scheduleID;
        this.position=position;
        this.scheduleName = scheduleName;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleStopTime = scheduleStopTime;
        this.scheduleRemark = scheduleRemark;
        this.scheduleRemind=scheduleRemind;
    }

    public int getscheduleID() {
        return scheduleID;
    }

    public String getscheduleName() {
        return scheduleName;
    }

    public void setscheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getscheduleStartTime() {
        return scheduleStartTime;
    }

    public void setscheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getscheduleStopTime() {
        return scheduleStopTime;
    }

    public void setscheduleStopTime(String scheduleStopTime) {
        this.scheduleStopTime = scheduleStopTime;
    }

    public int getscheduleRemind() {
        return scheduleRemind;
    }

    public String getscheduleRemark() {
        return scheduleRemark;
    }

    public int getPosition(){return position;}

    public void setPosition(int position){this.position=position;}

    public void setscheduleRemark(String scheduleRemark) {
        this.scheduleRemark = scheduleRemark;
    }

    /*获得进度*/
    public int getlen(){
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long nowTimeL= new Date().getTime();
        long startL= 0;
        try {
            startL = sd.parse(scheduleStartTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long stopL= 0;
        try {
            stopL = sd.parse(scheduleStopTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(nowTimeL<=startL){
            return 0;
        }
        else if(nowTimeL<=stopL){
            double gone=nowTimeL-startL;
            double all=stopL-startL;
            double result=gone/all*100;
            int realResult=(int)result;
            return realResult;
        }
        else{
            return 100;
        }
    }



    public int getlenAdd() {
        return 0;
    }



}
