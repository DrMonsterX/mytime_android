package com.example.dy.mytime.SchedulePackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class ModifyScheduleController extends ScheduleController implements IModifySchedule {
    private MyDatabaseController controller;
    public ModifyScheduleController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //修改日程
    public void changeSchedule(int scheduleId,String schduleName,String startTime,String stopTime,String remark,int remind){
        String cmd="update Schedule set ScheduleName='"+schduleName+"',StartTime='"+startTime+"',FinishTime='"+stopTime
                +"',Remark='"+remark+"',IsRemind="+Integer.toString(remind)+" where Schedule_id="+Integer.toString(scheduleId);
        controller.modify(cmd);
    }
}
