package com.example.administrator.langues.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Communicate_loadingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton openquiet_btn,closequiet_btn,opensound_btn,closesound_btn,finish_btn;
    private TextView cl_time;
    private int time= 0;
    private boolean t_btn=false;//判断按钮是否被点击
    //统计通话时间参数
    private Timer timer;
    private TimerTask timerTask;
    private DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate_loading);
        init();
        getTime();
        statTime();//开始计时
        openquiet_btn.setOnClickListener(this);
        closequiet_btn.setOnClickListener(this);
        opensound_btn.setOnClickListener(this);
        closesound_btn.setOnClickListener(this);
        finish_btn.setOnClickListener(this);
    }
    private void getTime() {
    }
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.openquiet_btn:
                openquiet_btn.setVisibility(view.GONE);
                closequiet_btn.setVisibility(view.VISIBLE);
                break;
            case R.id.closequiet_btn:
                openquiet_btn.setVisibility(view.VISIBLE);
                closequiet_btn.setVisibility(view.GONE);
                break;
            case R.id.opensound_btn:
                opensound_btn.setVisibility(view.GONE);
                closesound_btn.setVisibility(view.VISIBLE);
                break;
            case R.id.closesound_btn:
                opensound_btn.setVisibility(view.VISIBLE);
                closesound_btn.setVisibility(view.GONE);
                break;
            case R.id.finish_btn:
                t_btn=true;
                finish();
                break;
        }
    }
    private void init() {
        openquiet_btn=findViewById(R.id.openquiet_btn);
        closequiet_btn=findViewById(R.id.closequiet_btn);
        opensound_btn=findViewById(R.id.opensound_btn);
        closesound_btn=findViewById(R.id.closesound_btn);
        finish_btn=findViewById(R.id.finish_btn);
        cl_time=findViewById(R.id.cl_time);
    }
    //统计通话时间
    private void statTime(){
        timer = new Timer();
        timerTask = new TimerTask(){
            @Override
            public void run() {
                if (!t_btn){
                    Message msg = new Message();
                    msg.what = 1;
                    //发送
                    handler.sendMessage(msg);
                }
            }
        };
        if(timer != null){
            timer.scheduleAtFixedRate(timerTask, 1000,1000);//严格按照时间执行
//        timer.schedule(timerTask, 1000, 1000);//如果时间过长，间隔时间会不准
        }
    }
    private void stopTime(){
        if(timer!= null){
            timer.cancel();
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    freshTime();
                    break;
                default:
                    stopTime();
                    break;
            }
        }
    };
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


