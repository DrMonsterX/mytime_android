package com.example.dy.mytime.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.Schedule;
import com.example.dy.mytime.SchedulePackage.ScheduleController;
import com.example.dy.mytime.TaskPackage.ShareTaskController;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    private  ImageButton returnButton;
    private  ImageButton changeButton;
    private ImageButton shareButton;
    private TextView scheduleTime,scheduleName,scheduleRemark;
    private SeekBar seekBar;
    private int len ;
    private int lenadd=0;
    private Schedule schedule;
    private int num;


    private TextView remind;
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
        setContentView(R.layout.activity_schedule);

        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                SimpleDateFormat mydf = new SimpleDateFormat("yyyy-MM-dd");
                String nowDay = mydf.format(new Date());
                bundle.putString("dayStr", nowDay);
                intent.putExtras(bundle);
                intent.setClass(ScheduleActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

        changeButton = (ImageButton) findViewById(R.id.changeBtn);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("scheduleID",schedule.getscheduleID() );
                intent.putExtras(bundle);
                intent.setClass(ScheduleActivity.this, ChangeScheduleActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int scheduleID = bundle.getInt("scheduleID");

        initData(scheduleID);

        scheduleTime= (TextView) findViewById(R.id.scheduleTime);
        scheduleName= (TextView) findViewById(R.id.scheduleName);
        scheduleRemark= (TextView) findViewById(R.id.scheduleRemark);

        scheduleTime.setText(schedule.getscheduleStartTime()+"--"+schedule.getscheduleStopTime());
        scheduleName.setText(schedule.getscheduleName());
        scheduleRemark.setText(schedule.getscheduleRemark());

        seekBar = (SeekBar)findViewById(R.id.scheduleProgressBar);
        seekBar.setMax(100);
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        });
        len=schedule.getlen();
        lenadd=schedule.getlenAdd();

        remind=(TextView)findViewById(R.id.scheduleRemind);

        if(schedule.getscheduleRemind()==0)
            remind.setText("不提醒");
        else remind.setText("提前一小时");

        shareButton = (ImageButton) findViewById(R.id.shareBtn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                ScheduleController mySTC=new ScheduleController(dbCon);
                String myShareCode=mySTC.getShareCode(schedule.getscheduleID());
                AlertDialog.Builder builder= new AlertDialog.Builder(ScheduleActivity.this,R.style.dialog_style);

                builder.setTitle("分享码");//提示框标题
                builder.setMessage("您的分享码已复制到剪贴板，快去分享吧！");
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData m=ClipData.newPlainText("Label",myShareCode);
                cm.setPrimaryClip(m);
                builder.setPositiveButton("确定",null);//确定按钮

                builder.create().show();
            }
        });
    }

    /*数据库得到ID为scheduleID的日程*/
    private void initData(int scheduleID) {
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        ScheduleController mySC=new ScheduleController(dbCon);
        schedule=mySC.getScheduleById(scheduleID);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            len += lenadd;
            handler.sendEmptyMessageDelayed(1,1000);
            seekBar.setProgress(len);
        }
    };

    private Context getContext(){return  this;}

}
