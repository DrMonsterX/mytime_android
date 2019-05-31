package com.example.dy.mytime.Adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dy.mytime.R;
import com.example.dy.mytime.SchedulePackage.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private List<Schedule> list;

    public ScheduleAdapter(List<Schedule> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_scheduleName, tv_scheduleStartTime, tv_scheduleStopTime,tv_percentage;
        private SeekBar seekBar;
        int len ;
        int lenadd;



        public MyViewHolder(View schedule_item) {
            super(schedule_item);
            tv_scheduleName = (TextView) itemView.findViewById(R.id.tv_scheduleName);
            tv_scheduleStartTime = (TextView) itemView.findViewById(R.id.tv_scheduleStartTime);
            tv_scheduleStopTime = (TextView) itemView.findViewById(R.id.tv_scheduleStopTime);
            tv_percentage= (TextView) itemView.findViewById(R.id.tv_percentage);
            seekBar = (SeekBar) itemView.findViewById(R.id.myProcessBar);
            seekBar.setMax(100);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            });
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                len += 0;
                handler.sendEmptyMessageDelayed(1,1000);
                seekBar.setProgress(len);
            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Schedule sch = list.get(position);
        holder.tv_scheduleName.setText(sch.getscheduleName());
        holder.tv_scheduleStartTime.setText(sch.getscheduleStartTime()+" —— ");
        holder.tv_scheduleStopTime.setText(sch.getscheduleStopTime());
        holder.tv_percentage.setText(sch.getlen()+"%");
        holder.len = sch.getlen();
        holder.lenadd =sch.getlenAdd();

        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里利用回调来给RecyclerView设置点击事件
                    mItemClickListener.onItemClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ItemClickListener mItemClickListener ;
    public interface ItemClickListener{
        void onItemClick(int position) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }




}
