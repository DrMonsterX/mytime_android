package com.example.dy.mytime.Activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.dy.mytime.R;


public class MyProcessBar extends android.support.v7.widget.AppCompatSeekBar {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyProcessBar(Context context) {
        super(context);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        this.setMax(100);
        this.setThumbOffset(dip2px(getContext(), 0));
        this.setBackgroundResource(R.drawable.sbg);
        int padding = dip2px(getContext(),(float)0.5);
        this.setPadding(padding*20, 0, padding*20, 0);
        this.setProgressDrawable(getResources().getDrawable(R.drawable.snailbar_define_style));
    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return false ;
    }

}
