package com.example.administrator.langues.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import entry.Scence;
import util.Scence_Talk_Tool;

public class TestVideo extends AppCompatActivity {

    private OrientationUtils orientationUtils;
    private boolean isPlay, isPause;
    private Scence scence;
    private StandardGSYVideoPlayer videoPlayerTest;
    private String url2 = "http://47.106.76.8/resource/video/Japan/unnature.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video);
        Scence_Talk_Tool scence_talk_tool=new Scence_Talk_Tool();
        scence_talk_tool.getScenceById(2, new Scence_Talk_Tool.getScenceByIdCallback() {
            @Override
            public void onSuccess(Scence s) {
                scence=s;
                initView();
            }

            @Override
            public void onError() {

            }
        });

    }

    private void initView() {
        videoPlayerTest = (StandardGSYVideoPlayer) findViewById(R.id.videoPlayerTest);
        resolveNormalVideoUI();
        orientationUtils = new OrientationUtils(this, videoPlayerTest);
        GSYVideoOptionBuilder gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        gsyVideoOptionBuilder
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setCacheWithPlay(false)
                .setUrl(scence.getMedia_path())
                .setVideoTitle(scence.getMedia_name())
                .setVideoAllCallBack(new GSYSampleCallBack() {
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
                .build(videoPlayerTest);
        videoPlayerTest.startPlayLogic();
        videoPlayerTest.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//直接横屏
                orientationUtils.resolveByClick();
//第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayerTest.startWindowFullscreen(TestVideo.this, true, true);
            }
        });
    }

    private void resolveNormalVideoUI() {
//增加title
        View view = (View) videoPlayerTest.getParent();
        SeekBar mPrgress = view.findViewById(R.id.progress);
        ProgressBar mBottomPrgress = view.findViewById(R.id.bottom_progressbar);
        TextView mTotalTimeTextView = view.findViewById(R.id.total);
        mTotalTimeTextView.setVisibility(View.INVISIBLE);
        mPrgress.setVisibility(View.INVISIBLE);
        videoPlayerTest.getTitleTextView().setVisibility(View.GONE);
        videoPlayerTest.getBackButton().setVisibility(View.GONE);
//        videoPlayerTest.setBottomProgressBarDrawable(getResources().getDrawable(R.color.black));
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
            videoPlayerTest.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
// 当前为横屏
            StatusBarUtil.hideFakeStatusBarView(TestVideo.this);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
// 当前为竖屏
            setStatusBar();
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 60);
    }
}


