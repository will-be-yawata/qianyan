package com.example.administrator.langues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import util.EMHelp;

public class RegisterActivity extends AppCompatActivity {

private EditText name;
private EditText pwd;
private ImageButton btn;
private EMHelp emHelp;

    public static String ROOT="http://172.16.245.206:88/qianyan/public/index/";
    //    public static String ROOT="http://119.23.216.103/qianyan/public/index/";
//    public static String ROOT="http://192.168.1.5:88/qianyan/public/index/";
    public static String REGISTER="register/register.html";
    public static String LOGIN="login/login.html";
    public static String USER_IMG="http://172.16.245.206:88/qianyan/public/static/images/";
    public static String SETTING="user/setting.html";
    public static String CREATE_ROOM="room/create.html";
    //    public static String PUBLIC_ROOM="room/publicroom.html";
    public static String INFO_ROOM="room/info.html";
    public static String GET_USER="room/getuser.html";
    public static String DELETE_ROOM="room/deleteroom.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        emHelp=new EMHelp();
        emHelp.init(this);
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
        });

    }

    private void initView() {
        name=findViewById(R.id.editText2);
        pwd=findViewById(R.id.editText3);
        btn=findViewById(R.id.imageButton25);
    }
    private void register() {
            String userName=name.getText().toString();
            String userPwd=pwd.getText().toString();

            emHelp.registered(userName, userPwd, new EMHelp.RegisterCallback() {
                @Override
                public void isRegister(boolean isRegister) {
                    if(isRegister){
                        runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show());
                    }else{
                        runOnUiThread(()->Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show());
                    }
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
