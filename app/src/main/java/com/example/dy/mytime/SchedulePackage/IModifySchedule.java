package com.example.dy.mytime.SchedulePackage;

public interface IModifySchedule {
    //修改日程
    public void changeSchedule(int scheduleId,String schduleName,String startTime,String stopTime,String remark,int remind);
}
