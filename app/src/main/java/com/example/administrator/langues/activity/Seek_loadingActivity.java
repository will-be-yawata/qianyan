package com.example.administrator.langues.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class Seek_loadingActivity extends AppCompatActivity {
    private static int START_ANIMATION=0;
    private TextView seek_loading_text;//匹配中....
    private ImageButton cancel_btn;
    private CircleImageView image_btn;
    private ImageView icon1,icon2,icon5,icon3,icon7;
    private Animation animation1 = null;
    private Animation animation2 = null;
    private Animation animation3 = null;
    private Animation animation4 = null;
    private Animation animation5 = null;
    private ObjectAnimator objectAnimator1=null;

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
                icon2.startAnimation(animation5);
                icon3.startAnimation(animation1);
                icon5.startAnimation(animation4);
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
        //匹配中动画
        objectAnimator1.setDuration(2000l);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator1.start();



        //取消匹配
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTime();
                finish();

            }
        });

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),Seek_successActivity.class);
                closeTime();
                startActivity(i);
            }
        });
    }

    private void animation() {
        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rotate_seek);
        animation2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_translate_seek);
        animation3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rerotate_seek);
        animation4 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_retranslate_seek);
        animation5 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_translate1_seek);
        objectAnimator1=ObjectAnimator.ofFloat(seek_loading_text,"translationX",20f,40f,20f);
    }

    private void init() {

        icon3=findViewById(R.id.icon3);
        icon1=findViewById(R.id.icon1);
        icon2=findViewById(R.id.icon2);
        icon5=findViewById(R.id.icon5);
        icon7=findViewById(R.id.icon7);
        seek_loading_text=findViewById(R.id.seek_loading_text);
        cancel_btn=findViewById(R.id.cancel_btn);
        image_btn=findViewById(R.id.seek_loading_user);

    }
    public  void startTime(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message m=Message.obtain();
                m.what=START_ANIMATION;
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
    public void closeTime(){
        timer.cancel();

    }
}

