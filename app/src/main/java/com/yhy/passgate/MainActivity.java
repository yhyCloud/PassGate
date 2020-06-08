package com.yhy.passgate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTextViewName;
    private TextView mTextViewTime;
    private TextView mTextViewPermission;
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changStatusIconCollor(true);
        mToolbar = findViewById(R.id.toolBar);
//        mToolbar.setTitle("平安成电智慧通行");
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);

        Window window = getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.ToolBarBackGround));

        mTextViewTime = findViewById(R.id.textViewTime);
        mTextViewPermission = findViewById(R.id.permission);
        TextPaint tp = mTextViewPermission.getPaint();
        tp.setFakeBoldText(true);



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        mTextViewTime.setText(simpleDateFormat.format(date)+"  "+simpleDateFormat.format(date)+"  "+simpleDateFormat.format(date)
        +" "+simpleDateFormat.format(date));
        mTextViewTime.setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));  // 添加跑马灯功能
        mTextViewTime.setMarqueeRepeatLimit(Integer.MAX_VALUE); // 跑马灯滚动次数，此处已设置最大值
        mTextViewTime.setFocusable(true); // 获得焦点
        mTextViewTime.setFocusableInTouchMode(true); // 通过触碰获取焦点的能力
        mTextViewTime.setSingleLine(true);
        mTextViewTime.setSelected(true);

        mTextViewName = findViewById(R.id.textViewName);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String name = pref.getString("name", "");
        if (name != null) {
            mTextViewName.setText(name);
        }

        mTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nameEdit = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("请输入姓名")
                        .setView(nameEdit).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name;
                        name = nameEdit.getText().toString();
                        mTextViewName.setText(name);
                        mTextViewName.setFocusedByDefault(true);
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("name",name);
                        editor.apply();

                    }
                })
                        .setNegativeButton("取消",null).show();
            }
        });

    }


@SuppressLint("AppCompatCustomView")
public class AlwaysMarqueeTextView extends TextView {
    public AlwaysMarqueeTextView(Context context) {
        super(context);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
    public void changStatusIconCollor(boolean setDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(setDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }




}