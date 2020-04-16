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
import android.view.MotionEvent;
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

import entry.Scence;
import util.QianYanPlayer;
import util.Scence_Talk_Tool;
import util.VoiceTool;
import util.core.LanguageTool;


public class Situational_dialogueActivity extends AppCompatActivity implements View.OnClickListener {
    //动画
    private static int START_ANIMATION=0;
    private static int CLOSE_ANIMATION=1;
    private String s_id;
    private VoiceTool voiceTool;
    private Animation animation1;
    private Timer animTimer;
    private RelativeLayout text_tip;
    private ImageButton start_record_btn;
    private Handler mHandler;
    private Scence current_scence;
    private String current_sentence;//当前需要说出的答案句子
    private Scence_Talk_Tool scence_talk_tool=new Scence_Talk_Tool();
    private QianYanPlayer qianYanPlayer;

    private OrientationUtils orientationUtils;
    private boolean isPlay,isPause;
    private StandardGSYVideoPlayer videoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle=getIntent().getBundleExtra("bundle");
        s_id=bundle.getString("MID");



        super.onCreate(savedInstanceState);
        scence_talk_tool.getScenceById(Integer.parseInt(s_id), new Scence_Talk_Tool.getScenceByIdCallback() {
            @Override
            public void onSuccess(Scence s) {
                current_scence=s;

                setContentView(R.layout.activity_situational_dialogue);
                voiceTool=new VoiceTool(getBaseContext(),s);


                initView();
                //videoListener();
                startAnimTime();
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.release();
    }






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
        start_record_btn=findViewById(R.id.start_record_btn);
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
        qianYanPlayer=new QianYanPlayer(Situational_dialogueActivity.this,current_scence,videoPlayer);
        orientationUtils = new OrientationUtils(this, videoPlayer);

        qianYanPlayer.init();
        qianYanPlayer.play(new QianYanPlayer.TimeCallBack() {
            @Override
            public void process_stop(String sentence) {
                Log.i("test_point",sentence);
                showTips("请读出"+sentence);
                start_record_btn.setEnabled(true);
                current_sentence=sentence;
//                qianYanPlayer.pause();

            }
        });
//        qianYanPlayer.startListening();



       start_record_btn.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               switch (motionEvent.getAction()){
                   case MotionEvent.ACTION_DOWN:
                       showTips("请开始说话");
                        voiceTool.getVoice(Situational_dialogueActivity.this,LanguageTool.ENGLISH);
                       break;
                       case MotionEvent.ACTION_UP:
                           showTips(voiceTool.getCurrent_sentence());
                           voiceTool.stopVoice();
                           break;

               }
               return false;
           }
       });

        start_record_btn.setEnabled(false);

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

    private void showTips(String tip){
        Toast.makeText(getBaseContext(),tip,Toast.LENGTH_LONG).show();
    }
}
