package com.example.administrator.langues.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.hyphenate.chat.EMClient;


import com.example.administrator.langues.R;

<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/LoginActivity.java
=======
import entry.Dynamic;
import entry.Friend;
import entry.User;

>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/LoginActivity.java
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
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/LoginActivity.java
=======
        if(EMClient.getInstance().getCurrentUser()==""){
            Toast.makeText(getBaseContext(),"请先登录",Toast.LENGTH_SHORT).show();
        }else{
            EMClient.getInstance().logout(true);
        }
//        Log.i("test",EMClient.getInstance().getCurrentUser()+"|"+EMClient.getInstance().getCurrentUser());

>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/LoginActivity.java
        emHelp=new EMHelp();
        emHelp.init(this);

//        emHelp.login("15728283805","1");
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/LoginActivity.java
        login_button.setOnClickListener(view -> {
            String phone=usertext.getText().toString();
            String pwdtextcon=pwdtext.getText().toString();
            user_login(phone,pwdtextcon);
=======

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=usertext.getText().toString();
                String pwdtextcon=pwdtext.getText().toString();
                user_login(phone,pwdtextcon);

            }
>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/LoginActivity.java
        });
        resign_text.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        });

    }
    public void initViews(){
        login_button= findViewById(R.id.btn_login);
//        Log.i("ttg",login_button.getText().toString());
        usertext= findViewById(R.id.editText4);
        pwdtext= findViewById(R.id.editText5);
        resign_text= findViewById(R.id.textView33);
    }
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/LoginActivity.java
=======


>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/LoginActivity.java
    public void user_login(String phone,String pwd){

            emHelp.login(phone, pwd, (isLogin, message) -> {
                if(isLogin){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                }
                else{
                    Toast.makeText(getBaseContext(),"登录失败,请检查用户名和密码",Toast.LENGTH_SHORT).show();
                }
            });


    }

}
