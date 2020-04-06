package com.example.administrator.langues.activity.MyPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.LoginActivity;

import entry.User;
import util.EMHelp;

public class SettingActivity extends AppCompatActivity {
    ImageButton setting_return;
    RelativeLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        listener();
    }
    private void init(){
        logout=findViewById(R.id.logout);
        setting_return= findViewById(R.id.setting_return);
    }
    private void listener(){
        setting_return.setOnClickListener(v -> finish());
        logout.setOnClickListener(view -> logout());
    }
    private void logout(){
        AlertDialog.Builder dialog = new AlertDialog.Builder (this);
        dialog.setTitle ("注销").setMessage ("确定退出账号？");
        dialog.setPositiveButton ("确定", (dialog1, which) -> {
            (new EMHelp()).logout();
            User.getInstance().release();
            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
            SettingActivity.this.startActivity(intent);
            SettingActivity.this.finish ();
        });
        dialog.setNegativeButton ("取消",null);
        dialog.show ();
    }
}