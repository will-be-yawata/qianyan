package com.example.administrator.langues.activity.Matching;

import android.animation.ObjectAnimator;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import entry.User;
import util.EMHelp;
import util.Url;
import util.core.PairingOperation;


public class Seek_loadingActivity extends AppCompatActivity {
    private static int START_ANIMATION=0;
    private static int START_COUNT=1;
    private int count=0;
    private TextView seek_loading_text;//匹配中....
    private ImageButton cancel_btn;
    private ImageView user_img;
    private ImageView icon1,icon2,icon5,icon3,icon7;
    private Animation animation1,animation2,animation3,animation4,animation5;
    private ObjectAnimator objectAnimator1=null;

    private Timer animTimer,countTimer;
    private Handler mHandler;
    private PairingOperation pairingOperation;
    private PairingOperation.CancelPairing cancelPairing;
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
            cancelPairing.cancel();
            closeAnimTime();
            finish();
        });
//        Button btn=findViewById(R.id.zjqbtn);
//        btn.setOnClickListener(view-> {
//            (new EMHelp()).answerCall();
//            Intent intent=new Intent(Seek_loadingActivity.this,Communicate_loadingActivity.class);
//            intent.putExtra("enemyImg","default.jpg");
//            startActivity(intent);
//        });
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

        ImageOptions imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
                .setRadius(DensityUtil.dip2px(70))
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
        x.image().bind(user_img,Url.USER_IMG+ User.getInstance().getImg(),imageOptions);
        cancel_btn.setClickable(false);

        animTimer=new Timer();
        countTimer=new Timer();

        pairingOperation=new PairingOperation();

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
            startPairing();
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
    private void startPairing(){
         cancelPairing= pairingOperation.pairing(new PairingOperation.PairingCallback() {
                    public void onSuccess(int status, HashMap<String, String> data) {
                        EMHelp emHelp=new EMHelp();
                        if(status==PairingOperation.WAIT){
                            runOnUiThread(()-> Toast.makeText(Seek_loadingActivity.this,"if(status==pairingop...)",Toast.LENGTH_SHORT).show());
                            emHelp.receiveListener(Seek_loadingActivity.this, from ->{
                                runOnUiThread(()-> Toast.makeText(Seek_loadingActivity.this,"get from...",Toast.LENGTH_LONG).show());
                                    pairingOperation.getEnemy(from, new PairingOperation.GetEnemyCallback() {
                                public void onSuccess(HashMap<String, String> data) {
                                    runOnUiThread(()-> Toast.makeText(Seek_loadingActivity.this,"getEnemySuccess",Toast.LENGTH_LONG).show());
                                    Intent intent=new Intent(Seek_loadingActivity.this,Seek_successActivity.class);
                                    intent.putExtra("status",PairingOperation.WAIT);
                                    intent.putExtra("enemyPhone",from);
                                    intent.putExtra("enemyImg",data.get("img"));
//                                        intent.putExtra("enemyName",data.get("name"));
//                                        intent.putExtra("enemyDan",data.get("dan"));
                                    closeAnimTime();
                                    closeCountTime();
                                    startActivity(intent);
                                    finish();
                                }
                                public void onError(String msg) {
                                    runOnUiThread(()->Toast.makeText(Seek_loadingActivity.this,"onError:"+msg,Toast.LENGTH_LONG).show());
                                }
                            });
                            });
                        }else if(status==PairingOperation.PAIRING){
                            Intent intent=new Intent(Seek_loadingActivity.this,Seek_successActivity.class);
                            intent.putExtra("status",PairingOperation.PAIRING);
                            intent.putExtra("enemyPhone",data.get("owner"));
                            intent.putExtra("enemyImg",data.get("img"));
//                            intent.putExtra("enemyName",data.get("?"));
//                            intent.putExtra("enemyDan",data.get("name"));
                            closeAnimTime();
                            closeCountTime();
                            startActivity(intent);
                            finish();
//                            emHelp.voiceCall(data.get("owner"));
////                            //跳转页面并渲染
////                            Log.i("mData","头像:"+data.get("img"));
////                            Log.i("mData","房间id:"+data.get("id"));
////                            Log.i("mData","段位:"+data.get("name"));
                        }
                    }
                    public void onCancelled() {}
                    public void onError(String msg) {}
                });
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