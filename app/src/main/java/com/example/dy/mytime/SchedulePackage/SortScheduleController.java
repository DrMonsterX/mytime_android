package com.example.dy.mytime.SchedulePackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class SortScheduleController extends ScheduleController implements ISortSchedule {

    public SortScheduleController(){
        super();

    }

    //对日程重新排序
    public void resortSchedule(int scheduleId,int position){
        Thread thread = new resortScheduleThread(scheduleId,position);
        thread.start();  //执行sort线程
        try {
            thread.join();//等待线程执行结束后，主线程继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
