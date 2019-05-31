package com.example.dy.mytime.SchedulePackage;

import java.util.ArrayList;

public interface IGetSchedule {
    //获取目标天所有日程
    public ArrayList<Schedule> getScheduleByDay(String date);
    //按照ScheduleId获取日程
    public Schedule getScheduleById(int scheduleId);
    //获取目标日schedule个数
    public int getDBPosition(String startTime);
}
