package com.example.dy.mytime.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dy.mytime.Activity.UserHomepageActivity;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.FollowController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;
import com.example.dy.mytime.UserPackage.UserId;

import java.util.ArrayList;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchViewHolder> {

    private List<User> list;
    private Context context;
    private User me;

    public SearchUserAdapter(Context context,List<User> list) {
        this.context = context;
        this.list = list;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userID;
        private ImageView userImage;
        private Button attention_btn;

        public SearchViewHolder(View attention_item) {
            super(attention_item);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userID = (TextView) itemView.findViewById(R.id.userID);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            attention_btn = (Button) itemView.findViewById(R.id.attention_btn);
        }
    }

    @Override
    public SearchUserAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new  SearchUserAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SearchUserAdapter.SearchViewHolder holder, final int position) {
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context, "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        UserController myUC=new UserController(dbCon);
        FollowController myFC=new FollowController(dbCon);
        /*数据库找到id为userID的user*/
        me=myUC.getUser(UserId.getInstance().getUserId());

        User user = list.get(position);
        holder.userName.setText(user.getUserName());
        holder.userID.setText(user.getUserID()+"");
        if(user.getUserID()==me.getUserID()){
            holder.attention_btn.setVisibility(View.INVISIBLE);
        }
        ArrayList<User> myFollow=myFC.getFollow(UserId.getInstance().getUserId());
        for  ( int  i = 0 ; i < myFollow.size(); i++ )  {
            if(myFollow.get(i).getUserID()==user.getUserID()){
                holder.attention_btn.setText("已关注");
                holder.attention_btn.setEnabled(false);
            }
        }

        if (user.getIconID()==1) holder.userImage.setBackgroundResource(R.drawable.headshot1);
        else if (user.getIconID()==2) holder.userImage.setBackgroundResource(R.drawable.headshot2);
        else if (user.getIconID()==3) holder.userImage.setBackgroundResource(R.drawable.headshot3);
        else if (user.getIconID()==4) holder.userImage.setBackgroundResource(R.drawable.headshot4);

        holder.attention_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*数据库添加关注*/
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(context, "OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                FollowController myUC=new FollowController(dbCon);
                myUC.followUser(list.get(position).getUserID());
                if(Activity.class.isInstance(context))
                {
                    Activity activity = (Activity)context;
                    Intent intent = new Intent(context, UserHomepageActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
