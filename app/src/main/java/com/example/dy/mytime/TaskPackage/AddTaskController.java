package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;

public class AddTaskController extends TaskController implements IAddTask {
    private MyDatabaseController controller;
    public AddTaskController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //添加任务
    public void addTask(String taskName, String taskStartTime, String taskStopTime,String tagNum, String taskRemark,int taskRemind){
        int task_id=controller.getMax("Task","Task")+1;
        int position=controller.getCount("Task","User_id",Integer.toString(UserId.getInstance().getUserId()));
        String cmd="insert into Task values("+Integer.toString(task_id)+","+Integer.toString(UserId.getInstance().getUserId())+",'"+taskName+"','"+
                taskStartTime+"','"+taskStopTime+"',0,'"+taskRemark+"',"+Integer.toString(taskRemind)+",'"+tagNum+"',"+Integer.toString(position)+")";
        controller.modify(cmd);
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
