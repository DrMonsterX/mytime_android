package com.example.dy.mytime.TaskPackage;

public class TaskDraw{
    public int day;
    public int taskcolor1;
    public int taskcolor2;
    public int taskcolor3;
    public TaskDraw(int day){
        this.day=day;
        this.taskcolor1 = 0;
        this.taskcolor2 = 0;
        this.taskcolor3 = 0;
    }
    public TaskDraw(int day, int taskcolor1,int taskcolor2,int taskcolor3) {
        this.day = day;
        this.taskcolor1 = taskcolor1;
        this.taskcolor2 = taskcolor2;
        this.taskcolor3 = taskcolor3;
    }
}
