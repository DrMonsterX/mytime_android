package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class DeleteTaskController extends TaskController implements IDeleteTask {
    private MyDatabaseController controller;
    public DeleteTaskController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //删除task
    public void deleteTask(int taskId){
        controller.deleteById("Task","Task_id",taskId);
        controller.deleteById("Node","Task_id",taskId);
    }
}
