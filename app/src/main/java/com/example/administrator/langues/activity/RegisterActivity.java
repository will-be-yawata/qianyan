package com.example.administrator.langues.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.langues.R;

import util.core.ChatOperation;
import util.core.LoginOperation;


public class RegisterActivity extends AppCompatActivity {

private EditText name;
private EditText pwd;
private Button btn;
private ChatOperation chatOperation;
private LoginOperation loginOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        chatOperation=new ChatOperation();
        chatOperation.answerCall();
        btn.setOnClickListener(view -> {
            Log.i("mData",name.getText().toString());
            Log.i("mData",pwd.getText().toString());
            register();
        });
    }
    private void initView() {
        name=findViewById(R.id.rg_tellphone);
        pwd=findViewById(R.id.tg_password);
        btn=findViewById(R.id.rg_register_btn);
    }
    private void register() {
            String userName=name.getText().toString();
            String userPwd=pwd.getText().toString();
            loginOperation.registered(userName, userPwd, isRegister -> {
                if(isRegister){
                    runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show());
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else{
                    runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show());
                }
            });
    }
}
