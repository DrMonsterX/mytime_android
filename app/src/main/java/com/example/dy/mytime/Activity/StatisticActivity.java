package com.example.dy.mytime.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dy.mytime.CompletenessPackage.CompletenessController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.github.mikephil.charting.animation.Easing;

public class StatisticActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private BarChart barChart1;
    private ImageButton goUserButton;
    private ImageButton rankButton;
    private TextView goSchedule;
    private TextView goTask;
    private TextView goCalendar;
    private TextView times;
    private MyDatabaseHelper dbHelper;
    private MyDatabaseController dbCon;
    private CompletenessController myCC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initView();

        goUserButton=(ImageButton)findViewById(R.id.goUserBtn);
        goUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, UserHomepageActivity.class);
                startActivity(intent);
            }
        });

        rankButton=(ImageButton)findViewById(R.id.rankBtn);
        rankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });

        goCalendar=(TextView)findViewById(R.id.goCalendar);
        goCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        goTask=(TextView)findViewById(R.id.goTask);
        goTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        goSchedule=(TextView)findViewById(R.id.goSchedule);
        goSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                SimpleDateFormat mydf = new SimpleDateFormat("yyyy-MM-dd");
                String nowDay = mydf.format(new Date());
                bundle.putString("dayStr", nowDay);
                intent.putExtras(bundle);
                intent.setClass(StatisticActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });

        times=(TextView)findViewById(R.id.times);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate1 = sdf.format(calendar1.getTime());
        String lastEndDate1 = sdf.format(calendar2.getTime());
        calendar1.add(Calendar.DATE,  - 7);
        calendar2.add(Calendar.DATE,  - 7);
        String lastBeginDate2 = sdf.format(calendar1.getTime());
        String lastEndDate2 = sdf.format(calendar2.getTime());
        calendar1.add(Calendar.DATE, -7);
        calendar2.add(Calendar.DATE, -7);
        String lastBeginDate3 = sdf.format(calendar1.getTime());
        String lastEndDate3 = sdf.format(calendar2.getTime());
        calendar1.add(Calendar.DATE, -7);
        calendar2.add(Calendar.DATE, -7);
        String lastBeginDate4 = sdf.format(calendar1.getTime());
        String lastEndDate4 = sdf.format(calendar2.getTime());
        calendar1.add(Calendar.DATE, -7);
        calendar2.add(Calendar.DATE, -7);
        String lastBeginDate5 = sdf.format(calendar1.getTime());
        String lastEndDate5 = sdf.format(calendar2.getTime());
        times.setText("   "+lastBeginDate5+"-"+lastEndDate5+"  "+lastBeginDate4+"-"+lastEndDate4+"  "+
                lastBeginDate3+"-"+lastEndDate3+"  "+lastBeginDate2+"-"+lastEndDate2+"  "+lastBeginDate1+"-"+lastEndDate1);


    }


    private void initView() {
        //柱状图
       barChart1 = (BarChart) findViewById(R.id.barChar);
       showBarChartAlong();
        //饼状图
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.rgb(255,255,255));

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(55f);
        mPieChart.setTransparentCircleRadius(60f);

        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterTextSize(18);
        mPieChart.setCenterTextColor(Color.DKGRAY);
        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);


        dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        dbCon=new MyDatabaseController(dbHelper);
        myCC=new CompletenessController();
        /*数据库得到完成度*/
        int value=myCC.getWeekCompleteness();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(value, "完成"));
        entries.add(new PieEntry(100-value, "未完成"));

        //设置数据
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setFormSize(14f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(16f);
    }



    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("本周完成度");
        return s;
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "我的任务");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
       colors.add(Color.rgb(6 ,83 ,54));
        colors.add(Color.rgb(178 ,223, 206));

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    //柱状图
    private void showBarChartAlong() {
        BarChartManager barChartManager = new BarChartManager(barChart1);

        dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
        dbCon=new MyDatabaseController(dbHelper);
        myCC=new CompletenessController();
        /*数据库得到历史完成度*/
        int[] history=myCC.getHistoryCompleteness();
        List<BarEntry> yVals = new ArrayList<>();
        yVals.add(new BarEntry(1f, history[0]));
        yVals.add(new BarEntry(2f, history[1]));
        yVals.add(new BarEntry(3f, history[2]));
        yVals.add(new BarEntry(4f, history[3]));
        yVals.add(new BarEntry(5f, history[4]));


        String label = "";
        barChartManager.showBarChart(yVals, label, Color.parseColor("#a8dbc8"));
    }

    private Context getContext(){return this;}
}