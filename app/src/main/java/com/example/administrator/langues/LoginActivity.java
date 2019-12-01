package com.example.administrator.langues;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import util.EMHelp;

public class LoginActivity extends AppCompatActivity {
    private Button login_button;
    private EditText usertext,pwdtext;
    private TextView resign;
    private EMHelp emHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        emHelp=new EMHelp();
        emHelp.init(this);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=usertext.getText().toString();
                String pwdtextcon=pwdtext.getText().toString();
                user_login(phone,pwdtextcon);
            }
        });

    }

    public void initViews(){
        login_button=(Button)findViewById(R.id.btn_login);
        Log.i("ttg",login_button.getText().toString());
        usertext=(EditText) findViewById(R.id.editText4);
        pwdtext=(EditText)findViewById(R.id.editText5);
        resign=(TextView)findViewById(R.id.textView33);
    }

    public void user_login(String phone,String pwd){

        emHelp.login(phone,pwd);
    }
}
