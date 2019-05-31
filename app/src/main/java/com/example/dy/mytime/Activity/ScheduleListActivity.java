package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.DeleteScheduleController;
import com.example.dy.mytime.SchedulePackage.Schedule;
import com.example.dy.mytime.SchedulePackage.ScheduleController;
import com.example.dy.mytime.Adapter.ScheduleAdapter;
import com.example.dy.mytime.SchedulePackage.SortScheduleController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ScheduleListActivity extends AppCompatActivity {

    private ImageButton goUserButton;
    private ImageButton addScheduleButton;
    private TextView goCalendar;
    private TextView goTask;
    private TextView gostatistic;


    private RecyclerView rv;
    private ScheduleAdapter adapter;
    private List<Schedule> schlist= new ArrayList<>();
    private TextView mEmptyTextView;
    private TextView dayText;
    private Calendar calendar;
    private DatePickerDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);


        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleListActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        addScheduleButton=(ImageButton)findViewById(R.id.addScheduleBtn);
        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleListActivity.this, AddScheduleActivity.class);
                startActivity(intent);
            }
        });

        goCalendar=(TextView)findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleListActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        goTask=(TextView)findViewById(R.id.goTask);
        goTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleListActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        gostatistic=(TextView)findViewById(R.id.goStatistic);
        gostatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleListActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String dayStr = bundle.getString("dayStr");
        dayText= (TextView) findViewById(R.id.dayText);
        dayText.setText(dayStr);

        initData(dayStr);

        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator)rv.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new ScheduleAdapter(schlist);
        rv.setAdapter(adapter);

        updateUI();

        helper.attachToRecyclerView(rv);

        adapter.setOnItemClickListener(new ScheduleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("scheduleID",schlist.get(position).getscheduleID() );
                intent.putExtras(bundle);
                intent.setClass(ScheduleListActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

        dayText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(ScheduleListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + (monthOfYear+1) + "日-->" + dayOfMonth);
                                dayText.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("dayStr", dayText.getText().toString());
                                intent.putExtras(bundle);
                                intent.setClass(ScheduleListActivity.this, ScheduleListActivity.class);
                                startActivity(intent);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });

    }

    private void initData(String dayStr) {
        schlist.clear();
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        ScheduleController mySC=new ScheduleController(dbCon);

        /*数据库得到nowDay日期的日程列表*/
        ArrayList<Schedule> todaySchedule=mySC.getScheduleByDay(dayStr);
        for(int i=0;i<todaySchedule.size();i++){
            for(Schedule schedule:todaySchedule){
                if(schedule.getPosition()==i){
                    schlist.add(schedule);
                    break;
                }
            }
        }

        //需要处理
        //schlist.add(new Schedule(3,2,"事件一","7:00","8:00","",2));
    }

    private void updateUI() {

        if(schlist.size()>0) {
            mEmptyTextView.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);
        }
        else{
            rv.setVisibility(View.INVISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
        }

    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //首先回调的方法 返回int表示是否监听该方向
            int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
            int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //滑动事件

            Collections.swap(schlist,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
            MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
            SortScheduleController mySC=new SortScheduleController(dbCon);
            /*数据库日程列表交换位置*/
            for(int i=0;i<schlist.size();i++)
            {
                mySC.resortSchedule(schlist.get(i).getscheduleID(),i);
            }
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            adapter.notifyItemRangeChanged(0,schlist.size());
            updateUI();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑事件
            deleteSchedule(viewHolder.getAdapterPosition());

        }

        @Override
        public boolean isLongPressDragEnabled() {
            //是否可拖拽
            return true;
        }


    });

    private void deleteSchedule(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(ScheduleListActivity.this,R.style.dialog_style);

        builder.setTitle("提示");//提示框标题
        builder.setMessage("\n  您确定要删除该日程吗？");//提示内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Auto-generated method stub
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                SortScheduleController mySC=new SortScheduleController(dbCon);
                DeleteScheduleController myDSC=new DeleteScheduleController(dbCon);
                /*数据库删除日程*/
                myDSC.deleteSchedule(schlist.get(position).getscheduleID());
                schlist.remove(position);//修改list内容
                for(int i=0;i<schlist.size();i++)
                {
                    mySC.resortSchedule(schlist.get(i).getscheduleID(),i);
                }
                adapter.notifyDataSetChanged();
                updateUI();
            }
        });//确定按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
                updateUI();
            }
        });
        builder.create().show();
    }

    private Context getContext(){return this;}
}
