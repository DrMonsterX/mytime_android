package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dy.mytime.Adapter.AttentionAdapter;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.FollowController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;
import com.example.dy.mytime.UserPackage.UserId;

import java.util.ArrayList;
import java.util.List;

public class UserHomepageActivity extends AppCompatActivity {

    private ImageButton retrunButton;
    private Button addAttentionButton;
    private TextView changeButton;
    private TextView backToMainButton;
    private TextView name;
    private TextView id;
    private ImageView userImage;
    private User user;

    private RecyclerView rv;
    private AttentionAdapter adapter;
    private List<User> attentionlist= new ArrayList<>();
    private TextView mEmptyTextView;


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
        setContentView(R.layout.activity_user_homepage);


        UserController myUC=new UserController();
        /*数据库找到id为userID的user*/
        user=myUC.getUser(UserId.getInstance().getUserId());
        retrunButton=(ImageButton)findViewById(R.id.returnBtn);
        addAttentionButton=(Button) findViewById(R.id.addAttBtn);
        changeButton = (TextView) findViewById(R.id.changePasswordView);
        backToMainButton= (TextView) findViewById(R.id.backToMain);
        userImage=(ImageView) findViewById(R.id.user_image);
        if (user.getIconID()==0)userImage .setBackgroundResource(R.drawable.headshot1);
        else if (user.getIconID()==1) userImage.setBackgroundResource(R.drawable.headshot2);
        else if (user.getIconID()==2) userImage.setBackgroundResource(R.drawable.headshot3);
        else if (user.getIconID()==3) userImage.setBackgroundResource(R.drawable.headshot4);
        name=(TextView) findViewById(R.id.name);
        name.setText(user.getUserName());
        id=(TextView) findViewById(R.id.id);
        id.setText(user.getUserID()+"");

        retrunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        addAttentionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, AttentionActivity.class);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepageActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UserHomepageActivity.this,R.style.dialog_style);
                builder.setTitle("提示");//提示框标题
                builder.setMessage("\n  确认退出当前账号？");//提示内容

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                        dbCon.deleteId();

                        //TODO Auto-generated method stub
                        Intent intent = new Intent(UserHomepageActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });//确定按钮

                builder.setNegativeButton("取消",null);//取消按钮
                builder.create().show();


            }
        });

        /*数据库得到关注列表*/
        attentionlist.clear();
        FollowController myFC=new FollowController();
        ArrayList<User> myFollow=myFC.getFollow(UserId.getInstance().getUserId());
        attentionlist.addAll(myFollow);

        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttentionAdapter(UserHomepageActivity.this,attentionlist);
        rv.setAdapter(adapter);

        updateUI();

    }

    private void updateUI() {
        if(attentionlist.size()>0) {
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
