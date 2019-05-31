package com.example.dy.mytime.TaskPackage;

import java.util.ArrayList;

public interface IGetTask {
    //获取所有task
    public ArrayList<Task> getAllTask();
    //获取对应标签的task
    public ArrayList<Task> getTaskByTag(String tag);
    //获取task总数
    public int getAllCount();
    //获取对应taskId的task
    public Task getTaskById(int task_id);
    //获取任务id对应的所有节点
    public ArrayList<Node> getNodeByTaskId(int task_id);
    //获取对应月份的task
    public ArrayList<Task> getTaskByMonth(String date);
}
