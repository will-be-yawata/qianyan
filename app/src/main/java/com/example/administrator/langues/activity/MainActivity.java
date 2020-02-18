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

import java.util.ArrayList;
import java.util.List;

import entry.User;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(User.getInstance().getPhone()==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        fragments= new ArrayList<>();
        fm=super.getSupportFragmentManager();
        initFragments();
    }
    private void initFragments(){
        TabFragment tabFragment=new TabFragment();
        fragments.add(tabFragment);
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/MainActivity.java
=======


        /*TrendsFragment trendsFragment=new TrendsFragment();
        fragments.add(trendsFragment);
        FindFragment findFragment=new FindFragment();
        fragments.add(findFragment);
        PersonalFragment personalFragment=new PersonalFragment();
        fragments.add(personalFragment);*/

>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/MainActivity.java
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
