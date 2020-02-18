package com.example.administrator.langues.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.langues.R;

import util.EMHelp;

public class RegisterActivity extends AppCompatActivity {

private EditText name;
private EditText pwd;
private ImageButton btn;
private EMHelp emHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        emHelp=new EMHelp();
        emHelp.init(this);
        emHelp.answerCall();
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/RegisterActivity.java
        btn.setOnClickListener(view -> {
            Log.i("mData",name.getText().toString());
            Log.i("mData",pwd.getText().toString());
            register();
=======
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("mData",name.getText().toString());
                Log.i("mData",pwd.getText().toString());
                String userName=name.getText().toString();
                String userPwd=pwd.getText().toString();
                try {
                    register();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"注册失败",Toast.LENGTH_SHORT).show();
                }



            }
>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/RegisterActivity.java
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
<<<<<<< HEAD:app/src/main/java/com/example/administrator/langues/activity/RegisterActivity.java
            emHelp.registered(userName, userPwd, isRegister -> {
                if(isRegister){
                    runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show());
                }else{
                    runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show());
=======

            emHelp.registered(userName, userPwd, new EMHelp.RegisterCallback() {
                @Override
                public void isRegister(boolean isRegister) {
                    if(isRegister){
                        runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show());
                    }else{
                        runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show());
                    }
>>>>>>> cwk:app/src/main/java/com/example/administrator/langues/RegisterActivity.java
                }
            });
//            if(emHelp.registered(userName,"123",userPwd)){
//                runOnUiThread(()->Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_LONG));
//            }else{
//                runOnUiThread(()->Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_LONG));
//            }

//            emHelp.registered()
//            RequestParams params=new RequestParams(ROOT+REGISTER);
//            params.setMultipart(false);
//            params.addBodyParameter("name",userName);
//            params.addBodyParameter("pwd",userPwd);
//            x.http().post(params, new Callback.CommonCallback<String>(){
//                @Override
//                public void onSuccess(String result) {
//                    Map<String,Object> data=JSON.parseObject(result,new TypeReference<HashMap<String,Object>>(){});
//                    if((boolean)data.get("success")){
//
//                        runOnUiThread(()-> Toast.makeText(x.app(),data.get("message").toString(),Toast.LENGTH_LONG).show());
//                    }else{
//                        runOnUiThread(()->Toast.makeText(x.app(),data.get("message").toString(),Toast.LENGTH_LONG).show());
//                    }
//                }
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Toast.makeText(x.app(),"网络错误\n"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                }
//                @Override
//                public void onCancelled(CancelledException cex) {
//                    Toast.makeText(x.app(),"操作被取消",Toast.LENGTH_LONG).show();
//                }
//                @Override
//                public void onFinished() {}
//            });
    }
}
