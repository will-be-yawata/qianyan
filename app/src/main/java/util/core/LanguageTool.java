package util.core;

import android.os.Environment;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;

import java.util.ArrayList;
import java.util.HashMap;

public class LanguageTool {
    public final static String PUTONGHUA="zh_cn";
    public final static String ENGLISH="en_us";
    public final static String RIYU="ja_jp";
    public final static String HANYU="ko_kr";
    public final static String EYU="ru-ru";
    public final static String FAYU="fr_fr";
    public final static String XIBANYAYU="es_es";
    public final static String YUEYU="cantonese";
    private static HashMap parms;


    /**
     *
     * @param language  选择语言
     * @param mEngineType  识别引擎
     * @param return_type  返回结果类型
     * @param VAD_BOS  设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
     * @param VAD_EOS  设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
     * @param is_biaodian  设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
     * @param audio_type  保存音频格式支持pcm、wav
     * @param audio_path  设置音频保存路径
     * @return
     */
    public static HashMap getParmers(String language, String mEngineType, String return_type, String VAD_BOS, String VAD_EOS, String is_biaodian, String audio_type, String audio_path) throws Exception{
        parms=new HashMap();
        parms.put(SpeechConstant.ENGINE_TYPE,mEngineType);
        parms.put(SpeechConstant.RESULT_TYPE,return_type);
        parms.put(SpeechConstant.VAD_BOS,VAD_BOS);
        parms.put(SpeechConstant.VAD_EOS,VAD_EOS);
        parms.put(SpeechConstant.ASR_PTT,is_biaodian);
        parms.put(SpeechConstant.AUDIO_FORMAT,audio_type);
        parms.put(SpeechConstant.ASR_AUDIO_PATH,audio_path);
        switch (language){
            case "cantonese":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.PUTONGHUA);
                parms.put(SpeechConstant.ACCENT,LanguageTool.YUEYU);
                break;
            case "zh_cn":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.PUTONGHUA);
                break;
            case "ja_jp":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.RIYU);
                break;
            case "ko_kr":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.HANYU);
                break;
            case "ru-ru":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.EYU);
                break;
            case "fr_fr":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.FAYU);
                break;
            case "es_es":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.XIBANYAYU);
                break;
            case "en_us":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.ENGLISH);
                break;
                default:
                    Exception err=new Exception("请使用LanguageTool类的语言字符串");
                    throw err;
        }
        return parms;
    }

    public static HashMap getParmers(String language) throws Exception{
        parms=new HashMap();
        parms.put(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        parms.put(SpeechConstant.RESULT_TYPE,"json");
        parms.put(SpeechConstant.VAD_BOS,"4000");
        parms.put(SpeechConstant.VAD_EOS,"3000");
        parms.put(SpeechConstant.ASR_PTT,"0");
        parms.put(SpeechConstant.AUDIO_FORMAT,"wav");
        parms.put(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
        switch (language){
            case "cantonese":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.PUTONGHUA);
                parms.put(SpeechConstant.ACCENT,LanguageTool.YUEYU);
                break;
            case "zh_cn":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.PUTONGHUA);
                break;
            case "ja_jp":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.RIYU);
                break;
            case "ko_kr":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.HANYU);
                break;
            case "ru-ru":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.EYU);
                break;
            case "fr_fr":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.FAYU);
                break;
            case "es_es":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.XIBANYAYU);
                break;
            case "en_us":
                parms.put(SpeechConstant.LANGUAGE,LanguageTool.ENGLISH);
                break;
            default:
                Exception err=new Exception("请使用LanguageTool类的语言字符串");
                throw err;
        }
        return parms;
    }
}
