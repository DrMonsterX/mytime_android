package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class NodeController extends TaskController implements INode {
    private MyDatabaseController controller;
    public NodeController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //删除所有相关节点
    public void deleteAllNode(int taskId){
        controller.deleteById("Node","Task_id",taskId);
    }

    //添加节点
    public void addNode(int taskId,String nodeName,String nodeTime,int finishNum){
        int nodeId=controller.getMax("Node","Node")+1;
        String cmd="insert into Node values("+Integer.toString(nodeId)+","+Integer.toString(taskId)+",'"+nodeName+"','"+nodeTime+"',"+Integer.toString(finishNum)+")";
        controller.modify(cmd);
    }
    //修改节点为已完成或未完成
    public void changeNodeFinish(int nodeId,int finishNum){
        String cmd="update Node set IsComplete="+Integer.toString(finishNum)+" where Node_id="+Integer.toString(nodeId);
        controller.modify(cmd);
    }
}
