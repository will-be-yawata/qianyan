package com.example.administrator.langues.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.langues.R;
import com.example.administrator.langues.fragment.TabFragment;

//import com.hyphenate.chat.EMClient;

import com.hyphenate.EMMessageListener;


import java.util.ArrayList;
import java.util.List;

import entry.User;

//import util.EMHelp;

import util.core.ChatOperation;
import util.core.FriendOperation;
import util.core.LoginOperation;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private List<Fragment> fragments;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*EMHelp emHelp=new EMHelp();
        emHelp.init(this);
        emHelp.autologin(EMClient.getInstance().getCurrentUser(), new EMHelp.AutoLoginCallback() {
            @Override
            public void onSuccess() {
                initActi();
            }

            @Override
            public void onError() {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    public void initActi(){*/

        (new LoginOperation()).autoLogin(MainActivity.this);
        if(User.getInstance().getPhone()==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        FriendOperation.getInstance().setContactListener(new FriendOperation.ContactVallback() {
            public void onBeInvited() {
                //收到好友邀请
            }
            public void onBeAccepted() {
                //好友请求被同意
            }
            public void onBeDeclined() {
                //好友请求被拒绝
            }
            public void onBeAdded() {
                //增加了联系人时回调此方法
            }
            public void onBeDeleted() {
                //被删除时回调
            }
        });
        fragments= new ArrayList<>();
        fm=super.getSupportFragmentManager();
        initFragments();
    }
    private void initFragments(){
        TabFragment tabFragment=new TabFragment();
        fragments.add(tabFragment);
        setFragments(0);
    }
    private void setFragments(int position){
        fm.beginTransaction().replace(R.id.content_frame,
                fragments.get(position),"t"+position).commit();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }
}
