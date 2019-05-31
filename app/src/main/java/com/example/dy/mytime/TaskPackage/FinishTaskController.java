package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class FinishTaskController extends TaskController implements IFinishTask {
    private MyDatabaseController controller;
    public FinishTaskController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //修改任务为已完成或未完成
    public void changeTaskFinish(int taskId,int finishNum){
        String cmd="update Task set IsComplete="+Integer.toString(finishNum)+" where Task_id="+Integer.toString(taskId);
        controller.modify(cmd);
    }
}
