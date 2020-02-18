package com.example.administrator.langues;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.langues.activity.LoginActivity;
import com.example.administrator.langues.activity.MainActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

import org.xutils.x;

import entry.User;
import util.EMHelp;
import util.core.FriendOperation;

public class MyApplication extends Application {
    private boolean isBackground=true;
    @Override
    public void onCreate() {
        super.onCreate();
        initEM();
        x.Ext.init(this);

        listenForForeground();
        listenForScreenTurningOff();
    }
    private void initEM() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("test", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    //应用切换至前台
    private void listenForForeground(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityResumed(Activity activity) {
                if (isBackground) {
                    isBackground = false;
                    notifyForeground(activity);
                }
            }
            public void onActivityCreated(Activity activity, Bundle bundle) {}
            public void onActivityStarted(Activity activity) {}
            public void onActivityPaused(Activity activity) {}
            public void onActivityStopped(Activity activity) {}
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
            public void onActivityDestroyed(Activity activity) {}
        });
    }
    //手机熄屏
    private void listenForScreenTurningOff() {
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                isBackground = true;
                notifyBackground();
            }
        }, screenStateFilter);
    }
    //应用切换至后台
    public void onTrimMemory(int level){
        super.onTrimMemory(level);
        if(level==TRIM_MEMORY_UI_HIDDEN){
            isBackground=true;
            notifyBackground();
        }
    }
    private void notifyForeground(Activity activity) {
        Log.i("mData","调用notifyForeground()");
        Log.i("mData","isConnected():"+EMClient.getInstance().isLoggedInBefore());
        Log.i("mData","isLoggedInBefore():"+EMClient.getInstance().isLoggedInBefore());
        Log.i("mData","getCurrentUser():"+EMClient.getInstance().getCurrentUser());
        Log.i("mData","getPhone():"+User.getInstance().getPhone());
        if(EMClient.getInstance().isConnected())
            if (EMClient.getInstance().isLoggedInBefore())
                if(EMClient.getInstance().getCurrentUser()!=null && !EMClient.getInstance().getCurrentUser().equals(""))
                    if (User.getInstance().getPhone()==null || User.getInstance().getPhone().equals("")) {
                        ProgressDialog progressDialog = new ProgressDialog(activity);
                        progressDialog.setTitle("自动登录");
                        progressDialog.setMessage("正在获得用户信息...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        new EMHelp().autologin(EMClient.getInstance().getCurrentUser(), new EMHelp.AutoLoginCallback() {
                            public void onSuccess() {
                                FriendOperation.getInstance().friendListener();
                                Intent intent=new Intent(activity.getApplicationContext(),MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                                Log.i("mData",User.getInstance().getPhone());
                            }
                            public void onError() {
                                Toast.makeText(activity,"自动登录失败",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(activity.getApplicationContext(),LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            public void onFinished(){
                                progressDialog.dismiss();
                            }
                        });
                    }
    }
    private void notifyBackground() {}
    public boolean isBackground() {
        return isBackground;
    }
}
