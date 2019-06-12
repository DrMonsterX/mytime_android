package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class ShareTaskController extends TaskController implements IShareTask {
    private MyDatabaseController controller;
    public ShareTaskController(){
        super();

    }

    public String getShareCode(int taskId){
        Task task=getTaskById(taskId);
        String result="￥"+task.gettaskName()+"￥"+task.gettaskStartTime()+"￥"+task.gettaskStopTime()+"￥"+task.gettaskTag()
                +"￥"+task.getTaskRemark()+"￥"+Integer.toString(task.gettaskRemind())+"￥";
        return result;
    }
}
