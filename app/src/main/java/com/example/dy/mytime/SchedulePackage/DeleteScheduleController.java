package com.example.dy.mytime.SchedulePackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class DeleteScheduleController extends ScheduleController implements IDeleteSchedule {
    private MyDatabaseController controller;
    public DeleteScheduleController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //删除日程
    public void deleteSchedule(int scheduleId){
        controller.deleteById("Schedule","Schedule_id",scheduleId);
    }
}
