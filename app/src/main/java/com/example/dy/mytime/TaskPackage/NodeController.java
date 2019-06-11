package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class NodeController extends TaskController implements INode {
    private MyDatabaseController controller;
    public NodeController(){
        super();

    }

    //删除所有相关节点
    public void deleteAllNode(int taskId){
        Thread thread = new deleteAllNodeThread( taskId);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //添加节点
    public void addNode(int taskId,String nodeName,String nodeTime,int finishNum){
        Thread thread = new AddNodeThread( taskId,nodeName,nodeTime,finishNum);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //修改节点为已完成或未完成
    public void changeNodeFinish(int nodeId,int finishNum){
        Thread thread = new changeNodeThread( nodeId,finishNum);
        thread.start();  //执行线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
