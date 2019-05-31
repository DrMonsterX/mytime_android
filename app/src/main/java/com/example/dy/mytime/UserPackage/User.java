package com.example.dy.mytime.UserPackage;

public class User {
    private int userID;
    private String userName;
    private int iconID;
    private String password;
    private int completenessID;


    public User(int userID,String userName,int iconID) {
        this.userID = userID;
        this.userName = userName;
        this.iconID = iconID;
    }

    public User(int userID, String userName, int iconID,String password,int completenessID){
        this.userID=userID;
        this.userName=userName;
        this.iconID=iconID;
        this.password=password;
        this.completenessID=completenessID;
    }

    public int getUserID() {return userID;}

    public String getUserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getPassword(){return password;}

}