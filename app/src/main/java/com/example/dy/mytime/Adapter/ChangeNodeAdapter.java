package com.example.dy.mytime.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.Node;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeNodeAdapter extends RecyclerView.Adapter<ChangeNodeAdapter.ChangeNodeViewHolder> {

    private List<Node> list;
    private Context context;
    private Calendar calendar;
    private DatePickerDialog dialog;
    public HashMap<Integer, String> name = new HashMap<>();
    public HashMap<Integer, String> time = new HashMap<>();


    public ChangeNodeAdapter(List<Node> list, Context context) {
        this.context=context;
        this.list = list;
    }

    class ChangeNodeViewHolder extends RecyclerView.ViewHolder {
        private EditText taskNodeName;
        private TextView  nodeTime;
        private ImageButton nodeDeleteBtn;

        public ChangeNodeViewHolder(View change_node_item) {
            super(change_node_item);
            taskNodeName = (EditText) itemView.findViewById(R.id.taskNodeName);
            nodeTime = (TextView) itemView.findViewById(R.id.nodeTime);
            nodeDeleteBtn =(ImageButton) itemView.findViewById(R.id.nodeDeleteBtn);
        }
    }

    @Override
    public ChangeNodeAdapter.ChangeNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_node_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ChangeNodeAdapter.ChangeNodeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ChangeNodeAdapter.ChangeNodeViewHolder holder, final int position) {

        Node node = list.get(position);
        holder.taskNodeName.setText(node.getnodeName());
        holder.nodeTime.setText(node.getnodeTime());
        holder.nodeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).getnodeId();//参数ID

                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.nodeTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + monthOfYear + "日-->" + dayOfMonth);
                                holder.nodeTime.setText(year + "-" + (monthOfYear+1) + "-"
                                        + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        holder.taskNodeName.addTextChangedListener(new MyTextChangedListener(holder,name));
        holder.nodeTime.addTextChangedListener(new MyTextChangedListener(holder,time));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyTextChangedListener implements TextWatcher {

        public ChangeNodeAdapter.ChangeNodeViewHolder holder;
        public HashMap<Integer, String> contents;

        public MyTextChangedListener(ChangeNodeAdapter.ChangeNodeViewHolder holder,HashMap<Integer, String> contents){
            this.holder = holder;
            this.contents = contents;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(holder != null && contents != null){
                int adapterPosition = holder.getAdapterPosition();
                contents.put(adapterPosition,editable.toString());
            }
        }
    }



}