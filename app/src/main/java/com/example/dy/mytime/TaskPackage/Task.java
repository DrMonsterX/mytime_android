package com.example.dy.mytime.TaskPackage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private int taskID;
    private String taskName;
    private String taskStartTime;
    private String taskStopTime;
    private String taskRemark;
    private int taskRemind;
    private int isfinished;
    private String taskTag;
    private int position;


    public Task(int taskID,String taskName, String taskStartTime, String taskStopTime, String taskTag) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskStartTime = taskStartTime;
        this.taskStopTime = taskStopTime;
        this.taskTag = taskTag;
    }

    public Task(int taskID,String taskName, String taskStartTime, String taskStopTime,String taskRemark,int taskRemind,int isfinished, String taskTag,int position) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskStartTime = taskStartTime;
        this.taskStopTime = taskStopTime;
        this.taskRemark=taskRemark;
        this.taskRemind=taskRemind;
        this.isfinished=isfinished;
        this.taskTag = taskTag;
        this.position=position;
    }

    public int gettaskID() {
        return taskID;
    }

    public String gettaskName() {
        return taskName;
    }

    public void settaskName(String taskName) {
        this.taskName = taskName;
    }

    public String gettaskStartTime() {
        return taskStartTime;
    }

    public void settaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String gettaskStopTime() {
        return taskStopTime;
    }

    public void settaskStopTime(String taskStopTime) {
        this.taskStopTime = taskStopTime;
    }

    public int gettaskRemind() {
        return taskRemind;
    }

    public String getTaskRemark(){return taskRemark;}

    public String gettaskTag() {
        return taskTag;
    }

    public boolean gettaskFinish() {
        if(isfinished==1)
            return true;
        return false;
    }

    public void settaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    /*获得进度*/
    public int getlen() {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        long nowTimeL= new Date().getTime();
        long startL= 0;
        try {
            startL = sd.parse(taskStartTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long stopL= 0;
        try {
            stopL = sd.parse(taskStopTime).getTime();
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
            return (int)result;
        }
        else{
            return 100;
        }
    }

    public int getPosition(){
        return position;
    }

    public int gettaskLastTime(){
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        long nowTimeL= new Date().getTime();
        long stopL= 0;
        try {
            stopL = sd.parse(taskStopTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double last=stopL-nowTimeL;
        double result=last/(24*3600*1000);
        return (int)result;
    }

}
