package com.example.dy.mytime.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android .widget.CompoundButton;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.RegistController;
import com.example.dy.mytime.UserPackage.UserController;


public class RegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageButton returnButton;
    private RadioButton box1, box2, box3, box4;
    private RadioGroup group1, group2;
    private EditText name,newPassword,ensurePassword;
    private int num=0;
    private Button registerButton;



    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
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
        setContentView(R.layout.activity_sign);

        box1 = (RadioButton) findViewById(R.id.box1);
        box2 = (RadioButton) findViewById(R.id.box2);
        box3 = (RadioButton) findViewById(R.id.box3);
        box4 = (RadioButton) findViewById(R.id.box4);
        group1 = (RadioGroup) findViewById(R.id.group1);
        group2 = (RadioGroup) findViewById(R.id.group2);

        box1.setOnCheckedChangeListener(this);
        box2.setOnCheckedChangeListener(this);
        box3.setOnCheckedChangeListener(this);
        box4.setOnCheckedChangeListener(this);

        name = (EditText) findViewById(R.id.newIdText);
        newPassword = (EditText) findViewById(R.id.newPasswordText);
        ensurePassword = (EditText) findViewById(R.id.repeatPasswordText);

        returnButton = (ImageButton) findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerButton = (Button) findViewById(R.id.signUpButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if(name.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入用户名！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(newPassword.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入密码！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }

                else if(newPassword.getText().toString().equals(ensurePassword.getText().toString()))//两次密码输入一致时
                {

                    /*数据库添加用户*/
                    MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                    MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                    RegistController myUC=new RegistController(dbCon);
                    int myId=0;
                    myId=myUC.register(name.getText().toString(),num,newPassword.getText().toString());



                    //注册成功提示框
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.dialog_style);
                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("     您已注册成功！快快登陆吧！   您的id为 "+myId);//提示内容
                                                                 /*返回id信息*/

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //TODO Auto-generated method stub
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });//确定按钮

                    builder.create().show();
                }

                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  两次密码输入不一致！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();

                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.box1:
                group2.clearCheck();
                num=0;
                break;
            case R.id.box2:
                group2.clearCheck();
                num=1;
                break;
            case R.id.box3:
                group1.clearCheck();
                num=2;
                break;
            case R.id.box4:
                group1.clearCheck();
                num=3;
                break;
            default:
                break;
        }
    }

    private Context getContext(){return this;}
}

