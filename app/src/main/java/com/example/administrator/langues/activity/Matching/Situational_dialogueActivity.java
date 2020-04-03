package com.example.administrator.langues.activity.Matching;


import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.video.CommonUtils;
import com.example.administrator.langues.activity.Matching.video.VideoManager;
import com.example.administrator.langues.fragment.SeekFragment;
import com.jaeger.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class Situational_dialogueActivity extends AppCompatActivity implements View.OnClickListener {
    //动画
    private static int START_ANIMATION=0;
    private static int CLOSE_ANIMATION=1;
    private Animation animation1;
    private Timer animTimer;
    private RelativeLayout text_tip;
    private Handler mHandler;


    private OrientationUtils orientationUtils;
    private boolean isPlay,isPause;
    private StandardGSYVideoPlayer videoPlayer;
    private String url2 = "http://47.106.76.8/resource/video/English/dream.m3u8";//http://47.106.76.8/resource/video/Japan/unnature.mp4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_dialogue);
        initView();
        //videoListener();
        startAnimTime();
    }

   /* private void videoListener() {
        //获取得到video所暂停的秒数

        //当视频暂停，则显示提示文字和动画
        text_tip.setVisibility(View.VISIBLE);
        startAnimTime();
        //当视频播放，则隐藏提示文字和动画
        text_tip.setVisibility(View.GONE);
        closeAnimTime();
    }*/

    //文字提示动画
    private void startAnimation(){
        text_tip.startAnimation(animation1);
    }
    //开始动画
    private  void startAnimTime(){
        animTimer.schedule(new TimerTask() {
            public void run() {
                Message m=Message.obtain();
                m.what=START_ANIMATION;
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
    //停止动画
    private void closeAnimTime(){  animTimer.cancel();}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case 返回按钮id://当点击放回，即退出时，将弹出警告对话框。
                new Alert_Dialog(Situational_dialogueActivity.this) {
                    @Override
                    public void btncancel() {
                        cancel();
                    }

                    @Override
                    public void btnconfirm() {
                        finish();

                    }
                }.show();
                break;*/
        }
    }
    private void initView() {
        //文字提示
        animTimer=new Timer();
        text_tip=findViewById(R.id.tip_text);
        animation1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_text_tips);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==START_ANIMATION){
                    startAnimation();
                }
            }
        };
        //视频播放
        videoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.videoPlayer);
        orientationUtils = new OrientationUtils(this, videoPlayer);
        GSYVideoOptionBuilder gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        gsyVideoOptionBuilder
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setCacheWithPlay(false)
                .setUrl(url2)
                .setVideoTitle("title")
                .setVideoAllCallBack(new GSYSampleCallBack(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            // 配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .build(videoPlayer);
        videoPlayer.startPlayLogic();
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayer.startWindowFullscreen(Situational_dialogueActivity.this, true, true);
            }
        });
    }

    private void resolveNormalVideoUI() {
        //增加title
        View view = (View) videoPlayer.getParent();
        SeekBar mPrgress = view.findViewById(R.id.progress);
        ProgressBar mBottomPrgress = view.findViewById(R.id.bottom_progressbar);
        TextView mTotalTimeTextView = view.findViewById(R.id.total);
        mTotalTimeTextView.setVisibility(View.INVISIBLE);
        mPrgress.setVisibility(View.INVISIBLE);
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 当前为横屏
            StatusBarUtil.hideFakeStatusBarView(Situational_dialogueActivity.this);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 当前为竖屏
            setStatusBar();
        }
    }
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),60);
    }
}
