package com.example.dy.mytime.UserPackage;

import android.database.Cursor;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FollowController extends UserController implements IFollow {
    private MyDatabaseController controller;
    public FollowController(MyDatabaseController myDBC){
        super(myDBC);
        this.controller=myDBC;
    }

    //获取关注列表
    public ArrayList<User> getFollow(int userId){
        ArrayList<User> myFollow=new ArrayList<>();
        Cursor followCursor=controller.searchById(userId,"Follow","Following");
        while(followCursor.moveToNext()){
            int followId=followCursor.getInt(followCursor.getColumnIndex("Followed_id"));
            Cursor cursor=controller.searchById(followId,"User","User");
            cursor.moveToNext();
            User followed=new User(cursor.getInt(cursor.getColumnIndex("User_id")),cursor.getString(cursor.getColumnIndex("UserName")),
                    cursor.getInt(cursor.getColumnIndex("Icon_id")),cursor.getString(cursor.getColumnIndex("Password")),
                    cursor.getInt(cursor.getColumnIndex("Completeness_id")));
            myFollow.add(followed);
        }
        return myFollow;
    }

    //关注好友
    public void followUser(int userId){
        int num=controller.getMax("Follow","Follow")+1;
        String cmd="insert into Follow values("+Integer.toString(num)+","+Integer.toString(UserId.getInstance().getUserId())+","+Integer.toString(userId)+")";
        controller.modify(cmd);
    }

    //取消关注
    public void deleteFollow(int userId){
        String cmd="delete from Follow where Following_id="+Integer.toString(UserId.getInstance().getUserId())+" and Followed_id="+Integer.toString(userId);
        controller.modify(cmd);
    }

    //获取排名
    public ArrayList<User> getRank(){
        Comparator comparator=new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(getWeekCompleteness(o1.getUserID())>getWeekCompleteness(o2.getUserID())){
                    return -1;
                }
                else
                    return 1;
            }
        };
        ArrayList<User> myRank=new ArrayList<>();
        myRank.add(getUser(UserId.getInstance().getUserId()));
        myRank.addAll(getFollow(UserId.getInstance().getUserId()));
        Collections.sort(myRank,comparator);
        return myRank;
    }
}
