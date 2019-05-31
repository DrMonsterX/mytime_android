package com.example.dy.mytime.Activity;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dy.mytime.DatabasePackage.MyDatabaseController;
import com.example.dy.mytime.DatabasePackage.MyDatabaseHelper;
import com.example.dy.mytime.R;
import com.example.dy.mytime.Adapter.SearchUserAdapter;
import com.example.dy.mytime.UserPackage.User;
import com.example.dy.mytime.UserPackage.UserController;

import java.util.ArrayList;
import java.util.List;

public class AttentionActivity extends AppCompatActivity {

    private ImageButton returnButton;

    private RecyclerView rv;
    private SearchUserAdapter adapter;
    private List<User> searchlist= new ArrayList<>();
    private TextView mEmptyTextView;
    private ImageView empty_image;
    private SearchView searchView;

    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }


    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        returnButton = (ImageButton) findViewById(R.id.returnBtn);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttentionActivity.this.finish();
            }
        });




        rv = (RecyclerView) findViewById(R.id.rv);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        empty_image = (ImageView) findViewById(R.id.empty_image);
        mEmptyTextView.setVisibility(View.INVISIBLE);
        empty_image.setVisibility(View.INVISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(this));

        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
            public boolean onQueryTextSubmit(String query)
            {

                if(TextUtils.isEmpty(query))
                {
                    //Toast.makeText(MainActivity.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(), "OurAPP.db", null, 1);
                    MyDatabaseController dbCon=new MyDatabaseController(dbHelper);
                    UserController myUC=new UserController();
                    User searchUser=myUC.getUser(Integer.parseInt(query));
                    /*数据库搜索ID为query的用户，添加到searchlist*/
                    searchlist.clear();
                    if(searchUser!=null)
                    {
                        searchlist.add(searchUser);
                    }

                    adapter = new SearchUserAdapter(AttentionActivity.this,searchlist);
                    //adapter.notifyDataSetChanged();
                    rv.setAdapter(adapter);
                    updateUI();
                }
                return true;
            }

            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
            public boolean onQueryTextChange(String newText)
            {
                return true;
            }
        });


    }

    private void updateUI() {
        if(searchlist.size()>0) {
            mEmptyTextView.setVisibility(View.INVISIBLE);
            empty_image.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);

        }
        else{
            rv.setVisibility(View.INVISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            empty_image.setVisibility(View.VISIBLE);
        }
    }

    private Context getContext(){return this;}
}
