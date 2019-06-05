package com.example.dy.mytime.TaskPackage;

import android.database.Cursor;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.UserPackage.UserId;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskController implements IGetTask{
    private MyDatabaseController controller;
    public TaskController(MyDatabaseController myDBC){
        controller=myDBC;
    }

    //获取所有task
    public ArrayList<Task> getAllTask(){
        ArrayList<Task> allTask=new ArrayList<>();
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Task","User");
        while(cursor.moveToNext()){
            Task task=new Task(cursor.getInt(cursor.getColumnIndex("Task_id")),cursor.getString(cursor.getColumnIndex("TaskName")),
                    cursor.getString(cursor.getColumnIndex("StartTime")),cursor.getString(cursor.getColumnIndex("FinishTime")),
                    cursor.getString(cursor.getColumnIndex("Remark")),cursor.getInt(cursor.getColumnIndex("IsRemind")),
                    cursor.getInt(cursor.getColumnIndex("IsComplete")),cursor.getString(cursor.getColumnIndex("Tag")),
                    cursor.getInt(cursor.getColumnIndex("Position")));
            allTask.add(task);
        }
        cursor.close();
        controller.closeDB();
        return allTask;
    }

    //获取对应标签的task
    public ArrayList<Task> getTaskByTag(String tag){
        ArrayList<Task> allTask=new ArrayList<>();
        tag="'"+tag+"'";
        Cursor cursor=controller.searchByTarget("Task","User_id",Integer.toString(UserId.getInstance().getUserId()),"Tag",tag);
        while(cursor.moveToNext()){
            Task task=new Task(cursor.getInt(cursor.getColumnIndex("Task_id")),cursor.getString(cursor.getColumnIndex("TaskName")),
                    cursor.getString(cursor.getColumnIndex("StartTime")),cursor.getString(cursor.getColumnIndex("FinishTime")),
                    cursor.getString(cursor.getColumnIndex("Remark")),cursor.getInt(cursor.getColumnIndex("IsRemind")),
                    cursor.getInt(cursor.getColumnIndex("IsComplete")),cursor.getString(cursor.getColumnIndex("Tag")),
                    cursor.getInt(cursor.getColumnIndex("Position")));
            allTask.add(task);
        }
        cursor.close();
        controller.closeDB();
        return allTask;
    }

    //获取task总数
    public int getAllCount(){
        int count=0;
        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Task","User");
        while(cursor.moveToNext()){
            count++;
        }
        cursor.close();
        controller.closeDB();
        return count;
    }

    //获取对应taskId的task
    public Task getTaskById(int task_id){
        Cursor cursor=controller.searchById(task_id,"Task","Task");
        if(cursor.moveToNext()){
            Task task=new Task(cursor.getInt(cursor.getColumnIndex("Task_id")),cursor.getString(cursor.getColumnIndex("TaskName")),
                    cursor.getString(cursor.getColumnIndex("StartTime")),cursor.getString(cursor.getColumnIndex("FinishTime")),
                    cursor.getString(cursor.getColumnIndex("Remark")),cursor.getInt(cursor.getColumnIndex("IsRemind")),
                    cursor.getInt(cursor.getColumnIndex("IsComplete")),cursor.getString(cursor.getColumnIndex("Tag")),
                    cursor.getInt(cursor.getColumnIndex("Position")));
            cursor.close();
            controller.closeDB();
            return task;
        }
        cursor.close();
        controller.closeDB();
        return null;
    }

    //获取任务id对应的所有节点
    public ArrayList<Node> getNodeByTaskId(int task_id){
        ArrayList<Node> allNode=new ArrayList<>();
        Cursor cursor=controller.searchById(task_id,"Node","Task");
        while(cursor.moveToNext()){
            Node node=new Node(cursor.getInt(cursor.getColumnIndex("Node_id")),cursor.getString(cursor.getColumnIndex("NodeName")),
                    cursor.getString(cursor.getColumnIndex("Time")),cursor.getInt(cursor.getColumnIndex("IsComplete")));
            allNode.add(node);
        }
        cursor.close();
        controller.closeDB();
        return allNode;
    }

    //获取对应月份的task
    public ArrayList<Task> getTaskByMonth(String date){
        ArrayList<Task> allTask=new ArrayList<>();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
        long aimTime=0;
        try {
            aimTime=sd.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Cursor cursor=controller.searchById(UserId.getInstance().getUserId(),"Task","User");
//        while(cursor.moveToNext()){
//            long taskTimeS=0;
//            long taskTimeE=0;
//            String taskStartTime=cursor.getString(cursor.getColumnIndex("StartTime"));
//            String taskEndTime=cursor.getString(cursor.getColumnIndex("FinishTime"));
//            try {
//                taskTimeS=sd.parse(taskStartTime).getTime();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try {
//                taskTimeE=sd.parse(taskEndTime).getTime();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if(aimTime==taskTimeS||aimTime==taskTimeE) {
//                Task task=new Task(cursor.getInt(cursor.getColumnIndex("Task_id")),cursor.getString(cursor.getColumnIndex("TaskName")),
//                        cursor.getString(cursor.getColumnIndex("StartTime")),cursor.getString(cursor.getColumnIndex("FinishTime")),
//                        cursor.getString(cursor.getColumnIndex("Remark")),cursor.getInt(cursor.getColumnIndex("IsRemind")),
//                        cursor.getInt(cursor.getColumnIndex("IsComplete")),cursor.getString(cursor.getColumnIndex("Tag")),
//                        cursor.getInt(cursor.getColumnIndex("Position")));
//                allTask.add(task);
//            }
//        }
//        cursor.close();
//        controller.closeDB();
        return allTask;
    }
}
