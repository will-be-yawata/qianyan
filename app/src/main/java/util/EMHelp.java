package util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import entry.User;


public class EMHelp {
    public final static int CHAT_TXT=0;//文本消息
    public final static int CHAT_VOICE=1;//语音消息
    public final static int CHAT_VIDEO=2;//视频消息
    public final static int CHAT_IMAGE=3;//图片消息

    private Activity activity;
    public void init(Activity activity){
        this.activity=activity;
    }

    /**
     * 检查完用户名和密码的合法性后再调用
     * @param phone 唯一标识，用户编号，手机号码
     * @param nickname 用户昵称
     * @param pwd 用户密码
     * @return
     */
    public boolean registered(String phone,String nickname,String pwd){
        Map<String,Object> result;
        RequestParams params=new RequestParams(Url.ROOT+Url.REGISTER);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("nickname",nickname);
        params.addBodyParameter("pwd",pwd);
        try {
            result=JSON.parseObject(x.http().postSync(params,String.class),new TypeReference<HashMap<String,Object>>(){});
            activity.runOnUiThread(()->Toast.makeText(x.app(),result.get("message").toString(),Toast.LENGTH_SHORT).show());
            if(Boolean.parseBoolean(result.get("success").toString())) return true;
        } catch (Throwable throwable) {
            System.out.println("mData:错误");
            activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show());
        }
        return false;
    }
    public void login(String phone,String pwd){
        EMClient.getInstance().login(phone, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                Map<String,String> result;
                User user=User.getInstance();
                RequestParams params=new RequestParams(Url.ROOT+Url.LOGIN);
                params.addBodyParameter("phone",phone);
                params.addBodyParameter("pwd",pwd);
                try {
                    activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"start try",Toast.LENGTH_SHORT).show());
                    result=JSON.parseObject(x.http().postSync(params,String.class),new TypeReference<HashMap<String,String>>(){});
                    user.setPhone(result.get("phone"));
                    user.setName(result.get("name"));
                    user.setPwd(result.get("pwd"));
                    user.setImg(result.get("img"));
                    user.setSex(result.get("sex"));
                    activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show());
//                    activity.startActivity(new Intent(activity.getApplicationContext(),jump));
                    activity.finish();
                } catch (Throwable throwable) {
                    user.release();
                    Log.e("test","I am here");
                    activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show());
                }
            }
            @Override
            public void onError(int code, String error) {
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),error,Toast.LENGTH_SHORT).show());
                User.getInstance().release();
                Log.e("test","on Error,I am here");
            }
            @Override
            public void onProgress(int progress, String status) {
            }
        });
    }

    /**
     *
     * @param content 文本消息提供content;
     *                语音消息和视频消息提供content(路径)和length(长度);
     *                图片消息提供content(路径)和original(是否原图)
     * @param userId
     * @param chatType
     * @param type
     */
    public void chat(HashMap<String,Object> content, String userId, EMMessage.ChatType chatType,int type){
        EMMessage message;
        switch(type){
            case CHAT_TXT:
                message=EMMessage.createTxtSendMessage(content.get("content").toString(),userId);
                if(chatType== EMMessage.ChatType.GroupChat)
                    message.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(message);
                break;
            case CHAT_VOICE:
                message=EMMessage.createVoiceSendMessage(content.get("content").toString(),(int)((float)content.get("length")),userId);
                if(chatType== EMMessage.ChatType.GroupChat)
                    message.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(message);
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"成功发送",Toast.LENGTH_SHORT).show());
                break;
            case CHAT_VIDEO:
                break;
            case CHAT_IMAGE:
                break;

        }
    }
    public void voiceCall(String username,HashMap<String,Object> data){
        if(data==null){
            try{
                EMClient.getInstance().callManager().makeVoiceCall(username);
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"拨打...",Toast.LENGTH_SHORT).show());
            }catch(EMServiceNotReadyException e){
                e.printStackTrace();
            }
        }else{
            if(data.size()>0){
                try {
                    EMClient.getInstance().callManager().makeVoiceCall(username,data.toString());
                }catch(EMServiceNotReadyException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void addBroadcastReceiver(HashMap<String,View> btn){
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        activity.getBaseContext().registerReceiver(new CallReceiver(), callFilter);
        btn.get("answer").setOnClickListener(view->{
            try {
                EMClient.getInstance().callManager().answerCall();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"接听...",Toast.LENGTH_SHORT).show());
            } catch (EMNoActiveCallException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"接听出错",Toast.LENGTH_SHORT).show());
            }
        });
        btn.get("reject").setOnClickListener(view->{
            try {
                EMClient.getInstance().callManager().rejectCall();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"拒绝",Toast.LENGTH_SHORT).show());
            } catch (EMNoActiveCallException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"拒绝出错",Toast.LENGTH_SHORT).show());
            }
        });
        btn.get("end").setOnClickListener(view -> {
            try {
                EMClient.getInstance().callManager().endCall();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"挂断",Toast.LENGTH_SHORT).show());
            } catch (EMNoActiveCallException e) {
                e.printStackTrace();
                activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),"挂断出错",Toast.LENGTH_SHORT).show());
            }
        });
    }
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            activity.runOnUiThread(()->Toast.makeText(activity.getApplicationContext(),from+"来电...",Toast.LENGTH_SHORT).show());
            Log.i("mData","from:"+from+",type:"+type);

            //跳转到通话页面
        }
    }
}
