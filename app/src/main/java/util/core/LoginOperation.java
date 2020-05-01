package util.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.langues.activity.LoginActivity;
import com.example.administrator.langues.activity.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import entry.User;
import util.Url;

public class LoginOperation {
    public void registered(String phone, String pwd, RegisterCallback callback){

        RequestParams params=new RequestParams(Url.ROOT+Url.REGISTER);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("pwd",pwd);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                HashMap res=JSON.parseObject(s,new TypeReference<HashMap<String,Object>>(){});
                if((boolean)res.get("success"))
                    callback.isRegister(true);
                else
                    callback.isRegister(false);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.isRegister(false);
            }

            @Override
            public void onCancelled(CancelledException e) {
                callback.isRegister(false);
            }

            @Override
            public void onFinished() {
            }
        });
    }
    public void login(String phone, String pwd, IsLoginCallback callback){
        EMClient.getInstance().login(phone, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                RequestParams params=new RequestParams(Url.ROOT+Url.LOGIN);
                params.addBodyParameter("phone",phone);
                params.addBodyParameter("pwd",pwd);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        HashMap<String,String> result;
                        result=JSON.parseObject(s,new TypeReference<HashMap<String,String>>(){});
                        User.getInstance().setPhone(result.get("phone"));
                        User.getInstance().setName(result.get("name"));
                        User.getInstance().setPwd(result.get("pwd"));
                        User.getInstance().setImg(result.get("img"));
                        User.getInstance().setSex(result.get("sex"));
                        User.getInstance().setDan(result.get("dan"));
                        callback.isLogin(true,"登录成功");
                        FriendOperation.getInstance().friendListener();
                        FriendStatus.getInstance().getFriendStatus();
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        callback.isLogin(false,throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {
                        callback.isLogin(false,e.getMessage());
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
            @Override
            public void onError(int code, String error) {
                callback.isLogin(false,error);
                User.getInstance().release();
            }
            @Override
            public void onProgress(int progress, String status) {
            }
        });
    }
    public void logout(){
        EMClient.getInstance().logout(true);
    }
    public void autoLogin(Activity activity){
        String phone=EMClient.getInstance().getCurrentUser();
        if(EMClient.getInstance().isConnected())
            if(EMClient.getInstance().isLoggedInBefore())
                if(phone!=null || !phone.equals(""))
                    if(User.getInstance().getPhone()==null || User.getInstance().getPhone().equals("")){
                        ProgressDialog progressDialog = new ProgressDialog(activity);
                        progressDialog.setTitle("自动登录");
                        progressDialog.setMessage("正在获得用户信息...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        RequestParams params=new RequestParams(Url.ROOT+Url.AUTOLOGIN);
                        params.addBodyParameter("phone",phone);
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            public void onSuccess(String s) {
                                Log.i("zjq","success:"+s);
                                HashMap<String,String> result;
                                result=JSON.parseObject(s,new TypeReference<HashMap<String,String>>(){});
                                User.getInstance().setPhone(result.get("phone"));
                                User.getInstance().setName(result.get("name"));
                                User.getInstance().setPwd(result.get("pwd"));
                                User.getInstance().setImg(result.get("img"));
                                User.getInstance().setSex(result.get("sex"));
                                User.getInstance().setDan(result.get("dan"));

                                FriendOperation.getInstance().friendListener();
                                Intent intent=new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                                Log.i("mData",User.getInstance().getPhone());

                                FriendOperation.getInstance().friendListener();
                                FriendStatus.getInstance().getFriendStatus();
                            }
                            public void onError(Throwable throwable, boolean b) {
                                Log.i("zjq","onError");

                                Toast.makeText(activity,"自动登录失败",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(activity.getApplicationContext(),LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();

                                for (int i = 0; i < throwable.getStackTrace().length; i++) {
                                    Log.i("mData","onError:"+throwable.getStackTrace()[i]);
                                }
                            }
                            public void onCancelled(CancelledException e) {}
                            public void onFinished() {
                                progressDialog.dismiss();
                            }
                        });
                    }
    }
    public interface IsLoginCallback{
        void isLogin(boolean isLogin,String message);
    }
    public interface RegisterCallback{
        void isRegister(boolean isRegister);
    }
}
