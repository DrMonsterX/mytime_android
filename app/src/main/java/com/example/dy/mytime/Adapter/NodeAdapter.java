package com.example.dy.mytime.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.TaskPackage.Node;
import com.example.dy.mytime.TaskPackage.NodeController;
import com.example.dy.mytime.TaskPackage.TaskController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {

    private List<Node> list;
    private Context context;

    public NodeAdapter(List<Node> list, Context context) {
        this.list = list;
        this.context=context;
    }

    class NodeViewHolder extends RecyclerView.ViewHolder {
        private TextView nodeName, nodeTime;
        private CheckBox checkBox;

        public NodeViewHolder(View node_item) {
            super(node_item);
            nodeName = (TextView) itemView.findViewById(R.id.nodeName);
            nodeTime = (TextView) itemView.findViewById(R.id.nodeTime);
            checkBox =(CheckBox) itemView.findViewById(R.id.nodeCheckBox);
        }
    }

    @Override
    public NodeAdapter.NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new NodeAdapter.NodeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(NodeAdapter.NodeViewHolder holder, final int position) {
        Node node = list.get(position);
        holder.nodeName.setText(node.getnodeName());
        holder.nodeTime.setText(node.getnodeTime());
        if(node.getnodeFinish()) holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"OurAPP.db", null, 1);
                MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                NodeController myTC=new NodeController(dbCon);
                if(isChecked){
                    /*修改节点的isfinifshed为true*/
                    myTC.changeNodeFinish(list.get(position).getnodeId(),1);
                }else{
                    /*改为false*/
                    myTC.changeNodeFinish(list.get(position).getnodeId(),0);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
