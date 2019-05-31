package com.example.dy.mytime.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.UserPackage.FollowController;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private List<User> list;
    private Context context;

    public RankAdapter(List<User> list,Context context) {
        this.list = list;
        this.context=context;
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, number,percent;
        private ImageView userHead;

        public RankViewHolder(View attention_item) {
            super(attention_item);
            userName = (TextView) itemView.findViewById(R.id.userName);
            number = (TextView) itemView.findViewById(R.id.number);
            percent = (TextView) itemView.findViewById(R.id.percent);
            userHead = (ImageView) itemView.findViewById(R.id.userHead);
        }
    }

    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new  RankAdapter.RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder( RankAdapter.RankViewHolder holder, final int position) {
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context, "OurAPP.db", null, 1);
        MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
        UserController myUC=new UserController(dbCon);
        User user = list.get(position);
        holder.userName.setText(user.getUserName());
        holder.number.setText(position+1+"");
        holder.percent.setText(myUC.getWeekCompleteness(user.getUserID())+"%");
        if (user.getIconID()==0) holder.userHead.setBackgroundResource(R.drawable.headshot1);
        else if (user.getIconID()==1) holder.userHead.setBackgroundResource(R.drawable.headshot2);
        else if (user.getIconID()==2) holder.userHead.setBackgroundResource(R.drawable.headshot3);
        else if (user.getIconID()==3) holder.userHead.setBackgroundResource(R.drawable.headshot4);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}