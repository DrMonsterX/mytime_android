package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class SortTaskController extends TaskController implements ISortTask {
    private MyDatabaseController controller;
    public SortTaskController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //对日程重新排序
    public void resortTask(int taskId,int position){
        String cmd="update Task set Position="+position+" where Task_id="+Integer.toString(taskId);
        controller.modify(cmd);
    }
}
