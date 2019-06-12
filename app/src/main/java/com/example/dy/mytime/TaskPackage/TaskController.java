package com.example.dy.mytime.TaskPackage;

import android.database.Cursor;
import android.util.Log;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskController implements IGetTask{
    private MyDatabaseController controller;
    public static ArrayList<Task> allTask=new ArrayList<>();
    public static ArrayList<Task> TaskByTag=new ArrayList<>();
    public static Task task;
    public static ArrayList<Node> allNode=new ArrayList<>();
    public TaskController(){

    }

    //获取所有task
    public ArrayList<Task> getAllTask(){

        Thread thread = new getTaskThread(UserId.getInstance().getUserId());
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTask;
    }

    //获取对应标签的task
    public ArrayList<Task> getTaskByTag(String tag){
        Thread thread = new getTaskByIdThread(UserId.getInstance().getUserId(),tag);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return TaskByTag;
    }

    //获取task总数
    public int getAllCount(){
        int count=0;
        getAllTask();
        count=allTask.size();
        return count;
    }

    //获取对应taskId的task
    public Task getTaskById(int task_id){
        Thread thread = new getOneTaskThread(task_id);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            return task;

    }

    //获取任务id对应的所有节点
    public ArrayList<Node> getNodeByTaskId(int task_id){
        Thread thread = new getNodeByIdThread(task_id);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allNode;
    }

    //获取对应月份的task
    public ArrayList<Task> getTaskByMonth(String date){
        ArrayList<Task> allTasks=getAllTask();
        ArrayList<Task> allTasksofmonth=new ArrayList<>();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
        long aimTime=0;
        try {
            aimTime=sd.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<allTasks.size();i++){
            String taskStartTime=allTasks.get(i).gettaskStartTime();
            String taskStopTime=allTasks.get(i).gettaskStopTime();
            long taskTimeS=0,taskTimeE=0;
            try {
                taskTimeS=sd.parse(taskStartTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                taskTimeE=sd.parse(taskStopTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(aimTime==taskTimeS||aimTime==taskTimeE){
                allTasksofmonth.add(allTasks.get(i));
            }
        }
        Log.e("month",Integer.toString(allTasksofmonth.size()));
        return allTasksofmonth;
    }
}
