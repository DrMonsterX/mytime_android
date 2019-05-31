package com.example.dy.mytime.TaskPackage;

public interface INode {
    //删除所有相关节点
    public void deleteAllNode(int taskId);
    //添加节点
    public void addNode(int taskId,String nodeName,String nodeTime,int finishNum);
    //修改节点为已完成或未完成
    public void changeNodeFinish(int nodeId,int finishNum);
}
