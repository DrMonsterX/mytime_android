package com.example.dy.mytime.TaskPackage;

public interface IAddTask {
    //添加任务
    public void addTask(String taskName, String taskStartTime, String taskStopTime,String tagNum, String taskRemark,int taskRemind);
    //通过搜索码添加任务
    public void addTaskByString(String str);
}
