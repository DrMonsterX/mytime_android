package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.AddScheduleController;
import com.example.dy.mytime.SchedulePackage.ScheduleController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddScheduleActivity extends AppCompatActivity {

    private ImageButton returnButton;
    private ImageButton finishButton;
    private ImageButton repeatButton;
    private ImageButton remindButton;

    private EditText scheduleName;
    private TextView getDate;
    private TextView getStartTime;
    private TextView getStopTime;
    private TextView remind;
    private TextView repeat;
    private EditText remark;
    private String startTime;
    private String stopTime;
    private int remindNum;
    private int repeatNum;
    private Calendar calendar;// 用来装日期
    private DatePickerDialog dialog;
    private TimePickerDialog timePickerDialog;


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
        if (v != null && (v instanceof EditText)) {//OIOJOIJOIJOJOI
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
        setContentView(R.layout.activity_add_schedule);


        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddScheduleActivity.this.finish();
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
                    AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入名称！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(getDate.getText().toString().isEmpty()||getStartTime.getText().toString().isEmpty()||getStartTime.getText().toString().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(!isTimeRight){
                    AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入正确的起止时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }

                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n   确认完成吗？");//提示内容

                    builder.setPositiveButton("完成",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO Auto-generated method stub


                            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                            MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                            AddScheduleController mySC=new AddScheduleController(dbCon);


                            if(remind.getText().toString().trim().equals("不提醒"))
                                remindNum=0;
                            else
                                remindNum=1;

                            if(repeat.getText().toString().trim().equals("不重复"))
                                repeatNum=0;
                            else if(repeat.getText().toString().trim().equals("一周内每天重复"))
                                repeatNum=1;
                            else repeatNum=2;


                            mySC.addSchedule(scheduleName.getText().toString(),startTime,stopTime,remark.getText().toString(),remindNum);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            SimpleDateFormat mydf = new SimpleDateFormat("yyyy-MM-dd");
                            String nowDay = mydf.format(new Date());
                            bundle.putString("dayStr", nowDay);
                            intent.putExtras(bundle);
                            intent.setClass(AddScheduleActivity.this, ScheduleListActivity.class);
                            startActivity(intent);
                        }
                    });//确定按钮
                    builder.setNegativeButton("取消", null);//继续按钮

                    builder.create().show();
                }


            }
        });

        getDate = (TextView) findViewById(R.id.scheduleDate);
        getDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(AddScheduleActivity.this,
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
        getStartTime= (TextView) findViewById(R.id.scheduleStartTime);
        getStartTime.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              calendar=Calendar.getInstance();
              timePickerDialog=new TimePickerDialog(AddScheduleActivity.this,
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
        getStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                timePickerDialog=new TimePickerDialog(AddScheduleActivity.this,
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

        remind= (TextView) findViewById(R.id.scheduleRemind);
        remindButton = (ImageButton) findViewById(R.id.scheduleRemindBtn);
        remindButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);
                builder.setTitle("请选择提醒方式");//提示框标题
                final String[] remindways = {"        提前一小时", "        不提醒"};
                //    设置一个下拉的列表选择项
                builder.setItems(remindways, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        remind.setText(remindways[which]);
                    }
                });
                builder.setNegativeButton("取消", null);//继续按钮

                builder.create().show();
            }
        });

        repeat= (TextView) findViewById(R.id.scheduleRepeat);
        repeatButton = (ImageButton) findViewById(R.id.scheduleRepeatBtn);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AddScheduleActivity.this,R.style.dialog_style);
                builder.setTitle("请选择重复方式");//提示框标题
                final String[] repeatways = {"        一周内每天重复", "        不重复","        一个月内每天重复"};
                //    设置一个下拉的列表选择项
                builder.setItems(repeatways, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        repeat.setText(repeatways[which]);
                    }
                });
                builder.setNegativeButton("取消", null);//继续按钮

                builder.create().show();
            }
        });

        remark= (EditText) findViewById(R.id.scheduleRemark);
        scheduleName = (EditText) findViewById(R.id.scheduleName);

    }

    private Context getContext(){return this;}
}
