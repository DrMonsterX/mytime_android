package com.example.dy.mytime.UserPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class PasswordController extends UserController implements IChangePassword {
    private MyDatabaseController controller;
    public PasswordController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //修改密码
    public void changePassword(String newPassword){
        String cmd="update User set Password="+newPassword+" where User_id="+Integer.toString(UserId.getInstance().getUserId());
        controller.modify(cmd);
    }
}
