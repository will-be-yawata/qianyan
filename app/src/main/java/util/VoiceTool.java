package util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.langues.activity.TestActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.zyq.easypermission.EasyPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import util.core.CalculateTool;
import util.core.LanguageTool;

/**
 * 语音工具类
 */
public class VoiceTool {
    private SpeechRecognizer mIat;
    private GetVoiceCallback callback;
    private static String TAG = VoiceTool.class.getSimpleName();
    private RecognizerDialog mIatDialog;
    private String language="zh_cn";
    private String resultType = "json";
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private Toast mToast;
    private VoiceTool singleton=null;
    private RecognizerResult results;
    private Context context;
    int ret = 0;

    public VoiceTool(Context context){
        this.context=context;
        mToast = Toast.makeText(this.context, "", Toast.LENGTH_SHORT);
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
    }

    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
//                Toast.makeText(,"初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案",Toast.LENGTH_LONG).show();
            }
            else{
//                Toast.makeText(getApplicationContext(),"初始化成功",Toast.LENGTH_LONG).show();
            }
        }


    };

    private RecognizerListener mRecognizerListener=new RecognizerListener(){
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            callback.onVol(i,bytes);
        }

        @Override
        public void onBeginOfSpeech() {
            callback.onStart();
        }

        @Override
        public void onEndOfSpeech() {
            callback.onEnd();
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

            callback.onResult(getVoiceResult(recognizerResult));
        }

        @Override
        public void onError(SpeechError speechError) {
            callback.onError("error"+speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    /**
     *
     * @param activity
     * @param language  选择语言,请使用LanguageTool内的静态字段
     * @param callback  需要实现GetVoiceCallback
     */
    public void getVoice(Activity activity,String language, GetVoiceCallback callback){
        this.callback=callback;
        try {
            Map params= LanguageTool.getParmers(language);


            Iterator map1it=params.entrySet().iterator();
            while(map1it.hasNext())
            {
                Map.Entry<String, String> entry=(Map.Entry<String, String>) map1it.next();
//                System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
                mIat.setParameter(entry.getKey(),entry.getValue());
            }

            EasyPermission.build().requestPermission(activity,Manifest.permission.RECORD_AUDIO);
            ret = mIat.startListening(mRecognizerListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stopVoice(){
        mIat.stopListening();
    }


    /**
     *
     * @return 返回识别用户语音之后的结果
     */
    public String getVoiceResult(RecognizerResult results){
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        return resultBuffer.toString();
    }


    /**
     *
     * @param str  需要比较的字符串
     * @param target 需要比较的字符串
     * @return 相似度
     */
    public float getSimilarityRatio(String str, String target){
        float res=CalculateTool.getSimilarityRatio(str,target);
        return res;
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    public interface GetVoiceCallback{
        /**
         *用户结束说话时调用
         */
        void onEnd();

        /**
         * 用户开始说话时调用
         */
        void onStart();

        /**
         * 获得用户说话结果时调用
         */
        void onResult(String s);

        /**
         * 错误时调用
         */
        void onError(String err);

        /**
         * 用户说话时触发,音量触发
         */
        void onVol(int i, byte[] bytes);
    }

}