package com.example.administrator.langues.activity.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Chat.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class Friends_chatActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private ChatAdapter adapter;
    private ImageButton voice_btn;
    private ImageButton keyboard_btn;
    private ImageButton friend_chat_return;

    private Button voice_say;

    private List<chat> chatList = new ArrayList<chat>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_chat);
        initView();
        initMsgs();
        adapter = new ChatAdapter(Friends_chatActivity.this, R.layout.friends_chat_items, chatList);

        msgListView.setAdapter(adapter);


        clickListener();

    }

    private void clickListener() {
        send.setOnClickListener(this);
        voice_btn.setOnClickListener(this);
        keyboard_btn.setOnClickListener(this);
        voice_say.setOnClickListener(this);
        friend_chat_return.setOnClickListener(this);
    }

    private void initView() {
        voice_btn=findViewById(R.id.voice_btn);
        keyboard_btn=findViewById(R.id.keyboard_btn);
        voice_say=findViewById(R.id.voice_say);
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.chat_send);
        msgListView = (ListView)findViewById(R.id.chat_listview);
        friend_chat_return=findViewById(R.id.friend_chat_return);
    }

    private void initMsgs() {
        chat chat1 = new chat("Hello, how are you?", chat.TYPE_RECEIVED);
        chatList.add(chat1);
        chat chat2 = new chat("Fine, thank you, and you?", chat.TYPE_SEND);
        chatList.add(chat2);
        chat chat3 = new chat("I am fine, too!", chat.TYPE_RECEIVED);
        chatList.add(chat3);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_send:
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
        }

    }
}
