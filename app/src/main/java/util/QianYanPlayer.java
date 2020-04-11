package util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.langues.activity.Matching.Situational_dialogueActivity;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;

import entry.Scence;
import entry.Stop_points;
import util.core.CalculateTool;

public class QianYanPlayer {
    private Scence scence;
    private int miss=150;//由于播放器无法精确获取当前进度,故加入了误差
    private Context context;
    private int current_judge_position;//当前需要比较的时间点位置
    private StandardGSYVideoPlayer gsyPlayer;
    private ArrayList<Integer> start_point_list=new ArrayList<>();
    private ArrayList<Integer> stop_point_list=new ArrayList<>();
    private ArrayList<String> sentences=new ArrayList<>();
    private ArrayList<Float> current_scores=new ArrayList<>();
    private OrientationUtils orientationUtils;
    private int total;//视频总时长
    private boolean isPlay,isPause;

    private ArrayList<Stop_points> stop_points=new ArrayList<>();


    public QianYanPlayer(Context context,Scence s,StandardGSYVideoPlayer gsyplayer) {
        gsyPlayer=gsyplayer;
        current_judge_position=0;
        scence=s;
        stop_points=scence.getStop_points();
        orientationUtils=new OrientationUtils((Activity) context,gsyPlayer);
        split();
    }

    public void play(TimeCallBack callBack){
//        total=gsyPlayer.getDuration();


//        gsyPlayer.setGSYVideoProgressListener(new GSYVideoProgressListener() {
//            @Override
//            public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
//                Log.i("test_jingdu","progress:"+progress+",duration:"+duration+",currentPosition"+currentPosition);
//            }
//        });
        gsyPlayer.setGSYVideoProgressListener(new GSYVideoProgressListener() {
            @Override
            public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                total=duration;
                String temp=judge(currentPosition);
                if(temp!=""){
                    callBack.process_stop(temp);
                }
                Log.i("test_jingdu","progress:"+progress+",duration:"+duration+",currentPosition"+currentPosition);
            }
        });
        gsyPlayer.startPlayLogic();

    }
    public void pause(){
        gsyPlayer.getGSYVideoManager().pause();
    }

    public float getSimilarityRatio(String str, String target){
        float res= CalculateTool.getSimilarityRatio(str,target);
        return res;
    }

    public float getScore(String str){
        Log.i("test_score","开始比较:用户说的是:"+str+",答案句子"+stop_points.get(current_judge_position-1).getSentence());
        String current_sentence=stop_points.get(current_judge_position-1).getSentence();
        float score=(float)getSimilarityRatio(str,current_sentence)*100;
        current_scores.add(current_judge_position-1,score);
        return score;
    }

    public void release(){
        gsyPlayer.release();
    }
    public void init(){
        initGsyPlayer();
    }


    public void initGsyPlayer(){
//        orientationUtils = new OrientationUtils(this, gsyPlayer);

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
                .build(gsyPlayer);
        gsyPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                gsyPlayer.startWindowFullscreen(context, true, true);
            }
        });
    }

    public void startListening(TimeCallBack callBack){


    }


    public void split(){

        for(Stop_points sp:stop_points){
            start_point_list.add(sp.getStart_time());
            stop_point_list.add(sp.getEnd_time());
            sentences.add(sp.getSentence());
        }
    }

    public String judge(int current){
        //判断是否是暂停点,如果是,返回该暂停点对应的句子,否则返回空句子

            if(Math.abs(start_point_list.get(current_judge_position)*1000-current)<=miss){
                String res=stop_points.get(current_judge_position).getSentence();
                if(current_judge_position<stop_points.size()){
                    current_judge_position++;
                }else if(current_judge_position==stop_points.size()){
                    current_judge_position=0;
                }

                return res;
            }


        return "";
    }


    public interface TimeCallBack{
        //播放视频中需要处理的逻辑
        //-----------------------
        //视频播放到了要暂停的时候,会调用此方法
        public void process_stop(String sentence);
    }

}
