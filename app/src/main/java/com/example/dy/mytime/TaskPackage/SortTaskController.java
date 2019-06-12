package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class SortTaskController extends TaskController implements ISortTask {
    public SortTaskController(){
        super();

    }

    //对日程重新排序
    public void resortTask(int taskId,int position){
        Thread thread = new resortTaskThread( taskId,position);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
