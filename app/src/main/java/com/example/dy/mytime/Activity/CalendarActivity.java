package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dy.mytime.Adapter.MonthTaskAdapter;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.AddScheduleController;
import com.example.dy.mytime.TaskPackage.AddTaskController;
import com.example.dy.mytime.TaskPackage.CalendarColorController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;
import com.example.dy.mytime.TaskPackage.TaskDraw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private ImageButton goUserButton;
    private TextView goSchedule;
    private TextView goTask;
    private TextView gostatistic;
    private CustomCalendar cal;
    private RecyclerView rv;
    private MonthTaskAdapter adapter;
    private List<Task> mtasklist= new ArrayList<>();
    private TextView mEmptyTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        goSchedule=(TextView)findViewById(R.id.goSchedule);
        goSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                SimpleDateFormat mydf = new SimpleDateFormat("yyyy-MM-dd");
                String nowDay = mydf.format(new Date());
                bundle.putString("dayStr", nowDay);
                intent.putExtras(bundle);
                intent.setClass(CalendarActivity.this, ScheduleListActivity.class);
                startActivity(intent);

            }
        });

        goTask=(TextView)findViewById(R.id.goTask);
        goTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        gostatistic=(TextView)findViewById(R.id.goStatistic);
        gostatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        cal = (CustomCalendar)findViewById(R.id.cal);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String nowMonth = df.format(new Date());//当前月

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        TaskController myTC=new TaskController(dbCon);
        CalendarColorController myCC=new CalendarColorController(dbCon);
        /*数据库得到本月nowMonth的任务列表*/
        ArrayList<Task> myMonthTask=myTC.getTaskByMonth(nowMonth);
        /*获得本月即nowMonth的任务色块（每天三种颜色）*/
        ArrayList<TaskDraw> monthDraw=new ArrayList<>();
        monthDraw=myCC.getColorByMonth(myMonthTask,nowMonth);
        final List<TaskDraw> taskdrawlist = new ArrayList<>();
        taskdrawlist.addAll(monthDraw);

        cal.setRenwu(nowMonth,taskdrawlist);

        cal.setOnClickListener(new CustomCalendar.onClickListener() {
            @Override
            public void onLeftRowClick() {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);
                CalendarColorController mmyCC=new CalendarColorController(dbCon);
                String month=cal.monthChange(-1);

                //month月的任务列表
                ArrayList<Task> myMonthTask=myTC.getTaskByMonth(month);

                /*数据库得到month月的任务色块*/
                ArrayList<TaskDraw> monthDraw=mmyCC.getColorByMonth(myMonthTask,month);
                List<TaskDraw> taskdrawlist = new ArrayList<>();
                taskdrawlist.addAll(monthDraw);

                cal.setRenwu(month,taskdrawlist);

                /*数据库得到month月的任务列表*/
                mtasklist.clear();
                mtasklist.addAll(myMonthTask);

                adapter.notifyDataSetChanged();
                updateUI();

            }

            @Override
            public void onRightRowClick() {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);
                CalendarColorController myCC=new CalendarColorController(dbCon);
                String month=cal.monthChange(1);

                //month月的任务列表
                ArrayList<Task> myMonthTask=myTC.getTaskByMonth(month);

                /*数据库得到month月的任务色块*/
                ArrayList<TaskDraw> monthDraw=myCC.getColorByMonth(myMonthTask,month);
                List<TaskDraw> taskdrawlist = new ArrayList<>();
                taskdrawlist.addAll(monthDraw);

                cal.setRenwu(month,taskdrawlist);

                /*数据库得到month的任务列表*/
                mtasklist.clear();
                mtasklist.addAll(myMonthTask);

                adapter.notifyDataSetChanged();
                updateUI();

            }

            @Override
            public void onTitleClick() {
                //时间选择弹窗
            }

            @Override
            public void onDayClick(int nowDay, String dayStr) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("dayStr", dayStr);

                intent.putExtras(bundle);
                intent.setClass(CalendarActivity.this, ScheduleListActivity.class);
                startActivity(intent);

            }
        });



        /*数据库得到本月nowMonth的任务列表*/
        mtasklist.clear();
        mtasklist.addAll(myMonthTask);

        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MonthTaskAdapter(mtasklist);
        rv.setAdapter(adapter);

        updateUI();

        adapter.setOnmtItemClickListener(new MonthTaskAdapter.mtItemClickListener() {
            @Override
            public void onmtItemClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("taskID",mtasklist.get(position).gettaskID() );
                intent.putExtras(bundle);
                intent.setClass(CalendarActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateUI() {
        if(mtasklist.size()>0) {
            mEmptyTextView.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);
        }
        else{
            rv.setVisibility(View.INVISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
        }
    }
    private Context getContext(){return this;}

    public void onResume(){
        super.onResume();
        GangUpInvite(this);
    }

    public void GangUpInvite(final Context context) {
        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        //无数据时直接返回
        if (!clipboard.hasPrimaryClip()) {
            return;
        }
        //如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            //此处是TEXT文本信息
            if (item.getText() != null) {
                String str = item.getText().toString();
                String key = "￥";
                final int first = str.indexOf(key);
                if (first >= 0) {
                    final String new1 = str.substring(first + 1);
                    int tow = new1.indexOf(key);
                    if (tow >= 0) {

                        AlertDialog.Builder builder= new AlertDialog.Builder(CalendarActivity.this,R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  检测到新任务，确认添加吗？");//提示内容

                        builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generated method stub

                                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                                AddTaskController myTC=new AddTaskController(dbCon);
                                myTC.addTaskByString(new1);
                                /*清空剪切板，防止重复添加**/
                                clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));

                                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                                startActivity(intent);
                            }
                        });//确定按钮
                        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generated method stub

                                /*清空剪切板，防止重复添加**/
                                clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));

                            }
                        });
                        builder.create().show();
                    }

                }
            }
            if (item.getText() != null) {
                String str = item.getText().toString();
                String key = "$";
                final int first = str.indexOf(key);
                if (first >= 0) {
                    final String new1 = str.substring(first + 1);
                    int tow = new1.indexOf(key);
                    if (tow >= 0) {

                        AlertDialog.Builder builder= new AlertDialog.Builder(CalendarActivity.this,R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  检测到新日程，确认添加吗？");//提示内容

                        builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generated method stub

//                                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
//                                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                                AddScheduleController mySC=new AddScheduleController();
                                mySC.addScheduleByString(new1);
                                /*清空剪切板，防止重复添加**/
                                clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));

                                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                                startActivity(intent);
                            }
                        });//确定按钮
                        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generated method stub

                                /*清空剪切板，防止重复添加**/
                                clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));

                            }
                        });
                        builder.create().show();
                    }

                }
            }
        }
    }
}
