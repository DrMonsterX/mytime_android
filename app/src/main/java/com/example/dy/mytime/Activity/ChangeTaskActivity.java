package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.Adapter.ChangeNodeAdapter;
import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.ModifyTaskController;
import com.example.dy.mytime.TaskPackage.Node;
import com.example.dy.mytime.TaskPackage.NodeController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChangeTaskActivity extends AppCompatActivity {

    private ImageButton returnButton;
    private ImageButton finishButton;
    private ImageButton tagButton;
    private ImageButton remindButton;
    private ImageButton addNodeButton;//增加节点按钮
    private TextView remind;
    private TextView tag;
    private int remindNum;
    private String tagNum;
    private int taskID;
    private TextView getStartTime;
    private TextView getStopTime;
    private String startTime;
    private String stopTime;
    private EditText taskName;
    private EditText taskRemark;
    private Calendar calendar;// 用来装日期
    private DatePickerDialog dialog;
    private Task task;
    private RecyclerView rv;
    private ChangeNodeAdapter adapter;
    private List<Node> nodelist= new ArrayList<>();

    //隐藏键盘取消焦点
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
        setContentView(R.layout.activity_change_task);


        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTaskActivity.this.finish();
            }
        });

        finishButton = (ImageButton) findViewById(R.id.changeBtn);
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                startTime=getStartTime.getText().toString();
                stopTime=getStopTime.getText().toString();
                boolean isTimeRight=false;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date star = formatter.parse(getStartTime.getText().toString());
                    Date stop = formatter.parse(getStopTime.getText().toString());
                    if(star.getTime()<=stop.getTime())//比较时间大小
                    {
                        isTimeRight=true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(taskName.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入名称！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(getStartTime.getText().toString().isEmpty()||getStartTime.getText().toString().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(!isTimeRight){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入正确的起止时间！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else {
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n   确认本次修改吗？");//提示内容

                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO Auto-generated method stub
                            ModifyTaskController myTC=new ModifyTaskController();
                            NodeController myNC=new NodeController();
                            /*数据库修改任务,且修改该任务的节点*/
                            if(remind.getText().toString().trim().equals("不提醒"))
                                remindNum=0;
                            else
                                remindNum=1;
                            String tagx=tag.getText().toString().trim();
                            if(tagx.equals("日常"))
                                tagNum="日常";
                            else if(tagx.equals("默认"))
                                tagNum="默认";
                            else if(tagx.equals("追星"))
                                tagNum="追星";
                            else if(tagx.equals("恋爱"))
                                tagNum="恋爱";
                            else tagNum="工作";
                            myTC.changeTask(taskID,taskName.getText().toString(),getStartTime.getText().toString(),getStopTime.getText().toString(),
                                    remindNum,tagNum,taskRemark.getText().toString());
                            //每一个节点的名字和时间
                            //删除所有节点
                            myNC.deleteAllNode(taskID);
                            //添加节点

                            HashMap<Integer, String> name = adapter.name;
                            HashMap<Integer, String> time = adapter.time;

                            for(int i=0;i<nodelist.size();i++)
                            {
                                int finishNum=0;
                                if(nodelist.get(i).getnodeFinish()){
                                    finishNum=1;
                                }
                                nodelist.get(i).setnodeName(adapter.name.get(i));
                                nodelist.get(i).setnodeTime(adapter.time.get(i));
                                myNC.addNode(taskID,nodelist.get(i).getnodeName(),nodelist.get(i).getnodeTime(),finishNum);
                            }
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt("taskID",task.gettaskID() );
                            intent.putExtras(bundle);
                            intent.setClass(ChangeTaskActivity.this, TaskActivity.class);
                            startActivity(intent);
                            ChangeTaskActivity.this.finish();
                        }
                    });//确定按钮
                    builder.setNegativeButton("取消", null);//继续按钮

                    builder.create().show();
                }


            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int taskID = bundle.getInt("taskID");

        initData(taskID);

        getStartTime = (TextView) findViewById(R.id.taskStartTime);
        getStartTime.setText(task.gettaskStartTime());
        getStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(ChangeTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + (monthOfYear+1) + "日-->" + dayOfMonth);
                                getStartTime.setText(year + "-" + (monthOfYear+1) + "-"
                                        + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        getStopTime= (TextView) findViewById(R.id.taskStopTime);
        getStopTime.setText(task.gettaskStopTime());
        getStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                dialog = new DatePickerDialog(ChangeTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + (monthOfYear+1) + "日-->" + dayOfMonth);
                                getStopTime.setText(year + "-" + (monthOfYear+1) + "-"
                                        + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tag= (TextView) findViewById(R.id.taskTag);
        tag.setText(task.gettaskTag());
        tagButton = (ImageButton) findViewById(R.id.taskTagBtn);
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);
                builder.setTitle("请选择任务标签");//提示框标题
                final String[] cities = {"        默认", "        追星", "        恋爱", "        日常", "        工作"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        tag.setText(cities[which]);
                    }
                });
                builder.setNegativeButton("取消", null);//继续按钮

                builder.create().show();
            }
        });

        remind= (TextView) findViewById(R.id.taskRemind);
        if(task.gettaskRemind()==0)
            remind.setText("不提醒");
        else remind.setText("每天提醒");
        remindButton = (ImageButton) findViewById(R.id.taskRemindBtn);
        remindButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChangeTaskActivity.this,R.style.dialog_style);
                builder.setTitle("请选择提醒方式");//提示框标题
                final String[] cities = {"        每天提醒", "        不提醒"};
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


        taskName= (EditText) findViewById(R.id.taskName);
        taskName.setText(task.gettaskName());


        taskRemark= (EditText) findViewById(R.id.taskRemark);
        taskRemark.setText(task.getTaskRemark());

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChangeNodeAdapter(nodelist,ChangeTaskActivity.this);
        rv.setAdapter(adapter);

        addNodeButton=(ImageButton)findViewById(R.id.addNodeBtn);
        addNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodelist.add(new Node("","请选择节点时间"));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData(int taskID) {
        this.taskID=taskID;

        TaskController myTC=new TaskController();
        /*数据库得到ID为taskID的任务*/
        task=myTC.getTaskById(taskID);


        /*数据库得到taskID任务的节点*/
        nodelist.clear();
        ArrayList<Node> allNode=myTC.getNodeByTaskId(taskID);
        nodelist.addAll(allNode);
    }
    private Context getContext(){return this;}
}


