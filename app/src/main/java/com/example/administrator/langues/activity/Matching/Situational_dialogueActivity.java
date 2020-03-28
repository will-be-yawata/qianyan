package com.example.administrator.langues.activity.Matching;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.video.CommonUtils;
import com.example.administrator.langues.activity.Matching.video.VideoManager;
import com.example.administrator.langues.fragment.SeekFragment;

import java.io.File;


public class Situational_dialogueActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton sit_return;
    private io.vov.vitamio.widget.VideoView mvideoView;
    private TextView tv_video_name;
    private SeekBar mSeekBar;
    private TextView tv_video_time;
    private ImageView iv_back;
    private ImageView iv_control;

    private VideoManager mVideoManager;

    private String videoName;
    private String videoPath;
    private String videoAllDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_dialogue);

        initView();
        //listener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sit_return:
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
                break;
        }
    }
    private void initView() {
        /*Intent intent = getIntent();
        videoName = intent.getStringExtra("name");
        videoPath = intent.getStringExtra("path");*/
        //Test
        videoName = "Test";
        videoPath = "android.resource://" + getPackageName() + "/raw/" + R.raw.video;
        Log.i("videoPath:",videoPath);
        sit_return=findViewById(R.id.sit_return);
        mvideoView=findViewById(R.id.mvideoview);
       tv_video_name = (TextView) findViewById(R.id.tv_video_name);
        mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
        tv_video_time = (TextView) findViewById(R.id.tv_video_time);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_control = (ImageView) findViewById(R.id.iv_control);
        tv_video_name.setText(videoName);
        initVideo();

    }
    private void initVideo(){
        mVideoManager = new VideoManager(mvideoView);


       // mVideoManager.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + R.raw.video));//通过uri网络播放
        mVideoManager.setVideoPath("http://47.106.76.8/resource/video/Japan/unnature.mp4");//通过文件路径播放
        Log.i("test11","1");
        mVideoManager.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(io.vov.vitamio.MediaPlayer mp) {
                mVideoManager.setPlaybackSpeed(mp,1.0f);
                long allDuration = mVideoManager.getDuration();
               videoAllDuration = CommonUtils.formatDuring(allDuration);
                mSeekBar.setMax((int) allDuration);
                mVideoManager.start();
            }
        });
        mVideoManager.setOnCompletionListener(new io.vov.vitamio.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(io.vov.vitamio.MediaPlayer mediaPlayer){

            }
        });

        mVideoManager.setOnVideoProgress(new VideoManager.OnVideoProgressListener() {
            @Override
            public void onProgress(long progress) {
               mSeekBar.setProgress((int) progress);
                tv_video_time.setText(CommonUtils.formatDuring(progress) + "/" + videoAllDuration);
            }
        });

        mVideoManager.setOnErrorListener(new io.vov.vitamio.MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(io.vov.vitamio.MediaPlayer mediaPlayer, int i,int i1) {

                return false;
            }
        });

       mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
                mVideoManager.seekTo(mSeekBar.getProgress());
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoManager.stopPlayback();
    }


    private void listener() {
        sit_return.setOnClickListener(this);
        iv_control.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }





}