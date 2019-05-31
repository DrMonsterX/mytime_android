package com.example.dy.mytime.UserPackage;

import java.util.ArrayList;

public interface IFollow {
    //获取关注列表
    public ArrayList<User> getFollow(int userId);
    //关注好友
    public void followUser(int userId);
    //取消关注
    public void deleteFollow(int userId);
    //获取排名
    public ArrayList<User> getRank();
}
