package com.example.administrator.langues.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import org.xutils.x;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import entry.User;
import util.Url;
import util.core.PairingOperation;


public class Seek_loadingActivity extends AppCompatActivity {
    private static int START_ANIMATION=0;
    private static int START_COUNT=1;
    private int count=0;
    private TextView seek_loading_text;//匹配中....
    private ImageButton cancel_btn;
    private CircleImageView user_img;
    private ImageView icon1,icon2,icon5,icon3,icon7;
    private Animation animation1,animation2,animation3,animation4,animation5;
    private ObjectAnimator objectAnimator1=null;

    private Timer animTimer,countTimer;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_loading);
        init();
        startAnimTime();
        startCountTime();
        setAndListener();
    }

    private void setAndListener() {
        objectAnimator1.setDuration(2000l);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator1.start();
        cancel_btn.setOnClickListener(view -> {
            closeAnimTime();
            finish();
        });
//        user_img.setOnClickListener(view -> {
//            Intent i=new Intent(getBaseContext(),Seek_successActivity.class);
//            closeAnimTime();
//            startActivity(i);
//        });
//        animTimer.schedule(new TimerTask() {
//            public void run() {//5秒后跳转到Seek_successActivity页面
//                Intent i=new Intent(getBaseContext(),Seek_successActivity.class);
//                finish();
//                closeAnimTime();
//                startActivity(i);
//            }
//        }, 5000);// 这里百毫秒
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        icon3=findViewById(R.id.icon3);
        icon1=findViewById(R.id.icon1);
        icon2=findViewById(R.id.icon2);
        icon5=findViewById(R.id.icon5);
        icon7=findViewById(R.id.icon7);
        seek_loading_text=findViewById(R.id.seek_loading_text);
        cancel_btn=findViewById(R.id.cancel_btn);
        user_img=findViewById(R.id.seek_loading_user);

        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rotate_seek);
        animation2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_translate_seek);
        animation3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_rerotate_seek);
        animation4 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_retranslate_seek);
        animation5 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_translate1_seek);
        objectAnimator1=ObjectAnimator.ofFloat(seek_loading_text,"translationX",20f,40f,20f);

        x.image().bind(user_img, Url.USER_IMG+ User.getInstance().getImg());
        cancel_btn.setClickable(false);

        animTimer=new Timer();
        countTimer=new Timer();

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==START_ANIMATION){
                    restartAnim();
                }else if(msg.what==START_COUNT){
                    startCount();
                }
            }
        };
    }
    private void startCount() {
        if(count==0){
            cancel_btn.setImageResource(R.mipmap.cancel_3);
            (new PairingOperation()).pairing(new PairingOperation.PairingCallback() {
                public void onSuccess(int status, HashMap<String, String> data) {

                }
                public void onCancelled() {

                }
                public void onError(String msg) {

                }
            });
        }else if(count==1){
            cancel_btn.setImageResource(R.mipmap.cancel_2);
        }else if(count==2){
            cancel_btn.setImageResource(R.mipmap.cancel_1);
        }else if(count>2){
            cancel_btn.setImageResource(R.mipmap.cancel);
            cancel_btn.setClickable(true);
            closeCountTime();
        }
        count++;
    }

    private void restartAnim() {
        icon1.clearAnimation();
        icon2.clearAnimation();
        icon5.clearAnimation();
        icon3.clearAnimation();
        icon7.clearAnimation();
        icon1.startAnimation(animation2);
        icon2.startAnimation(animation5);
        icon3.startAnimation(animation1);
        icon5.startAnimation(animation4);
        icon7.startAnimation(animation3);
    }
    private  void startAnimTime(){
        animTimer.schedule(new TimerTask() {
            public void run() {
                Message m=Message.obtain();
                m.what=START_ANIMATION;
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
    private void closeAnimTime(){
        animTimer.cancel();
    }
    private void startCountTime(){
        countTimer.schedule(new TimerTask() {
            public void run() {
                Message m=Message.obtain();
                m.what=START_COUNT;
                mHandler.sendMessage(m);
            }
        },0,1000);
    }
    private void closeCountTime(){
        countTimer.cancel();
    }
}

