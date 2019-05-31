package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.DeleteTaskController;
import com.example.dy.mytime.TaskPackage.SortTaskController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;
import com.example.dy.mytime.Adapter.TaskAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskTagActivity extends AppCompatActivity {

    private ImageButton goUserButton;
    private ImageButton addTaskButton;
    private TextView goCalendar;
    private TextView goSchedule;
    private TextView gostatistic;
    private TextView tagAll;
    private TextView tagStar;
    private TextView tagLove;
    private TextView tagLife;
    private TextView tagWork;
    private TextView tagMo;
    private RecyclerView rv;
    private TaskAdapter adapter;
    private List<Task> tasklist= new ArrayList<>();
    private TextView mEmptyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        tagAll=(TextView) findViewById(R.id.tag1);
        tagAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskTagActivity.this, TaskListActivity.class);
                startActivity(intent);

            }
        });

        tagStar=(TextView) findViewById(R.id.tag2);
        tagStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);

                /*搜索追星标签的任务*/
                tasklist.clear();
                ArrayList<Task> allTask=myTC.getTaskByTag("追星");
                tasklist.addAll(allTask);

                adapter.notifyDataSetChanged();
                updateUI();
            }
        });

        tagLove=(TextView) findViewById(R.id.tag3);
        tagLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);

                /*搜索恋爱标签的任务*/
                tasklist.clear();
                ArrayList<Task> allTask=myTC.getTaskByTag("恋爱");
                tasklist.addAll(allTask);


                adapter.notifyDataSetChanged();
                updateUI();
            }
        });

        tagLife=(TextView) findViewById(R.id.tag4);
        tagLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);

                /*搜索生活标签的任务*/
                tasklist.clear();
                ArrayList<Task> allTask=myTC.getTaskByTag("日常");
                tasklist.addAll(allTask);

                adapter.notifyDataSetChanged();
                updateUI();
            }
        });

        tagWork=(TextView) findViewById(R.id.tag5);
        tagWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);

                /*搜索工作标签的任务*/
                tasklist.clear();
                ArrayList<Task> allTask=myTC.getTaskByTag("工作");
                tasklist.addAll(allTask);

                adapter.notifyDataSetChanged();
                updateUI();
            }
        });

        tagMo=(TextView) findViewById(R.id.tag6);
        tagMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                TaskController myTC=new TaskController(dbCon);

                /*搜索默认标签的任务*/
                tasklist.clear();
                ArrayList<Task> allTask=myTC.getTaskByTag("默认");
                tasklist.addAll(allTask);

                adapter.notifyDataSetChanged();
                updateUI();
            }
        });

        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskTagActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        addTaskButton=(ImageButton)findViewById(R.id.addTaskBtn);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskTagActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        goCalendar=(TextView)findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskTagActivity.this, CalendarActivity.class);
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
                intent.setClass(TaskTagActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

        gostatistic=(TextView)findViewById(R.id.goStatistic);
        gostatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskTagActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String tag = bundle.getString("tag");

        initData(tag);

        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator)rv.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new TaskAdapter(tasklist,getContext());
        rv.setAdapter(adapter);

        updateUI();

        adapter.setOntaskItemClickListener(new TaskAdapter.taskItemClickListener() {
            @Override
            public void ontaskItemClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("taskID",tasklist.get(position).gettaskID() );
                intent.putExtras(bundle);
                intent.setClass(TaskTagActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData(String tag) {
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        TaskController myTC=new TaskController(dbCon);

        /*搜索恋爱标签的任务*/
        tasklist.clear();
        ArrayList<Task> allTask=myTC.getTaskByTag(tag);
        tasklist.addAll(allTask);

    }

    private void updateUI() {

        if(tasklist.size()>0) {
            mEmptyTextView.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);

        }
        else{
            rv.setVisibility(View.INVISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
        }

    }

    private Context getContext(){return this;}
}
