package com.example.dy.mytime.TaskPackage;


public class FinishTaskController extends TaskController implements IFinishTask {

    public FinishTaskController(){
        super();

    }
    //修改任务为已完成或未完成
    public void changeTaskFinish(int taskId,int finishNum){
        Thread thread = new changeTaskFinishThread( taskId,finishNum);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
