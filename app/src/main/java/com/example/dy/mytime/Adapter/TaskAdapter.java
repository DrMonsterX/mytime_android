package com.example.dy.mytime.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dy.mytime.CompletenessPackage.CompletenessController;

import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.FinishTaskController;
import com.example.dy.mytime.TaskPackage.Task;
import com.example.dy.mytime.TaskPackage.TaskController;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> list;
    private Context context;

    public TaskAdapter(List<Task> list,Context context) {
        this.context=context;
        this.list = list;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_taskName, tv_taskStartTime, tv_taskStopTime,tv_taskPercentage;
        private LinearLayout la_taskbg;
        private SeekBar seekBar;
        private CheckBox box;
        int len;
        int lenadd;


        public TaskViewHolder(View task_item) {
            super(task_item);
            tv_taskName = (TextView) itemView.findViewById(R.id.tv_taskName);
            tv_taskStartTime = (TextView) itemView.findViewById(R.id.tv_taskStartTime);
            tv_taskStopTime = (TextView) itemView.findViewById(R.id.tv_taskStopTime);
            tv_taskPercentage= (TextView) itemView.findViewById(R.id.tv_taskPercentage);
            la_taskbg = (LinearLayout) itemView.findViewById(R.id.la_taskbg);
            seekBar = (SeekBar) itemView.findViewById(R.id.taskProcessBar);
            box=(CheckBox)itemView.findViewById(R.id.taskCheckBox);
            seekBar.setMax(100);

        }

    }

    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new TaskAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder holder, final int position) {
        Task task = list.get(position);
        holder.tv_taskName.setText(task.gettaskName());
        holder.tv_taskStartTime.setText(task.gettaskStartTime()+" - ");
        holder.tv_taskStopTime.setText(task.gettaskStopTime());
        holder.tv_taskPercentage.setText(task.getlen()+"%");

        holder.seekBar.setProgress(task.getlen());

        if (task.gettaskTag().equals("默认")) holder.la_taskbg.setBackgroundColor(Color.parseColor("#f1ef6b7a"));//修改了颜色
        else if (task.gettaskTag().equals("追星")) holder.la_taskbg.setBackgroundColor(Color.parseColor("#b2dfce"));
        else if (task.gettaskTag().equals("恋爱")) holder.la_taskbg.setBackgroundColor(Color.parseColor("#f59d9d"));
        else if (task.gettaskTag().equals("日常")) holder.la_taskbg.setBackgroundColor(Color.parseColor("#F5F5DC"));
        else if (task.gettaskTag().equals("工作")) holder.la_taskbg.setBackgroundColor(Color.parseColor("#f4d0af"));

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里利用回调来给RecyclerView设置点击事件
                    mItemClickListener.ontaskItemClick(position);
                }
            });
        }

        if(task.gettaskFinish()) holder.box.setChecked(true);
        else holder.box.setChecked(false);

        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub

                FinishTaskController myTC=new FinishTaskController();
                CompletenessController myCC=new CompletenessController();
                if(isChecked){
                    /*修改任务的isfinifshed为true*/
                    myTC.changeTaskFinish(list.get(position).gettaskID(),1);
                    myCC.updateCompleteness();
                }else{
                    /*改为false*/
                    myTC.changeTaskFinish(list.get(position).gettaskID(),0);
                    myCC.updateCompleteness();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private TaskAdapter.taskItemClickListener mItemClickListener;

    public interface taskItemClickListener {
        void ontaskItemClick(int position);
    }

    public void setOntaskItemClickListener(TaskAdapter.taskItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;

    }
}