package com.example.dy.mytime.SchedulePackage;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;


import com.example.dy.mytime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * 定义闹钟广播
 * Created by 邹峰立 on 2017/10/19.
 */
public class AlarmBroadcast extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    public void onReceive(final Context context, Intent intent) {
        if ("startAlarm".equals(intent.getAction())) {
//            Toast.makeText(context, "闹钟提醒", Toast.LENGTH_LONG).show();
            // 处理闹钟事件
            // 振动、响铃、或者跳转页面等

            ScheduleController mySC=new ScheduleController();

            /*数据库得到nowDay日期的日程列表*/
            SimpleDateFormat mydf = new SimpleDateFormat("yyyy-MM-dd");
            String nowDay = mydf.format(new Date());
            ArrayList<Schedule> todaySchedule=mySC.getScheduleByDay(nowDay);
            for(int i=0;i<todaySchedule.size();i++){
                String time = todaySchedule.get(i).getscheduleStartTime();
                String[] start_parts = time.split(" |:");
                // 设置闹钟触发动作
                Intent new_intent = new Intent(context, AlarmBroadcast.class);
                new_intent.setAction("Alarm");
                pendingIntent = PendingIntent.getBroadcast(context, i, new_intent, PendingIntent.FLAG_CANCEL_CURRENT);
                long systemTime = System.currentTimeMillis();
                // 设置闹钟时间
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_parts[1])-1);
                calendar.set(Calendar.MINUTE, Integer.parseInt(start_parts[2]));

                long selectTime = calendar.getTimeInMillis();

                // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
                if(systemTime > selectTime) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }


        }
        else if("Alarm".equals((intent.getAction()))){
            //闹钟提示音
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();

            //日程提醒框
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_style);
            builder.setTitle("日程提醒");//提示框标题
            builder.setMessage("您有待完成的日程，还有一个小时就要开始了，请前往MEMO查看");//提示内容
            /*返回id信息*/

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                   // alarmManager.cancel(pendingIntent);
                }
            });//确定按钮

            builder.create().show();
        }
    }
}
