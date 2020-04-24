package util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.langues.activity.Matching.End_dialogueActivity;
import com.example.administrator.langues.activity.Matching.Situational_dialogueActivity;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entry.Scence;
import entry.Stop_points;
import util.core.CalculateTool;
import util.core.LanguageTool;

public class QianYanPlayer {
    private VoiceTool voiceTool;

    public void setVoiceTool(VoiceTool voiceTool) {
        this.voiceTool = voiceTool;
    }

    public VoiceTool getVoiceTool() {
        return voiceTool;
    }

    private Scence scence;
    private int miss = 50;//由于播放器无法精确获取当前进度,故加入了误差,必须小于所有暂停点的差值
    private Context context;
    private TimeCallBack timeCallBack;
    public static final String OVER_SIGN = "~~~###over###~~~";
    public static final int DETECT = 1;//检测标志
    public static final int ENDDETECT=-987;//不再进行"暂停点检测",等待播放完成
    public static final int PLAYEND = -99;//播放完成标志
    public static final int WAITING_PLAYEND=-435;//等待播放完成标志
    private int current_judge_position;//当前需要比较的时间点位置
    private StandardGSYVideoPlayer gsyPlayer;
    private String current_voice_result;//表示当前用户语音的结果
    private ArrayList<Integer> start_point_list = new ArrayList<>();
    private ArrayList<Integer> stop_point_list = new ArrayList<>();
    private ArrayList<String> sentences = new ArrayList<>();
    private ArrayList<Boolean> isPauses = new ArrayList<Boolean>();
    private HashMap<String, Float> current_scence_scores = new HashMap<>();
    private OrientationUtils orientationUtils;
    private int total;//视频总时长
    private boolean isPlay, isPause;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentPosition = gsyPlayer.getCurrentPositionWhenPlaying();
            int totalduration = gsyPlayer.getDuration();
            total=totalduration;

            if (msg.what == DETECT) {
//                TimeCallBack timeCallBack=(TimeCallBack) msg.obj;
                Log.i("zjq", "currentPosition:" + currentPosition);
                String temp = judge(currentPosition);
                if(temp.equals(OVER_SIGN)){
                    handler.removeCallbacks(null);
                }
                else{
                    if (!temp.equals("")) {
                        pause();
                        timeCallBack.process_stop(temp);
//                    current_judge_position++;
                    }
                    handler.sendEmptyMessageDelayed(DETECT, 1);//100毫秒重新执行一次
                }




            }
            else if(msg.what==ENDDETECT){
//                handler.sendEmptyMessageDelayed(WAITING_PLAYEND,1);
                timeCallBack.process_stop(OVER_SIGN);
            }
//            else if(msg.what==WAITING_PLAYEND){
//                if(Math.abs(currentPosition - totalduration) <= miss){
//                    timeCallBack.process_stop(QianYanPlayer.OVER_SIGN);
//
//                }else {
//                    handler.sendEmptyMessageDelayed(WAITING_PLAYEND,1);
//                }
//            }


        }
    };

    public void setScore(String sentence, Float score) {
        current_scence_scores.put(sentence, score);
    }

    public float getAverage() {
        //第二种(性能比第一种好，一次取值)
        Iterator map1it = current_scence_scores.entrySet().iterator();
        float total = 0;
        int count = 0;
        float res = 0;
        while (map1it.hasNext()) {
            Map.Entry<String, Float> entry = (Map.Entry<String, Float>) map1it.next();

            total += entry.getValue();
            count++;
        }
        res = (float) (total / count);
        return res;

    }

    public float getMaxScore() {
        Iterator map1it = current_scence_scores.entrySet().iterator();
        float max = 0;
        while (map1it.hasNext()) {
            Map.Entry<String, Float> entry = (Map.Entry<String, Float>) map1it.next();
            if (max <= entry.getValue()) {
                max = entry.getValue();
            }
        }

        return max;
    }

    public String getMaxSentence() {
        Iterator map1it = current_scence_scores.entrySet().iterator();
        float max = 0;
        String sentence = "";
        for (Map.Entry<String, Float> mapEntry : current_scence_scores.entrySet()) {
            if (mapEntry.getValue().equals(getMaxScore())) {
                sentence = mapEntry.getKey();
            }
        }

        return sentence;
    }

    private ArrayList<Stop_points> stop_points = new ArrayList<>();


    public QianYanPlayer(Context context, Scence s, StandardGSYVideoPlayer gsyplayer) {
        gsyPlayer = gsyplayer;
        this.context = context;
        current_judge_position = 0;
        scence = s;
        stop_points = scence.getStop_points();
        orientationUtils = new OrientationUtils((Activity) context, gsyPlayer);
        split();
    }


    public void getCurrent_voice_result(String res) {
        current_voice_result = res;
    }

    public void play(TimeCallBack callBack) {
//        total=gsyPlayer.getDuration();
        timeCallBack = callBack;
        Message message = new Message();
        message.obj = callBack;
        message.what = DETECT;
        handler.sendMessageDelayed(message, 500);
        gsyPlayer.startPlayLogic();


    }

    public void pause() {
        gsyPlayer.onVideoPause();
    }

    public float getSimilarityRatio(String str, String target) {
        float res = CalculateTool.getSimilarityRatio(str, target);
        return res;
    }


    public void release() {
        gsyPlayer.release();
    }

    public void init() {
        initGsyPlayer();
    }


    public void initGsyPlayer() {


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
                    public void onAutoComplete(String url, Object... objects) {
//                        super.onAutoComplete(url, objects);
                        Intent intent=getDATAIntent();
                        context.startActivity(intent);
                        Activity activity=(Activity)context;
                        activity.finish();
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

    public void startListening(TimeCallBack callBack) {


    }


    public void split() {

        for (Stop_points sp : stop_points) {
            start_point_list.add(sp.getStart_time());
            stop_point_list.add(sp.getEnd_time());
            sentences.add(sp.getSentence());
            isPauses.add(false);
        }
    }

    public String judge(int current) {
        //判断是否是暂停点,如果是,返回该暂停点对应的句子,否则返回空句子
        int judge_time;
        String res = "";
        if (current_judge_position >= stop_points.size()) {
//            handler.sendEmptyMessage(ENDDETECT);
            isPauses.clear();
            return QianYanPlayer.OVER_SIGN;
        } else if (current_judge_position < stop_points.size()) {
            judge_time = start_point_list.get(current_judge_position);
            if ((!isPauses.get(current_judge_position)) && (Math.abs(judge_time - current) <= miss) || (judge_time < current)) {
                res = stop_points.get(current_judge_position).getSentence();
                isPauses.set(current_judge_position, true);
                Log.i("test_judge", "已有输出值,结果为" + res + ",current:" + current + ",start_point_list.get(current_judge_position)" + start_point_list.get(current_judge_position));
//                if(!(current_judge_position==(stop_points.size()-1))){
                    current_judge_position++;
//                }
            }

        }
        return res;


    }


    public interface TimeCallBack {
        public void process_stop(String sentence);
    }



    public Intent getDATAIntent(){
        Intent intent=new Intent(context, End_dialogueActivity.class);
        intent.putExtra("max_score",getMaxScore());
        intent.putExtra("max_sentence",getMaxSentence());
        intent.putExtra("average",getAverage());
        intent.putExtra("name",scence.getMedia_name());
        return intent;
    }



}
