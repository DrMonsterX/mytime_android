package com.example.dy.mytime.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.Adapter.RankAdapter;
import com.example.dy.mytime.UserPackage.FollowController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;
import com.example.dy.mytime.UserPackage.UserId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private ImageButton goUserButton;
    private ImageButton graghButton;
    private TextView goCalendar;
    private TextView goSchedule;
    private TextView goTask;
    private RecyclerView rv;
    private RankAdapter adapter;
    private List<User> ranklist= new ArrayList<>();
    private TextView number;
    private ImageView userHead;
    private TextView userName;
    private TextView percent;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        graghButton=(ImageButton)findViewById(R.id.graghBtn);
        graghButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        goCalendar=(TextView)findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        goTask=(TextView)findViewById(R.id.goTask);
        goTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, TaskListActivity.class);
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
                intent.setClass(RankActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        FollowController myUC=new FollowController(dbCon);
        /*数据库得到排名列表*/
        ArrayList<User> myRank=myUC.getRank();
        ranklist.clear();
        ranklist.addAll(myRank);



        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RankAdapter(ranklist,this);
        rv.setAdapter(adapter);

        number=(TextView) findViewById(R.id.number);
        for(int i = 0 ; i < ranklist.size() ; i++) {
            if(ranklist.get(i).getUserID()==UserId.getInstance().getUserId()){
                user=ranklist.get(i);
                number.setText(i+1+"");
                break;
            }
        }

        userHead=(ImageView) findViewById(R.id.userHead);
        if (user.getIconID()==0)userHead .setBackgroundResource(R.drawable.headshot1);
        else if (user.getIconID()==1) userHead.setBackgroundResource(R.drawable.headshot2);
        else if (user.getIconID()==2) userHead.setBackgroundResource(R.drawable.headshot3);
        else if (user.getIconID()==3) userHead.setBackgroundResource(R.drawable.headshot4);
        userName=(TextView) findViewById(R.id.userName);
        userName.setText(user.getUserName());

        UserController myUC2=new UserController();
        percent=(TextView) findViewById(R.id.percent);
        percent.setText((myUC2.getWeekCompleteness(user.getUserID())+"%"));




    }

    private Context getContext(){return this;}
}
