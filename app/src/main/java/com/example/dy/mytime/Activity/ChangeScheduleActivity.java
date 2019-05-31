package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.*;
import android.app.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.ModifyScheduleController;
import com.example.dy.mytime.SchedulePackage.Schedule;
import com.example.dy.mytime.SchedulePackage.ScheduleController;


public class ChangeScheduleActivity extends AppCompatActivity {


    private ImageButton returnButton;
    private ImageButton finishButton;
    private ImageButton remindButton;

    private TextView remind;
    private EditText remark;
    private EditText scheduleName;
    private int remindNum;
    private int scheduleId;
    private String startTime;
    private String stopTime;
    private TextView getDate;
    private TextView getStartTime;
    private TextView getStopTime;
    private Calendar calendar;// 用来装日期
    private DatePickerDialog dialog;
    private TimePickerDialog timePickerDialog;
    private Schedule schedule;


    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }


    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_schedule);


        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeScheduleActivity.this.finish();
            }
        });

        finishButton = (ImageButton) findViewById(R.id.changeBtn);
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                startTime=getDate.getText().toString()+" "+getStartTime.getText().toString();
                stopTime=getDate.getText().toString()+" "+getStopTime.getText().toString();
                boolean isTimeRight=false;
                SimpleDateFormat formatter = new SimpleDateFormat("H:m");
                try {
                    Date star = formatter.parse(getStartTime.getText().toString());
                    Date stop = formatter.parse(getStopTime.getText().toString());
                    if(star.getTime()<stop.getTime())//比较时间大小
                    {
                        isTimeRight=true;
                    }
//                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//                    getDate.setText(f.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(scheduleName.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入名称！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(getDate.getText().toString().isEmpty()||getStartTime.getText().toString().isEmpty()||getStartTime.getText().toString().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(!isTimeRight){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入正确的起止时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else {
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n   确认本次修改吗？");//提示内容

                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO Auto-generated method stub
                            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                            MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                            ModifyScheduleController mySC=new ModifyScheduleController(dbCon);
                            /*数据库修改日程*/
                            startTime=getDate.getText()+" "+getStartTime.getText();
                            stopTime=getDate.getText()+" "+getStopTime.getText();


                            if(remind.getText().toString().trim().equals("不提醒"))
                                remindNum=0;
                            else
                                remindNum=1;
                            mySC.changeSchedule(scheduleId,scheduleName.getText().toString(),startTime,stopTime,remark.getText().toString(),remindNum);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt("scheduleID",schedule.getscheduleID() );
                            intent.putExtras(bundle);
                            intent.setClass(ChangeScheduleActivity.this, ScheduleActivity.class);
                            startActivity(intent);
                            ChangeScheduleActivity.this.finish();
                        }
                    });//确定按钮
                    builder.setNegativeButton("返回", null);//继续按钮

                    builder.create().show();
                }
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int scheduleID = bundle.getInt("scheduleID");

        initData(scheduleID);
        SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sd3=new SimpleDateFormat("yyyy-MM-dd");
        String startTime=schedule.getscheduleStartTime();
        long time= 0;
        try {
            time=sd1.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date myDate=new Date(time);
        String myDateS=sd3.format(myDate);
        getDate = (TextView) findViewById(R.id.scheduleDate);
        getDate.setText(myDateS);
        getDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(ChangeScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + (monthOfYear+1) + "日-->" + dayOfMonth);
                                getDate.setText(year + "-" + (monthOfYear+1) + "-"
                                        + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));


                dialog.show();
            }
        });
        SimpleDateFormat sd2=new SimpleDateFormat("HH:mm");
        getStartTime= (TextView) findViewById(R.id.scheduleStartTime);


        try {
            time = sd1.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myDate=new Date(time);
        startTime=sd2.format(myDate);
        getStartTime.setText(startTime);
        getStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                timePickerDialog=new TimePickerDialog(ChangeScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                getStartTime.setText(hourOfDay+":"+minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),false);
                timePickerDialog.show();
            }
        });

        getStopTime= (TextView) findViewById(R.id.scheduleStopTime);
        String stopTime=schedule.getscheduleStopTime();
        try {
            time = sd1.parse(stopTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myDate=new Date(time);
        stopTime=sd2.format(myDate);
        getStopTime.setText(stopTime);
        getStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                timePickerDialog=new TimePickerDialog(ChangeScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                getStopTime.setText(hourOfDay+":"+minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),false);
                timePickerDialog.show();
            }
        });

        scheduleName=(EditText) findViewById(R.id.scheduleName);
        scheduleName.setText(schedule.getscheduleName());

        remark=(EditText)findViewById(R.id.scheduleRemark);
        remark.setText(schedule.getscheduleRemark());

        remind= (TextView) findViewById(R.id.scheduleRemind);
        if(schedule.getscheduleRemind()==0)
            remind.setText("不提醒");
        else remind.setText("提前一小时");
        remindButton = (ImageButton) findViewById(R.id.scheduleRemindBtn);
        remindButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChangeScheduleActivity.this,R.style.dialog_style);
                builder.setTitle("请选择提醒方式");//提示框标题
                final String[] cities = {"        提前一小时", "        不提醒"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        remind.setText(cities[which]);
                    }
                });
                builder.setNegativeButton("取消", null);//继续按钮

                builder.create().show();
            }
        });


    }
    private void initData(int scheduleID) {
        /*数据库得到ID为scheduleID的日程*/
        this.scheduleId=scheduleID;
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        ScheduleController mySC=new ScheduleController(dbCon);
        schedule=mySC.getScheduleById(scheduleID);
    }
    private Context getContext(){return  this;}
}