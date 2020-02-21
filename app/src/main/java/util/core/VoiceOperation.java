package util.core;

import android.content.Context;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;

import util.VoiceTool;

public class VoiceOperation {
    private SpeechRecognizer mIat;
    private static String TAG = VoiceTool.class.getSimpleName();
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private Toast mToast;
    private VoiceTool singleton=null;
    private RecognizerResult results;
    private Context context;
    int ret = 0;
}
