package com.example.administrator.langues.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.langues.R;

import java.util.Timer;
import java.util.TimerTask;

public class Seek_successActivity extends AppCompatActivity {
    private final static int START_ANIMATION=0;
    private final static int STOP_ANIMATION=1;
    private ImageView seeksuccess_icon;
    private RelativeLayout red_user,blue_user;
    private Timer timer;
    private int width,height;
    private ObjectAnimator objectAnimator1=null;
    private ObjectAnimator objectAnimator2=null;
    private ObjectAnimator objectAnimator3=null;
    private ObjectAnimator objectAnimator4=null;
    private ObjectAnimator objectAnimator5=null;
    private ObjectAnimator objectAnimator6=null;
    private ObjectAnimator objectAnimator7=null;
    private AnimatorSet animSet;
    private AnimatorSet animSet1;
    private AnimatorSet animSet2;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case START_ANIMATION:

                    break;
                case STOP_ANIMATION:

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_success);
        init();
        animation();
        getdisplay();
        start();
        timer=new Timer();
        startTime();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent i=new Intent(getBaseContext(),Communicate_loadingActivity.class);
                finish();
                closeTime();
                startActivity(i);
            }
        }, 4000);//4秒后跳转到Communicate_loadingActivity页面





    }

    private void getdisplay() {
        // 通过WindowManager获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;
    }

    private void start() {
        animSet= new AnimatorSet();
        animSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
        animSet.setDuration(3000l);
        animSet.start();
        animSet1=new AnimatorSet();
        animSet1.play(objectAnimator4).with(objectAnimator5);
        animSet1.setDuration(3000l);
        animSet1.start();
        animSet2=new AnimatorSet();
        animSet2.play(objectAnimator6).with(objectAnimator7);
        animSet2.setDuration(3000l);
        animSet2.start();



    }

    private void animation() {
        //飞机动画
        objectAnimator1=ObjectAnimator.ofFloat(seeksuccess_icon,"scaleX",0f,20f);
        objectAnimator2=ObjectAnimator.ofFloat(seeksuccess_icon,"scaleY",0f,20f);
        objectAnimator3=ObjectAnimator.ofFloat(seeksuccess_icon,"alpha",0f);
        //红色用户
        objectAnimator4=ObjectAnimator.ofFloat(red_user,"translationX",600f,0f);
        objectAnimator5=ObjectAnimator.ofFloat(red_user,"translationY",-900f,272f);
        //蓝色用户
        objectAnimator6=ObjectAnimator.ofFloat(blue_user,"translationX",-600f,0f);
        objectAnimator7=ObjectAnimator.ofFloat(blue_user,"translationY",900f,-272f);


    }


    private void init() {
        seeksuccess_icon=findViewById(R.id.seeksuccess_icon);
        red_user=findViewById(R.id.red_user);
        blue_user=findViewById(R.id.blue_user);
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
