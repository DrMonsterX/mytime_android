package com.example.dy.mytime.TaskPackage;

public class Node {
    private int nodeId;
    private String nodeName;
    private String nodeTime;
    private int isFinished;


    public Node(String nodeName,String nodeTime) {
        this.nodeName = nodeName;
        this.nodeTime = nodeTime;
    }

    public Node(int nodeId,String nodeName,String nodeTime,int isFinished) {
        this.nodeId=nodeId;
        this.nodeName = nodeName;
        this.nodeTime = nodeTime;
        this.isFinished=isFinished;
    }


    public boolean getnodeFinish() {
        if(isFinished==1)
            return true;
        return false;
    }

    public String getnodeName() {
        return nodeName;
    }

    public int getnodeId() {
        return nodeId;
    }

    public void setnodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getnodeTime() {
        return nodeTime;
    }

    public void setnodeTime(String nodeTime) {
        this.nodeTime = nodeTime;
    }



}