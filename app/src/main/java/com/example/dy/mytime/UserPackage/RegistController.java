package com.example.dy.mytime.UserPackage;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

public class RegistController extends UserController implements IRegist {
    private MyDatabaseController controller;
    public RegistController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //用户注册
    public int register(String name,int iconid,String code) {
        int num;
        num=controller.getCount("User")+1+1652770;
        String cmd1="insert into User values("+Integer.toString(num)+",'"
                +name+"',"+Integer.toString(iconid)+",'"+code+"',"+Integer.toString(num)+")";
        String cmd2="insert into Completeness values("+Integer.toString(num)+",0,0,0,0,0,0)";
        controller.modify(cmd1);
        controller.modify(cmd2);
        return num;
    }
}
