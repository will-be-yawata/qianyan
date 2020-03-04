package com.example.administrator.langues.activity.Matching;


import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.fragment.SeekFragment;


public class Situational_dialogueActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton sit_return;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_dialogue);
        init();
        listener();
        //加载指定的视频文件
        String path = Environment.getExternalStorageDirectory().getPath()+"/芒种.mp4";
        videoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);

        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);

        //让VideoView获取焦点
        videoView.requestFocus();



    }

    private void listener() {
        sit_return.setOnClickListener(this);
    }

    private void init() {
        sit_return=findViewById(R.id.sit_return);
        videoView=findViewById(R.id.videoview);
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
}

