package com.example.dy.mytime.UserPackage;

public class UserId {
    private int userId;
    private  static UserId instance=new UserId();
    private UserId(){}
    public static UserId getInstance(){
        return instance;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }

    public int getUserId(){
        return userId;
    }
}
