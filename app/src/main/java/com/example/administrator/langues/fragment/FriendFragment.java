package com.example.administrator.langues.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Chat.Friends_chatActivity;
import com.example.administrator.langues.adapter.FriendAdapter;
import com.example.administrator.langues.view.FriendListView;

import util.core.ChatOperation;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {
    public final int REQUESTCODE_CHAT=0;
    private FriendListView friendListview;
    private FriendAdapter friendAdapter;
    private int messageCount=0;
    private ChatOperation chatOperation=new ChatOperation();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_friend, container, false);
        initView(view);
        listener();
        return view;
    }
    private void listener() {
        friendListview.setOnItemClickListener((adapterView, viewHolder, position, positionLine) -> {
            Intent intent=new Intent(FriendFragment.this.getContext(),Friends_chatActivity.class);
            intent.putExtra("photo",friendAdapter.getData(position).getImg());
            intent.putExtra("name",friendAdapter.getData(position).getName());
            intent.putExtra("phone",friendAdapter.getData(position).getPhone());
            startActivityForResult(intent,REQUESTCODE_CHAT);
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUESTCODE_CHAT:
                if(resultCode== Activity.RESULT_OK){
                    String phone=data.getStringExtra("phone");
                    friendAdapter.resetBubbles(phone);
                }
                break;
        }
    }

    private void initView(View view){
        friendListview= view.findViewById(R.id.friend_listview);
        friendAdapter=new FriendAdapter(getContext());
        friendListview.setAdapter(friendAdapter);
    }
    public void messageCountChange(String phone,int num){
        friendAdapter.setBubbles(phone,num);
    }
}
