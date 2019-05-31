package com.example.dy.mytime.SchedulePackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class SortScheduleController extends ScheduleController implements ISortSchedule {
    private MyDatabaseController controller;
    public SortScheduleController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //对日程重新排序
    public void resortSchedule(int scheduleId,int position){
        String cmd="update Schedule set Position="+position+" where Schedule_id="+Integer.toString(scheduleId);
        controller.modify(cmd);
    }
}
