package com.example.dy.mytime.TaskPackage;

import java.util.ArrayList;

public interface IGetCalendarColor {
    //获取对应月份颜色色块
    public ArrayList<TaskDraw> getColorByMonth(ArrayList<Task> monthTask, String month);
    //获得对应日期的日子
    public int getDay(String month,String date);
    //获取任务对应的日历颜色
    public int getColorByTag(String tag);

}
