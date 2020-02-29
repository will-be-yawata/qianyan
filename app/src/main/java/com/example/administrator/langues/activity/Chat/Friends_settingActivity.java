package com.example.administrator.langues.activity.Chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.administrator.langues.R;

public class Friends_settingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton top_chat;
    private ImageButton not_disturb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_setting);
        initView();
        clickListener();
        
    }

    private void clickListener() {
        top_chat.setOnClickListener(this);
        not_disturb.setOnClickListener(this);
    }

    private void initView() {
        top_chat=findViewById(R.id.top_chat);
        not_disturb=findViewById(R.id.not_disturb);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_chat:
                if (top_chat.getResources().gegetResources().getDrawable(R.mipmap.close))
                top_chat.setImageResource(R.mipmap.open);
                break;
            case R.id.not_disturb:
                not_disturb.setImageResource(R.mipmap.open);
                break;
        }
        
    }
}
