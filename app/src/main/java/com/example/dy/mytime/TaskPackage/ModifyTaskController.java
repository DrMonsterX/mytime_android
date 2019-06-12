package com.example.dy.mytime.TaskPackage;



public class ModifyTaskController extends TaskController implements IModifyTask {
    public ModifyTaskController(){
        super();

    }

    //修改任务
    public void changeTask(int taskId,String taskName,String startTime,String stopTime,int remind,String tag,String remark){
        Thread thread=new ChangeTaskThread(taskId,taskName,startTime,stopTime,remind,remark,tag);
        thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
