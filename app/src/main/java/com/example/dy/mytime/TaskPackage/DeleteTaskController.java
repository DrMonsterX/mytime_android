package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class DeleteTaskController extends TaskController implements IDeleteTask {
    private MyDatabaseController controller;
    public DeleteTaskController(){
        super();

    }
    //删除task
    public void deleteTask(int taskId){
        Thread thread = new deleteTaskThread( taskId);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
