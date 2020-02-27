package com.example.administrator.langues.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import entry.User;
import util.Url;
import util.core.ChatOperation;
import util.core.PairingOperation;

public class Seek_successActivity extends AppCompatActivity {
    private final static int START_ANIMATION=0;
    private final static int STOP_ANIMATION=1;
    private ImageView seeksuccess_icon,red_user_img,blue_user_img;
    private RelativeLayout red_user,blue_user;
    private Timer timer;
    private int width,height;
    private ObjectAnimator objectAnimator1,objectAnimator2,objectAnimator3,
            objectAnimator4,objectAnimator5,objectAnimator6,objectAnimator7;
    private AnimatorSet animSet,animSet1,animSet2;
    private Handler mHandler;
    private ChatOperation chatOperation=new ChatOperation();
    private boolean stopAnim=false;
    private int status=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_success);
        init();
        getdisplay();
        startTime();
        listener();
        if(status== PairingOperation.WAIT) {
            runOnUiThread(()->Toast.makeText(Seek_successActivity.this,"status:WAIT",Toast.LENGTH_SHORT).show());
            chatOperation.answerCall();
        }else if(status== PairingOperation.PAIRING){
            String enemyPhone=getIntent().getStringExtra("enemyPhone");
            runOnUiThread(()->Toast.makeText(Seek_successActivity.this,"status:PAIRING\nenemyPhone:"+enemyPhone,Toast.LENGTH_SHORT).show());
            chatOperation.voiceCall(enemyPhone);
        }else{
            closeTime();
            finish();
        }
    }
    private void getdisplay() {
        // 通过WindowManager获取屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;
    }
    private void startAnim() {
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
    @SuppressLint("HandlerLeak")
    private void init() {
        seeksuccess_icon=findViewById(R.id.seeksuccess_icon);
        red_user=findViewById(R.id.red_user);
        blue_user=findViewById(R.id.blue_user);

        red_user_img=findViewById(R.id.red_user_img);
        blue_user_img=findViewById(R.id.blue_user_img);

        ImageOptions imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
                .setRadius(DensityUtil.dip2px(70))
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
        x.image().bind(red_user_img,Url.USER_IMG+ User.getInstance().getImg(),imageOptions);
        imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
                .setRadius(DensityUtil.dip2px(70))
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
        x.image().bind(blue_user_img,Url.USER_IMG+getIntent().getStringExtra("enemyImg"),imageOptions);

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

        timer=new Timer();

        status=getIntent().getIntExtra("status",2);

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case START_ANIMATION:
                        startAnim();
                        stopAnim=true;
                        break;
                    case STOP_ANIMATION:
                        closeTime();
                        break;
                }
            }
        };
    }
    private void listener(){
        chatOperation.addCallStateListener(new ChatOperation.StateListenerCallback() {
            public void accepted() {
                Intent intent=new Intent(Seek_successActivity.this,Communicate_loadingActivity.class);
                intent.putExtra("enemyPhone",getIntent().getStringExtra("enemyPhone"));
                intent.putExtra("enemyImg",getIntent().getStringExtra("enemyImg"));
                startActivity(intent);
                closeTime();
                finish();
                chatOperation.closeCallStateListener();
            }
            public void disconnected() {}
        });
    }
    private  void startTime(){
        timer.schedule(new TimerTask() {
            public void run() {
                Message m=Message.obtain();
                if(stopAnim){
                    m.what=STOP_ANIMATION;
                }else {
                    m.what = START_ANIMATION;
                }
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
    private void closeTime(){ timer.cancel(); }
}
