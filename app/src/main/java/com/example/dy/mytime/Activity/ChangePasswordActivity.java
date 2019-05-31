package com.example.dy.mytime.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.PasswordController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;
import com.example.dy.mytime.UserPackage.UserId;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageButton returnButton;
    private Button ensureButton;
    private TextView userName;
    private TextView id;
    private EditText newPass;
    private EditText ensurePass;
    private User user;

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
        setContentView(R.layout.activity_change_password);

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        UserController myUC=new UserController();
        /*数据库找到id为userID的user*/
        user=myUC.getUser(UserId.getInstance().getUserId());

        newPass=(EditText) findViewById(R.id.newPassword);
        ensurePass=(EditText) findViewById(R.id.ensurePassword);
        userName=(TextView) findViewById(R.id.userName);
        userName.setText(user.getUserName());
        id=(TextView) findViewById(R.id.id);
        id.setText(user.getUserID()+"");

        returnButton=(ImageButton)findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.this.finish();
            }
        });

        ensureButton = (Button) findViewById(R.id.ensureButton);
        ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newPass.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangePasswordActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  请输入密码！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();
                }
                else if(newPass.getText().toString().equals(ensurePass.getText().toString()))//两次密码输入一致时
                {
                    MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                    MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                    PasswordController myUC=new PasswordController(dbCon);
                    /*数据库修改密码*/
                    myUC.changePassword(newPass.getText().toString());
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangePasswordActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  您已成功修改密码！");//提示内容

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO Auto-generated method stub
                            ChangePasswordActivity.this.finish();
                        }
                    });//确定按钮

                    builder.create().show();
                }

                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(ChangePasswordActivity.this,R.style.dialog_style);

                    builder.setTitle("提示");//提示框标题
                    builder.setMessage("\n  两次密码输入不一致！");//提示内容

                    builder.setPositiveButton("确定",null);//确定按钮

                    builder.create().show();

                }

            }
        });

    }
    private Context getContext(){return this;}
}
