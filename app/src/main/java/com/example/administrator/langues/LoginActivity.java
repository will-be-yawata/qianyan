package com.example.administrator.langues;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import util.EMHelp;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText usertext,pwdtext;
    private TextView resign;
    private EMHelp emHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        emHelp=new EMHelp();
        setContentView(R.layout.activity_login);
    }

    public void initViews(){
        btn_login=(Button)findViewById(R.id.btn_login);
        usertext=(EditText) findViewById(R.id.editText4);
        pwdtext=(EditText)findViewById(R.id.editText5);
        resign=(TextView)findViewById(R.id.textView33);
    }

    public void user_login(String phone,String pwd){
        emHelp.login(phone,pwd);
    }
}
