package com.example.administrator.langues.activity.Chat;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.adapter.ChatAdapter;
import com.example.administrator.langues.view.AudioRecorderButton;
import com.example.administrator.langues.view.ChatRecyclerView;
import com.hyphenate.EMMessageListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entry.Message;
import entry.User;
import util.core.ChatOperation;

public class Friends_chatActivity extends AppCompatActivity {
    private ChatRecyclerView msgRecyclerView;
    private EditText inputText;
    private Button send;
    private ChatAdapter chatAdapter;
    private ImageButton voice_btn;
    private ImageButton keyboard_btn;
    private ImageButton friend_chat_return;

//    private ImageButton friend_setting_btn;

    private TextView friend_chat_name;
    private LinearLayoutManager linearLayoutManager;
    private AudioRecorderButton voice_say;

    ChatOperation chatOperation= new ChatOperation();
    EMMessageListener onMessageListenerCall=null;


    private String friend_photo;
    private String friend_name;
    private String friend_phone;

    private int limit=10;
    private int offset=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_chat);
        Intent intent=getIntent();
        friend_photo=intent.getStringExtra("photo");
        friend_name=intent.getStringExtra("name");
        friend_phone=intent.getStringExtra("phone");
        if(friend_phone==null || friend_phone.equals("")) finish();


        initView();
        listener();
        getMsgs();

    }

    private void initView() {
        friend_chat_name=findViewById(R.id.friend_chat_name);
        voice_btn=findViewById(R.id.voice_btn);
        keyboard_btn=findViewById(R.id.keyboard_btn);
        voice_say=findViewById(R.id.voice_say);
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.chat_send);
        msgRecyclerView = findViewById(R.id.chat_recyclerview);
        friend_chat_return=findViewById(R.id.friend_chat_return);


        friend_chat_name.setText((friend_name==null || friend_name.equals(""))?friend_phone:friend_name);

        linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        chatAdapter=new ChatAdapter(this,friend_photo);
        msgRecyclerView.setAdapter(chatAdapter);
    }
    private void getMsgs(){
        chatOperation.getMessage(User.getInstance().getPhone(), friend_phone, limit, offset, new ChatOperation.GetMessageCallback() {
            public void onSuccess(ArrayList<Message> data) {
                offset+=limit;
                chatAdapter.addData(data,false);
                moveToPosition(offset==limit?data.size()-1:data.size());
                msgRecyclerView.refreshComplete();
            }
            public void onError(String msg) {
                Toast.makeText(Friends_chatActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void listener(){
        //返回
        friend_chat_return.setOnClickListener(view-> {
            Intent i=new Intent();
            i.putExtra("phone",friend_phone);
            setResult(Activity.RESULT_OK,i);
            finish();
        });
        //转为键盘
        keyboard_btn.setOnClickListener(view -> {
            keyboard_btn.setVisibility(View.INVISIBLE);
            voice_btn.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            voice_say.setVisibility(View.INVISIBLE);
            inputText.setVisibility(View.VISIBLE);
        });
        //转为语音
        voice_btn.setOnClickListener(view -> {
            voice_btn.setVisibility(View.INVISIBLE);
            keyboard_btn.setVisibility(View.VISIBLE);
            send.setVisibility(View.INVISIBLE);
            voice_say.setVisibility(View.VISIBLE);
            inputText.setVisibility(View.INVISIBLE);
            //关闭系统软键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View v = getWindow().peekDecorView();
            if (null != v) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        //录音按钮
        voice_say.setAudioFinishRecorderListener((seconds,filePath)->{
            //录音结束
            Message msg=new Message(User.getInstance().getPhone(),Message.TYPE_VOICE);
            msg.setContent(filePath);
            msg.setTo(friend_phone);
            msg.setLength(seconds);
            msg.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            chatAdapter.addOneData(msg);
            moveToPosition(chatAdapter.getItemCount()-1);
            chatOperation.sendMessage(msg);
        });
        //发送
        send.setOnClickListener(view->{
            String content = inputText.getText().toString();
            if(!"".equals(content)) {
                Message msg=new Message(User.getInstance().getPhone(),Message.TYPE_TEXT);
                msg.setContent(content);
                msg.setTo(friend_phone);
                msg.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                chatAdapter.addOneData(msg);
                moveToPosition(chatAdapter.getItemCount()-1);
                inputText.setText("");
                chatOperation.sendMessage(msg);
            }else{
                Toast.makeText(this,"你倒是说话啊",Toast.LENGTH_SHORT).show();
            }
        });
        msgRecyclerView.setOnScrollListener(new ChatRecyclerView.OnScrollListener() {
            public boolean onTop() {
                return linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            }
            public void doRefresh() {
                getMsgs();
            }
        });

    }
    private void moveToPosition(int n) {
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            msgRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = msgRecyclerView.getChildAt(n - firstItem).getTop();
            msgRecyclerView.scrollBy(0, top);
        } else {
            msgRecyclerView.scrollToPosition(n);
        }

    }


    /*@Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_send://发送文字
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    chat msg = new chat(content, chat.TYPE_SEND);
                    chatList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(chatList.size());
                    inputText.setText("");
                }
                break;
            case R.id.voice_btn://转为语音
                voice_btn.setVisibility(View.GONE);
                keyboard_btn.setVisibility(View.VISIBLE);
                voice_say.setVisibility(View.VISIBLE);
                inputText.setVisibility(View.GONE);
                //关闭系统软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View v = getWindow().peekDecorView();
                if (null != v) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }


                break;
            case R.id.keyboard_btn://转为键盘
                keyboard_btn.setVisibility(View.GONE);
                voice_btn.setVisibility(View.VISIBLE);
                voice_say.setVisibility(View.GONE);
                inputText.setVisibility(View.VISIBLE);
                break;
            case R.id.voice_say://语音

                break;
            case  R.id.friend_chat_return://返回
                finish();
                break;
            case  R.id.friend_setting_btn://好友设置
                //Toast.makeText(getBaseContext(),"123",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getBaseContext(),Friends_settingActivity.class);
                startActivity(intent);
                break;*/

    protected void onResume() {
        //消息接收
        if(onMessageListenerCall==null) {
            onMessageListenerCall = chatOperation.addOnMessageListenerCall(new ChatOperation.OnMessageListenerCall() {
                public void onReceived(ArrayList<Message> ms) {
                    runOnUiThread(() -> {
                        chatAdapter.addData(ms, true);
                        moveToPosition(chatAdapter.getItemCount() - 1);
                    });
                }
                public String getFriendPhone() {
                    return friend_phone;
                }
            });

        }
        super.onResume();
    }

    protected void onDestroy() {
        chatOperation.removeMessageListener(onMessageListenerCall);
        super.onDestroy();
    }
}
