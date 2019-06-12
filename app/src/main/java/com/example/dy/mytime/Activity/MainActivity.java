package com.example.dy.mytime.Activity;


import android.app.AlertDialog;
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

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.LoginController;
import com.example.dy.mytime.UserPackage.UserId;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private EditText id;
    private EditText password;



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
        dbHelper = new MyDatabaseHelper(this, "OurAPP.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();

        id = (EditText) findViewById(R.id.newIdText);
        password = (EditText) findViewById(R.id.newPasswordText);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        //自动登录

        MyDatabaseController dbCon = new MyDatabaseController(dbHelper);
        Cursor cursor = dbCon.searchId();
        if (cursor.moveToNext()) {
            UserId userId = UserId.getInstance();
            int Id = cursor.getInt(cursor.getColumnIndex("User_id"));
            userId.setUserId(Id);
            Log.e("User_id: ", Integer.toString(Id));
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
                if (id.getText().toString().trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入账号！");//提示内容

                    builder.setPositiveButton("确定", null);//确定按钮

                    builder.create().show();
                } else if (password.getText().toString().trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入密码！");//提示内容

                    builder.setPositiveButton("确定", null);//确定按钮

                    builder.create().show();
                } else {

                    LoginController myUC = new LoginController();
                    int checkResult = myUC.checkLogin(Integer.parseInt(id.getText().toString()), password.getText().toString());
                    if (checkResult == 0) {
                        UserId userId = UserId.getInstance();
                        userId.setUserId(Integer.parseInt(id.getText().toString()));
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                        MyDatabaseController dbCon = new MyDatabaseController(dbHelper);
                        String cmd = "insert into User values(" + id.getText().toString() + ")";
                        dbCon.modify(cmd);
                        /* 跳转到日历界面 */
                        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent);
                    } else if (checkResult == 1) {
                        /*密码错误**/
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  密码错误！");//提示内容

                        builder.setPositiveButton("确定", null);//确定按钮

                        builder.create().show();
                    } else {
                        /*用户名不存在**/
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);

                        builder.setTitle("提示");//提示框标题
                        builder.setMessage("\n  账号不存在！");//提示内容

                        builder.setPositiveButton("确定", null);//确定按钮

                        builder.create().show();
                    }
                }

            }
        });
    }

    private Context getContext(){return this;}




}
