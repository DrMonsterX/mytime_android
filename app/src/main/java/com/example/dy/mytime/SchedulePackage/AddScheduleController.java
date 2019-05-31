package com.example.dy.mytime.SchedulePackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;

public class AddScheduleController extends ScheduleController implements IAddSchedule {
    private MyDatabaseController controller;
    public AddScheduleController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }
    //添加日程
    public void addSchedule(String scheduleName, String scheduleStartTime, String scheduleStopTime, String scheduleRemark,int scheduleRemind){
        int schedule_id=controller.getMax("Schedule","Schedule")+1;
        int position=getDBPosition(scheduleStartTime);
        String cmd="insert into Schedule values("+Integer.toString(schedule_id)+","+Integer.toString(UserId.getInstance().getUserId())+","
                +Integer.toString(position)+",'"+scheduleName+"','"+scheduleStartTime+"','"+scheduleStopTime+"','"+scheduleRemark+"',"+Integer.toString(scheduleRemind)+")";
        controller.modify(cmd);
    }
    //通过分享码添加日程
    public void addScheduleByString(String str){
        int index = str.indexOf("$");
        if(index==-1)
            return;
        String name=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String startTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String stopTime=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        String remark=str.substring(0,index);
        str=str.substring(index+1);
        index=str.indexOf("$");
        if(index==-1)
            return;
        int remindNum=Integer.parseInt(str.substring(0,index));
        addSchedule(name,startTime,stopTime,remark,remindNum);
    }
}
