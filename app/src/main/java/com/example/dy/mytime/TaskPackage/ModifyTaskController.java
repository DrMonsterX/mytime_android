package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class ModifyTaskController extends TaskController implements IModifyTask {
    private MyDatabaseController controller;
    public ModifyTaskController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //修改任务
    public void changeTask(int taskId,String taskName,String startTime,String stopTime,int remind,String tag,String remark){
        String cmd="update Task set TaskName='"+taskName+"',StartTime='"+startTime+"',FinishTime='"+stopTime
                +"',Remark='"+remark+"',Tag='"+tag+"',IsRemind="+Integer.toString(remind)+" where Task_id="+Integer.toString(taskId);
        controller.modify(cmd);
    }
}
