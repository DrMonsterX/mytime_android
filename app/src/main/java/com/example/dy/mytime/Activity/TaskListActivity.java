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

public class TaskListActivity extends AppCompatActivity {

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
                Intent intent = new Intent(TaskListActivity.this, TaskListActivity.class);
                startActivity(intent);

            }
        });

        tagStar=(TextView) findViewById(R.id.tag2);
        tagStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag", "追星");
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskTagActivity.class);
                startActivity(intent);
            }
        });

        tagLove=(TextView) findViewById(R.id.tag3);
        tagLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag", "恋爱");
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskTagActivity.class);
                startActivity(intent);
            }
        });

        tagLife=(TextView) findViewById(R.id.tag4);
        tagLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag", "日常");
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskTagActivity.class);
                startActivity(intent);
            }
        });

        tagWork=(TextView) findViewById(R.id.tag5);
        tagWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag", "工作");
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskTagActivity.class);
                startActivity(intent);
            }
        });

        tagMo=(TextView) findViewById(R.id.tag6);
        tagMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag", "默认");
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskTagActivity.class);
                startActivity(intent);
            }
        });

        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        addTaskButton=(ImageButton)findViewById(R.id.addTaskBtn);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        goCalendar=(TextView)findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, CalendarActivity.class);
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
                intent.setClass(TaskListActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

        gostatistic=(TextView)findViewById(R.id.goStatistic);
        gostatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        initData();

        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator)rv.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new TaskAdapter(tasklist,getContext());
        rv.setAdapter(adapter);

        updateUI();

        helper.attachToRecyclerView(rv);

        adapter.setOntaskItemClickListener(new TaskAdapter.taskItemClickListener() {
            @Override
            public void ontaskItemClick(int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("taskID",tasklist.get(position).gettaskID() );
                intent.putExtras(bundle);
                intent.setClass(TaskListActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        tasklist.clear();
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        TaskController myTC=new TaskController(dbCon);
        /*数据库得到全部任务列*/
        ArrayList<Task> allTask=myTC.getAllTask();
        for(int i=0;i<allTask.size();i++){
            for(Task task:allTask){
                if(task.getPosition()==i){
                    tasklist.add(task);
                }
            }
        }
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
            Collections.swap(tasklist,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
            MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
            TaskController myTC=new TaskController(dbCon);
            SortTaskController mySTC=new SortTaskController(dbCon);
            /*数据库日程列表交换位置，现在tasklist的顺序即为新顺序*/
            if(tasklist.size()==myTC.getAllCount()){
                for(int i=0;i<tasklist.size();i++){
                    mySTC.resortTask(tasklist.get(i).gettaskID(),i);
                }
            }

            adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            adapter.notifyItemRangeChanged(0,tasklist.size());
            updateUI();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑事件
            deleteTask(viewHolder.getAdapterPosition());

        }

        @Override
        public boolean isLongPressDragEnabled() {
            //是否可拖拽
            return true;
        }


    });

    private void deleteTask(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(TaskListActivity.this,R.style.dialog_style);

        builder.setTitle("提示");//提示框标题
        builder.setMessage("\n  您确定要删除该任务吗？");//提示内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Auto-generated method stub
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                DeleteTaskController myTC=new DeleteTaskController(dbCon);
                SortTaskController mySTC=new SortTaskController(dbCon);
                /*数据库删除任务*/
                myTC.deleteTask(tasklist.get(position).gettaskID());
                tasklist.remove(position);//修改list内容
                for(int i=0;i<tasklist.size();i++){
                    mySTC.resortTask(tasklist.get(i).gettaskID(),i);
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
