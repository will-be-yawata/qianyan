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


import java.util.ArrayList;

import entry.Dynamic;
import entry.Friend;
import entry.User;

import util.EMHelp;
import util.core.DynamicOperation;

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

//        emHelp.login("15728283805","1");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=usertext.getText().toString();
                String pwdtextcon=pwdtext.getText().toString();
                user_login(phone,pwdtextcon);

            }
        });
        resign_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        // 下面是测试代码，可删除
//        User.getInstance().setPhone("15728283805");
//        (new DynamicOperation()).getDynamic(10, 0, res -> {
//            for (int i = 0; i < res.size(); i++) {
//                Log.i("mData",res.get(i).toString());
//            }
//        });
        //以下还是测试代码，可删除
//        User.getInstance().setPhone("15728283805");
//        User.getInstance().getFriends(f -> {
//            for (int i = 0; i < f.size(); i++) {
//                Log.i("mData",f.get(i).toString());
//            }
//        });
    }

    public void initViews(){
        login_button=(Button)findViewById(R.id.btn_login);
//        Log.i("ttg",login_button.getText().toString());
        usertext=(EditText) findViewById(R.id.editText4);
        pwdtext=(EditText)findViewById(R.id.editText5);
        resign_text=(TextView)findViewById(R.id.textView33);
    }


    public void user_login(String phone,String pwd){

        emHelp.login(phone, pwd, (isLogin, message) -> {
            if(isLogin){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


//         emHelp.login(phone, pwd, new EMHelp.IsLoginCallback() {
//             @Override
//             public void isLogin(boolean isLogin, String message) {
//                 if(isLogin){
//                     startActivity(new Intent(getBaseContext(),MainActivity.class));
// //                    Toast.makeText(getBaseContext(),"登录成功",Toast.LENGTH_SHORT).show();
//                     runOnUiThread(()->Toast.makeText(getBaseContext(),"登录成功",Toast.LENGTH_SHORT).show());
//                     finish();
//                 }else{
//                    runOnUiThread(()->Toast.makeText(getBaseContext(),"登录失败,请检查用户名或者密码",Toast.LENGTH_SHORT).show());
//                 }

            }
        });
    }

}
