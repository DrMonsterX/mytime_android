package com.example.dy.mytime.TaskPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarColorController extends TaskController implements IGetCalendarColor {
    private MyDatabaseController controller;
    public CalendarColorController(){
        super();

    }

    //获取对应月份颜色色块
    public ArrayList<TaskDraw> getColorByMonth(ArrayList<Task> monthTask, String month){
        ArrayList<TaskDraw> myColor=new ArrayList<>();
        for(Task task:monthTask){
            int startDay=getDay(month,task.gettaskStartTime());
            int stopDay=getDay(month,task.gettaskStopTime());
            for(int i=startDay;i<=stopDay;i++){
                boolean isFind=false;
                for(TaskDraw draw:myColor){
                    if(draw.day==i){
                        isFind=true;
                        if(draw.taskcolor1==0){
                            draw.taskcolor1=getColorByTag(task.gettaskTag());
                        }
                        else if(draw.taskcolor2==0){
                            draw.taskcolor2=getColorByTag(task.gettaskTag());
                        }
                        else if(draw.taskcolor3==0){
                            draw.taskcolor3=getColorByTag(task.gettaskTag());
                        }
                        break;
                    }
                }
                if(!isFind){
                    TaskDraw draw=new TaskDraw(i);
                    draw.taskcolor1=getColorByTag(task.gettaskTag());
                    myColor.add(draw);
                }
            }
        }
        return myColor;
    }

    //获得对应日期的日子
    public int getDay(String month,String date){
        long monthTime=0;
        long dateTime=0;
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
        try {
            monthTime=sd.parse(month).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateTime=sd.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(monthTime>dateTime){
            return 1;
        }
        else if(monthTime<dateTime){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(monthTime));
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date aimDate=c.getTime();
            SimpleDateFormat sd1=new SimpleDateFormat("dd");
            String day=sd1.format(aimDate);
            int result=Integer.parseInt(day);
            return result;
        }
        SimpleDateFormat sd1=new SimpleDateFormat("dd");
        SimpleDateFormat sd2=new SimpleDateFormat("yyyy-MM-dd");
        Date result = new Date();
        try {
            result=sd2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day=sd1.format(result);
        int resultDay=Integer.parseInt(day);
        return resultDay;
    }

    //获取任务对应的日历颜色
    public int getColorByTag(String tag){
        if(tag.equals("默认")){
            return 1;
        }
        else if(tag.equals("追星")){
            return 2;
        }
        else if(tag.equals("恋爱")){
            return 3;
        }
        else if(tag.equals("日常")){
            return 4;
        }
        return 5;
    }
}
