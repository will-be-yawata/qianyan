package com.example.administrator.langues.activity.Chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.MyPage.User_data.Personal_cardActivity;

public class Friends_settingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton top_chat_close,top_chat_open;
    private ImageButton not_disturb_close,not_disturb_open;
    private ImageButton friend_setting_return;
    private Button delete_friend;
    private RelativeLayout personal_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_setting);
        initView();
        clickListener();
        
    }

    private void clickListener() {
        top_chat_open.setOnClickListener(this);
        top_chat_close.setOnClickListener(this);
        not_disturb_close.setOnClickListener(this);
        not_disturb_open.setOnClickListener(this);
        delete_friend.setOnClickListener(this);
        personal_card.setOnClickListener(this);
        friend_setting_return.setOnClickListener(this);
    }

    private void initView() {
        top_chat_close=findViewById(R.id.top_chat_close);
        top_chat_open=findViewById(R.id.top_chat_open);
        not_disturb_close=findViewById(R.id.not_disturb_close);
        not_disturb_open=findViewById(R.id.not_disturb_open);
        delete_friend=findViewById(R.id.delete_friend);
        personal_card=findViewById(R.id.personal_card);
        friend_setting_return=findViewById(R.id.friend_setting_return);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.friend_setting_return:
                finish();
                break;
            case R.id.personal_card:
                Intent intent=new Intent(getBaseContext(),Personal_cardActivity.class);
                startActivity(intent);
                break;
            case R.id.top_chat_close:
                top_chat_close.setVisibility(View.GONE);
                top_chat_open.setVisibility(View.VISIBLE);
                break;
            case R.id.top_chat_open:
                top_chat_close.setVisibility(View.VISIBLE);
                top_chat_open.setVisibility(View.GONE);
                break;
            case R.id.not_disturb_close:
                not_disturb_close.setVisibility(View.GONE);
                not_disturb_open.setVisibility(View.VISIBLE);
                break;
            case R.id.not_disturb_open:
                not_disturb_close.setVisibility(View.VISIBLE);
                not_disturb_open.setVisibility(View.GONE);
                break;
            case R.id.delete_friend:
                new Delete_friend_dialog(Friends_settingActivity.this){

                    @Override
                    public void deletefriend() {
                        Toast.makeText(getContext(),"你点击了删除该好友",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void btncancel() {
                        cancel();
                    }
                }.show();
                break;

        }
        
    }
}
