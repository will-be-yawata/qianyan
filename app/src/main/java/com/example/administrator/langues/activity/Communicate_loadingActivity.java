package com.example.administrator.langues.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import entry.User;
import util.EMHelp;
import util.Url;

public class Communicate_loadingActivity extends AppCompatActivity {
    private final static int COUNTTIME=0;
    private ImageButton openquiet_btn,closequiet_btn,opensound_btn,closesound_btn,finish_btn;
    private TextView cl_time;
    private int time= 0;
    private boolean t_btn=false;//判断按钮是否被点击
    //统计通话时间参数
    private Timer timer;
    private TimerTask timerTask;
    private DecimalFormat decimalFormat;
    private ImageView userImg;
    private TextView userName;
    private Handler mHandl ;

    private EMHelp emHelp=new EMHelp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate_loading);
        init();
        statTime();//开始计时
        listener();
    }
    @SuppressLint("HandlerLeak")
    private void init() {
        openquiet_btn=findViewById(R.id.openquiet_btn);
        closequiet_btn=findViewById(R.id.closequiet_btn);
        opensound_btn=findViewById(R.id.opensound_btn);
        closesound_btn=findViewById(R.id.closesound_btn);
        finish_btn=findViewById(R.id.finish_btn);
        cl_time=findViewById(R.id.cl_time);
        userName=findViewById(R.id.com_user_name);
        userImg=findViewById(R.id.com_user_img);

        ImageOptions imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
                .setRadius(DensityUtil.dip2px(70))
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
        x.image().bind(userImg,Url.USER_IMG+getIntent().getStringExtra("enemyImg"),imageOptions);

        timer = new Timer();

        mHandl=new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case COUNTTIME:
                        freshTime();
                        break;
                    default:
                        stopTime();
                        break;
                }
            }
        };
    }
    private void listener(){
        openquiet_btn.setOnClickListener(view -> {
            openquiet_btn.setVisibility(View.GONE);
            closequiet_btn.setVisibility(View.VISIBLE);
        });
        closequiet_btn.setOnClickListener(view -> {
            openquiet_btn.setVisibility(View.VISIBLE);
            closequiet_btn.setVisibility(View.GONE);
        });
        opensound_btn.setOnClickListener(view -> {
            opensound_btn.setVisibility(View.GONE);
            closesound_btn.setVisibility(View.VISIBLE);
        });
        closesound_btn.setOnClickListener(view -> {
            opensound_btn.setVisibility(View.VISIBLE);
            closesound_btn.setVisibility(View.GONE);
        });
        finish_btn.setOnClickListener(view -> {
            t_btn=true;
            emHelp.endCall();
            finish();
        });
        emHelp.addCallStateListener(new EMHelp.StateListenerCallback() {
            public void accepted() {}
            public void disconnected() {
                finish();
                emHelp.closeCallStateListener();
            }
        });
    }
    //统计通话时间
    private void statTime(){
        if(timer != null){
            timer.scheduleAtFixedRate(new TimerTask(){
                public void run() {
                    if (!t_btn){
                        Message msg = new Message();
                        msg.what = COUNTTIME;
                        mHandl.sendMessage(msg);
                    }
                }
            },1000,1000);
        }
    }
    private void stopTime(){
        if(timer!= null){
            timer.cancel();
        }
    }
    private void freshTime() {
        time++;
        cl_time.setText(formatTime(time));
    }
    private String formatTime(int time) {
        if(decimalFormat == null){
            decimalFormat = new DecimalFormat("00");
        }
        String hh = decimalFormat.format(time / 3600);
        String mm = decimalFormat.format(time % 3600 / 60);
        String ss = decimalFormat.format(time % 60);
        return hh + ":" + mm + ":" + ss;
    }
}


