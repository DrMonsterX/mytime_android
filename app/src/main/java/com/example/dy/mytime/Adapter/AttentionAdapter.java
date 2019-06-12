package com.example.dy.mytime.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.FollowController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;

import java.util.List;

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.AttentionViewHolder> {

    private List<User> list;
    private Context context;
    public AttentionAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    class AttentionViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userID,attention;
        private ImageView user_image;

        public AttentionViewHolder(View attention_item) {
            super(attention_item);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userID = (TextView) itemView.findViewById(R.id.userID);
            user_image = (ImageView) itemView.findViewById(R.id.user_image);
            attention =(TextView) itemView.findViewById(R.id.attention);
        }
    }

    @Override
    public AttentionAdapter.AttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attention_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new  AttentionAdapter.AttentionViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AttentionAdapter.AttentionViewHolder holder, final int position) {
        User user = list.get(position);
        holder.userName.setText(user.getUserName());
        holder.userID.setText(user.getUserID()+"");
        if (user.getIconID()==0) holder.user_image.setBackgroundResource(R.drawable.headshot1);
        else if (user.getIconID()==1) holder.user_image.setBackgroundResource(R.drawable.headshot2);
        else if (user.getIconID()==2) holder.user_image.setBackgroundResource(R.drawable.headshot3);
        else if (user.getIconID()==3) holder.user_image.setBackgroundResource(R.drawable.headshot4);

        holder.attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context,R.style.dialog_style);

                builder.setTitle("提示");//提示框标题
                builder.setMessage("\n  您确定要取消关注吗？");//提示内容

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Auto-generated method stub

                        FollowController myUC=new FollowController();
                        /*数据库取消关注*/
                        myUC.deleteFollow(list.get(position).getUserID());
                        switch(FollowController.DFollow_code)
                        {
                            case -1:
                                AlertDialog.Builder builder= new AlertDialog.Builder(context,R.style.dialog_style);
                                builder.setTitle("提示");//提示框标题
                                builder.setMessage("\n  调用失败");//提示内容确定按钮
                                builder.create().show();
                                break;
                            case -2:
                                AlertDialog.Builder builder2= new AlertDialog.Builder(context,R.style.dialog_style);
                                builder2.setTitle("提示");//提示框标题
                                builder2.setMessage("\n  不可取消关注自己");//提示内容确定按钮
                                builder2.create().show();
                                break;
                            case -3:
                                AlertDialog.Builder builder3= new AlertDialog.Builder(context,R.style.dialog_style);
                                builder3.setTitle("提示");//提示框标题
                                builder3.setMessage("\n  用户id无效");//提示内容确定按钮
                                builder3.create().show();
                                break;
                            case 0:
                                AlertDialog.Builder builder4= new AlertDialog.Builder(context,R.style.dialog_style);
                                builder4.setTitle("提示");//提示框标题
                                builder4.setMessage("\n  未知错误");//提示内容确定按钮
                                builder4.create().show();
                                break;
                            case 1:
                                list.remove(position);//修改list内容
                                notifyDataSetChanged();
                                break;
                            default:

                        }

                    }
                });//确定按钮
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
