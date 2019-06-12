package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;

import java.util.ArrayList;

public class AddTaskController extends TaskController implements IAddTask {
    public AddTaskController(){
        super();

    }
    //添加任务
    public void addTask(String taskName, String taskStartTime, String taskStopTime,String tagNum, String taskRemark,int taskRemind){
        TaskController taskController=new TaskController();
        ArrayList<Task> tasks;
       tasks=taskController.getAllTask();
       int position=tasks.size();
        Thread thread = new AddTaskThread( UserId.getInstance().getUserId(),position,taskName,taskStartTime,taskStopTime,tagNum,taskRemark,taskRemind);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //通过分享码添加任务
    public void addTaskByString(String str){
        int index = str.indexOf("￥");
        if(index==-1)
            return;
        String name=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("￥");
        if(index==-1)
            return;
        String startTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("￥");
        if(index==-1)
            return;
        String stopTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("￥");
        if(index==-1)
            return;
        String tag=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("￥");
        if(index==-1)
            return;
        String remark=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("￥");
        if(index==-1)
            return;
        int remindNum=Integer.parseInt(str.substring(0,index));
        addTask(name,startTime,stopTime,tag,remark,remindNum);
    }
}
