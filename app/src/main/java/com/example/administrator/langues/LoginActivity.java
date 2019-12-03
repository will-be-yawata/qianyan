package com.example.administrator.langues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hyphenate.chat.EMClient;

import util.EMHelp;

public class LoginActivity extends AppCompatActivity {
    private Button login_button;
    private EditText usertext,pwdtext;
    private TextView resign_text;
    private EMHelp emHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        if(EMClient.getInstance().getCurrentUser()==""){
            Toast.makeText(getBaseContext(),"请先登录",Toast.LENGTH_SHORT).show();
        }else{
            EMClient.getInstance().logout(true);
        }
//        Log.i("test",EMClient.getInstance().getCurrentUser()+"|"+EMClient.getInstance().getCurrentUser());

        emHelp=new EMHelp();
        emHelp.init(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=usertext.getText().toString();
                String pwdtextcon=pwdtext.getText().toString();
                emHelp.login(phone,pwdtextcon);

            }
        });
        resign_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }

    public void initViews(){
        login_button=(Button)findViewById(R.id.btn_login);
//        Log.i("ttg",login_button.getText().toString());
        usertext=(EditText) findViewById(R.id.editText4);
        pwdtext=(EditText)findViewById(R.id.editText5);
        resign_text=(TextView)findViewById(R.id.textView33);
    }

}
