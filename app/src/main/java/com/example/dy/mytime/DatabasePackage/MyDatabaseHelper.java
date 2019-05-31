package com.example.dy.mytime.DatabasePackage;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_User = "create table User(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "User_id integer primary key," +
            "UserName text," +
            "Icon_id interger," +
            "Password text,"+
            "Completeness_id integer)";

    public static final String CREATE_Follow = "create table Follow(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "Follow_id integer primary key,"+
            "Following_id integer," +
            "Followed_id integer)";

    public static final String CREATE_Task = "create table Task("+
            "Task_id integer primary key," +
            "User_id integer," +
            "TaskName text,"+
            "StartTime text,"+
            "FinishTime text,"+
            "IsComplete integer,"+
            "Remark text,"+
            "IsRemind integer,"+
            "Tag text,"+
            "Position integer)";

    public static final String CREATE_Node = "create table Node("+
            "Node_id integer primary key autoincrement,"+
            "Task_id integer,"+
            "NodeName text,"+
            "Time text,"+
            "IsComplete integer)";

    public static final String CREATE_Schedule = "create table Schedule("+
            "Schedule_id integer primary key autoincrement,"+
            "User_id integer,"+
            "Position integer,"+
            "ScheduleName text,"+
            "StartTime text,"+
            "FinishTime text,"+
            "Remark text,"+
            "IsRemind integer)";

    public static final String CREATE_Completeness = "create table Completeness("+
            "Completeness_id integer primary key autoincrement,"+
            "WeekCompleteness integer,"+
            "History_one integer,"+
            "History_two integer,"+
            "History_three integer,"+
            "History_four integer,"+
            "History_five integer)";

    private Context mContext;

    //构造方法：第一个参数Context，第二个参数数据库名，第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，第四个参数表示目前库的版本号（用于对库进行升级）
    public  MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory , int version){
        super(context,name ,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_User);
        db.execSQL(CREATE_Follow);
        db.execSQL(CREATE_Task);
        db.execSQL(CREATE_Node);
        db.execSQL(CREATE_Schedule);
        db.execSQL(CREATE_Completeness);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

