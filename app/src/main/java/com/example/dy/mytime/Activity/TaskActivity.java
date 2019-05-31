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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.Adapter.NodeAdapter;
import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.Node;
import com.example.dy.mytime.TaskPackage.ShareTaskController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;

import java.util.ArrayList;
import java.util.List;


public class TaskActivity extends AppCompatActivity {

    private  ImageButton returnButton;
    private  ImageButton changeButton;
    private ImageButton shareButton;
    private TextView taskTime,taskName,taskRemark,taskTag,taskLastTime,taskRemind;
    private SeekBar seekBar;
    private int len ;
    private int lenadd;
    private int taskId;
    private Task task;
    private RecyclerView rv;
    private NodeAdapter adapter;
    private List<Node> nodelist= new ArrayList<>();


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
        setContentView(R.layout.activity_task);



        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("taskID",task.gettaskID() );
                intent.putExtras(bundle);
                intent.setClass(TaskActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });


        changeButton = (ImageButton) findViewById(R.id.changeBtn);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("taskID",task.gettaskID() );
                intent.putExtras(bundle);
                intent.setClass(TaskActivity.this, ChangeTaskActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int taskID = bundle.getInt("taskID");

        initData(taskID);

        taskTime= (TextView) findViewById(R.id.taskTime);
        taskName= (TextView) findViewById(R.id.taskName);
        taskRemark= (TextView) findViewById(R.id.taskRemark);
        taskTag=(TextView) findViewById(R.id.taskTag);
        taskLastTime=(TextView) findViewById(R.id.taskLastTime);
        taskRemind= (TextView) findViewById(R.id.taskRemind);

        if(task.gettaskRemind()==0)
            taskRemind.setText("不提醒");
        else taskRemind.setText("提前一小时");


        taskTime.setText(task.gettaskStartTime()+"--"+task.gettaskStopTime());
        taskName.setText(task.gettaskName());
        taskTag.setText(task.gettaskTag());
        if(task.gettaskLastTime()>=0){
            taskLastTime.setText("距结束还剩"+task.gettaskLastTime()+"天");
        }
        else taskLastTime.setText("距结束已经过去"+(task.gettaskLastTime()*(-1)+1)+"天");

        taskRemark.setText(task.getTaskRemark());

        seekBar = (SeekBar)findViewById(R.id.taskProgressBar);
        seekBar.setMax(100);

        len=task.getlen();
        seekBar.setProgress(len);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NodeAdapter(nodelist,getContext());
        rv.setAdapter(adapter);

        shareButton = (ImageButton) findViewById(R.id.shareBtn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                ShareTaskController mySTC=new ShareTaskController(dbCon);
                String myShareCode=mySTC.getShareCode(taskID);
                AlertDialog.Builder builder= new AlertDialog.Builder(TaskActivity.this,R.style.dialog_style);

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


    private void initData(int taskID) {
        this.taskId=taskID;
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        TaskController myTC=new TaskController(dbCon);

        /*数据库得到ID为taskID的任务*/
        task=myTC.getTaskById(taskID);



        /*数据库得到taskID任务的节点*/
        nodelist.clear();
        ArrayList<Node> allNode=myTC.getNodeByTaskId(taskID);
        nodelist.addAll(allNode);
    }


    private Context getContext(){return this;}
}
