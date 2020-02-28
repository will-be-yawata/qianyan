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
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import entry.User;
import util.EMHelp;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EMHelp emHelp=new EMHelp();
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

    public void initActi(){
        setContentView(R.layout.activity_main);
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
