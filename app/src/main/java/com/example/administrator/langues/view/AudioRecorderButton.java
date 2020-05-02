package com.example.administrator.langues.view;

import android.annotation.SuppressLint;
import android.content.Context;
//import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.langues.R;

@SuppressLint("AppCompatCustomView")
public class AudioRecorderButton extends Button {
    private static final int MSG_AUDIO_PREPARED=0x110;
    private static final int MSG_VOICE_CHANGED=0x111;
    private static final int MSG_DIALOG_DIMISS=0x112;
    private static final int DISTANCE_Y_CANCEL=50;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private int mCurState=STATE_NORMAL;

    //已经开始录音
    private boolean isRecording=false;
    private float mTime;
    //是否触发longclick
    private boolean mReady;

    private Runnable mGetVoiceLevelRunnable;
    private Handler mHandler;
    private DialogRecorder mDialog;
    private AudioManager mAudioManager;
    private AudioFinishRecorderListener audioFinishRecorderListener;
    public AudioRecorderButton(Context context) {
        this(context,null);
    }
    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mDialog=new DialogRecorder(getContext());
        String dir = Environment.getExternalStorageDirectory()+"/qianyan/recorder_audios";
        mAudioManager=AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStateListener(() -> mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED));

        setOnLongClickListener(view -> {
            mReady=true;
            mAudioManager.prepareAudio();
            return false;
        });
    }
    @SuppressLint("HandlerLeak")
    private void init() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_AUDIO_PREPARED:
                        //TODO 真正显示应该在audio end prepare以后
                        mDialog.showRecorderDialog();
                        isRecording=true;
                        new Thread(mGetVoiceLevelRunnable).start();

                        break;
                    case MSG_VOICE_CHANGED:
                        mDialog.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                        break;
                    case MSG_DIALOG_DIMISS:
                        mDialog.dimissDialog();
                        break;
                }
            }
        };
        mGetVoiceLevelRunnable= () -> {
            while(isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                //根据x，y的坐标，判断是否想要取消
                if(isRecording){
                    if(wanToCancel(x,y)){
                        changeState(STATE_WANT_TO_CANCEL);
                    }else{
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!mReady){
                    reset();
                    return super.onTouchEvent(event);
                }
                if(!isRecording|| mTime<0.6f){
                    mDialog.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS,1300);
                }else if(mCurState==STATE_RECORDING){//正常录制结束
                    mDialog.dimissDialog();
                    mAudioManager.release();
                    if(audioFinishRecorderListener!=null){
                        audioFinishRecorderListener.onFinish(mTime,mAudioManager.getCurrentFilePath());
                    }
                }else if(mCurState==STATE_WANT_TO_CANCEL){
                    mDialog.dimissDialog();
                    mAudioManager.cancel();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }
    private boolean wanToCancel(int x, int y) {
        return (x<0||x>getWidth() || y<-DISTANCE_Y_CANCEL||y>getHeight()+DISTANCE_Y_CANCEL);
    }
    private void changeState(int state) {
        if(mCurState!=state){
            mCurState=state;
            switch(state){
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.str_recorder_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_recording);
                    if(isRecording){
                        mDialog.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_want_cancel);
                    mDialog.wantToCancel();
                    break;
            }
        }
    }
    /**
     * 恢复状态以及标志位
     */
    private void reset() {
        isRecording =false;
        mReady=false;
        mTime=0;
        changeState(STATE_NORMAL);
    }
    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        audioFinishRecorderListener=listener;
    }
    /**
     * 录音完成后的回调
     */
    public interface AudioFinishRecorderListener{
        void onFinish(float seconds,String filePath);
    }
}