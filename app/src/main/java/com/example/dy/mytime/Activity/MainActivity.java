package com.example.dy.mytime.Activity;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.AlarmBroadcast;
import com.example.dy.mytime.UserPackage.LoginController;
import com.example.dy.mytime.UserPackage.UserId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private EditText id;
    private EditText password;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


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
        setContentView(R.layout.activity_main);
        MyDatabaseHelper dbHelper;
        dbHelper=new MyDatabaseHelper(this, "OurAPP.db", null, 1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.close();

        id = (EditText) findViewById(R.id.newIdText);
        password = (EditText) findViewById(R.id.newPasswordText);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        //自动登录

        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        Cursor cursor = dbCon.searchId();
        if(cursor.moveToNext()){
            UserId userId = UserId.getInstance();
            int Id=cursor.getInt(cursor.getColumnIndex("User_id"));
            userId.setUserId(Id);
            Log.e("User_id: ",Integer.toString(Id));
            /* 跳转到日历界面 */
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        }
        cursor.close();
        dbCon.closeDB();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入账号！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(password.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入密码！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else {
//                    MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
//                    MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                    LoginController myUC=new LoginController();
                    int checkResult=myUC.checkLogin(Integer.parseInt(id.getText().toString()),password.getText().toString());
                    if(checkResult==0) {
                        UserId userId=UserId.getInstance();
                        userId.setUserId(Integer.parseInt(id.getText().toString()));
                        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                        String cmd="insert into User values("+id.getText().toString()+")";
                        dbCon.modify(cmd);
                        /* 跳转到日历界面 */
                        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent);
                    }
                    else if(checkResult==1){
                        /*密码错误**/
                        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this,R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  密码错误！");//提示内容

                        builder.setPositiveButton("确定",null);//确定按钮

                        builder.create().show();
                    }
                    else{
                        /*用户名不存在**/
                        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this,R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  账号不存在！");//提示内容

                        builder.setPositiveButton("确定",null);//确定按钮

                        builder.create().show();
                    }
                }

            }
        });
        initAlarm();
        //每天0点获取当天日程，并设置定时提醒
        //获取当前毫秒值
        long systemTime = System.currentTimeMillis();
        // 设置闹钟时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);

        long selectTime = calendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }


        setAlarm(calendar);



    }

    private Context getContext(){return this;}



    // 初始化闹钟
    private void initAlarm() {
        // 实例化AlarmManager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 设置闹钟触发动作
        Intent intent = new Intent(this, AlarmBroadcast.class);
        intent.setAction("startAlarm");
        pendingIntent = PendingIntent.getBroadcast(this, 110, intent, PendingIntent.FLAG_CANCEL_CURRENT);



        // 设置闹钟
        /**
         * AndroidL开始，设置的alarm的触发时间必须大于当前时间 5秒
         *
         * 设置一次闹钟-(5s后启动闹钟)
         *
         * @AlarmType int type：闹钟类型
         * ELAPSED_REALTIME：在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
         * ELAPSED_REALTIME_WAKEUP：在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件）。延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的。
         * RTC：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
         * RTC_WAKEUP：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
         *
         * long triggerAtMillis：触发闹钟的时间。
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.set(@AlarmType int type, long triggerAtMillis, PendingIntent operation);

        /**
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置精准周期闹钟-该方法提供了设置周期闹钟的入口，闹钟执行时间严格按照startTime来处理，使用该方法需要的资源更多，不建议使用。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.setRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);

        /**
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置不精准周期闹钟- 该方法也用于设置重复闹钟，与第二个方法相似，不过其两个闹钟执行的间隔时间不是固定的而已。它相对而言更省电（power-efficient）一些，因为系统可能会将几个差不多的闹钟合并为一个来执行，减少设备的唤醒次数。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * 内置变量
         * INTERVAL_DAY： 设置闹钟，间隔一天
         * INTERVAL_HALF_DAY： 设置闹钟，间隔半天
         * INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟
         * INTERVAL_HALF_HOUR： 设置闹钟，间隔半个小时
         * INTERVAL_HOUR： 设置闹钟，间隔一个小时
         *
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.setInexactRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);


        // 设置时区（东八区）-需要加权限SET_TIME_ZONE
//        alarmManager.setTimeZone("GMT+08:00");

        // 取消闹钟
//        alarmManager.cancel(pendingIntent);
    }

    // 设置闹钟
    private void setAlarm(Calendar calendar) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();

    }
}
