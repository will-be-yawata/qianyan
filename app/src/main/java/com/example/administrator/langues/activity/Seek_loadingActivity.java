package com.example.administrator.langues.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.langues.R;

import java.util.Timer;
import java.util.TimerTask;


public class Seek_loadingActivity extends AppCompatActivity {
    private static int START_ANIMATION=0;
    private ImageButton cancel_btn;
    private ImageView icon1,icon2,icon5,icon3,icon7;
    private Animation animation1 = null;
    private Animation animation2 = null;
    private Animation animation3 = null;

    private Timer timer;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==START_ANIMATION){
                icon1.clearAnimation();
                icon2.clearAnimation();
                icon5.clearAnimation();
                icon3.clearAnimation();
                icon7.clearAnimation();
                /****************************************/
                icon1.startAnimation(animation2);
                icon2.startAnimation(animation2);
                icon3.startAnimation(animation1);
                icon5.startAnimation(animation2);
                icon7.startAnimation(animation3);

            }
        }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_loading);
        init();
        animation();
        timer=new Timer();
        startTime();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTime();
                finish();

            }
        });
    }
    private void animation() {
        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rotate_seek);
        animation2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_alpha);
        animation3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rerotate_seek);
    }

    private void init() {

        icon3=findViewById(R.id.icon3);
        icon1=findViewById(R.id.icon1);
        icon2=findViewById(R.id.icon2);
        icon5=findViewById(R.id.icon5);
        icon7=findViewById(R.id.icon7);
        cancel_btn=findViewById(R.id.cancel_btn);

    }
    public  void startTime(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message m=new Message();
                m.what=START_ANIMATION;
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
    public void closeTime(){
        timer.cancel();

    }
}

