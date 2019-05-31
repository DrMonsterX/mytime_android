package com.example.dy.mytime.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.Task;

import java.util.List;

public class MonthTaskAdapter extends RecyclerView.Adapter<MonthTaskAdapter.MonthTaskViewHolder> {

    private List<Task> list;

    public MonthTaskAdapter(List<Task> list) {
        this.list = list;
    }

    class MonthTaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_monthTaskName, tv_monthTaskStartTime, tv_monthTaskStopTime;
        private LinearLayout la_monthTask_bg;

        public MonthTaskViewHolder(View monthtask_item) {
            super(monthtask_item);
            tv_monthTaskName = (TextView) itemView.findViewById(R.id.tv_monthTaskName);
            tv_monthTaskStartTime = (TextView) itemView.findViewById(R.id.tv_monthTaskStartTime);
            tv_monthTaskStopTime = (TextView) itemView.findViewById(R.id.tv_monthTaskStopTime);
            la_monthTask_bg= (LinearLayout) itemView.findViewById(R.id.la_monthTask_bg);

        }
    }

    @Override
    public MonthTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthtask_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MonthTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonthTaskViewHolder holder, final int position) {
        Task task = list.get(position);
        holder.tv_monthTaskName.setText(task.gettaskName());
        holder.tv_monthTaskStartTime.setText(task.gettaskStartTime());
        holder.tv_monthTaskStopTime.setText(task.gettaskStopTime());
        if (task.gettaskTag().equals("默认")) holder.la_monthTask_bg.setBackgroundColor(Color.parseColor("#acdff9"));
        else if (task.gettaskTag().equals("追星")) holder.la_monthTask_bg.setBackgroundColor(Color.parseColor("#8bd8f9"));
        else if (task.gettaskTag().equals("恋爱")) holder.la_monthTask_bg.setBackgroundColor(Color.parseColor("#73c2f5"));
        else if (task.gettaskTag().equals("日常")) holder.la_monthTask_bg.setBackgroundColor(Color.parseColor("#6dc1f7"));
        else if (task.gettaskTag().equals("工作")) holder.la_monthTask_bg.setBackgroundColor(Color.parseColor("#58b7ef"));

        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里利用回调来给RecyclerView设置点击事件
                    mItemClickListener.onmtItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private mtItemClickListener mItemClickListener ;
    public interface mtItemClickListener{
        void onmtItemClick(int position) ;
    }
    public void setOnmtItemClickListener(mtItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }

}
