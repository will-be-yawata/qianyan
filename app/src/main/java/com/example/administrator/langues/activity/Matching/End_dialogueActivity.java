package com.example.administrator.langues.activity.Matching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.langues.R;

public class End_dialogueActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView score;//最终得分
    private TextView good_sentence;//最佳金句
    private TextView media_name;//播放时间
    private TextView highest_score;//最高得分
    private ImageButton dialogue_close;//关闭


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_dialogue);
        //初始化
        init();
        initData();
        onClickListen();

    }

    public void initData(){
        Intent intent=getIntent();
        String max_score=intent.getFloatExtra("max_score",0)+"";
        String max_sentence= intent.getStringExtra("max_sentence");
        String average= intent.getFloatExtra("average",0)+"";
        String name= intent.getStringExtra("name");

        score.setText(average);
        good_sentence.setText(max_sentence);
        media_name.setText(name);
        highest_score.setText(max_score);
    }

    private void onClickListen() {
        dialogue_close.setOnClickListener(this);
    }

    private void init() {
        score = findViewById(R.id.score);
        good_sentence = findViewById(R.id.good_sentence);
        media_name = findViewById(R.id.media_name);
        highest_score = findViewById(R.id.highest_score);
        dialogue_close = findViewById(R.id.dialogue_close);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialogue_close:
                finish();
                break;
        }
    }
}
