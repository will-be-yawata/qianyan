package com.example.administrator.langues.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import util.JsonParser;
import util.VoiceTool;
import util.core.CalculateTool;
import util.core.LanguageTool;

public class TestActivity extends AppCompatActivity {
    private EditText answer;
    private Button test_voice_btn;
    private Button stop_voice_btn;

    private static String TAG = TestActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private EditText mResultText;
    private EditText showContacts;
    private TextView languageText;
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String[] languageEntries ;
    private String[] languageValues;
    private String language="zh_cn";
    private VoiceTool voiceTool;
    private int selectedNum=0;
    private String resultType = "json";
    private boolean cyclic = false;//音频流识别是否循环调用

    private StringBuffer buffer = new StringBuffer();

    Handler han = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x001) {
//                executeStream();
            }
        }
    };


    private static int flg=0;

    int ret = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        answer=(EditText)findViewById(R.id.standard_answer);
        test_voice_btn=(Button)findViewById(R.id.test_voice);
        stop_voice_btn=(Button)findViewById(R.id.stop_voice);
        voiceTool=new VoiceTool(TestActivity.this);
        setListener();
    }
    public void setListener(){
        stop_voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceTool.stopVoice();
            }
        });

        test_voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    voiceTool.getVoice(TestActivity.this, LanguageTool.RIYU, new VoiceTool.GetVoiceCallback() {
                        @Override
                        public void onEnd() {
                            showTip("结束" );
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onResult(String s) {
                            Log.i("YUYINTEST","识别结果为:"+s);
                            Log.i("YUYINTEST","你这句话的得分为"+CalculateTool.getSimilarityRatio(s,"ありがとうございます。")+"");
                        }

                        @Override
                        public void onError(String err) {
                            Log.i("YUYINTEST",err);
                        }

                        @Override
                        public void onVol(int i, byte[] bytes) {
                            showTip("当前正在说话，音量大小：" + i);
                            Log.d(TAG, "返回音频数据："+bytes.length);
                        }
                    });
            }
        });
    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }


}
