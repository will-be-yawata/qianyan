package util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.langues.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.common.Callback;
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
    private Class jump;
    public void init(Activity activity){
        this.activity=activity;
    }


    public void registered(String phone,String pwd,RegisterCallback callback){

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
    public void login(String phone,String pwd,IsLoginCallback callback){
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
    public void autologin(String phone,AutoLoginCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.AUTOLOGIN);
        params.addBodyParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                Log.i("mData","success:"+s);
                HashMap<String,String> result;
                result=JSON.parseObject(s,new TypeReference<HashMap<String,String>>(){});
                User.getInstance().setPhone(result.get("phone"));
                User.getInstance().setName(result.get("name"));
                User.getInstance().setPwd(result.get("pwd"));
                User.getInstance().setImg(result.get("img"));
                User.getInstance().setSex(result.get("sex"));
                User.getInstance().setDan(result.get("dan"));
                callback.onSuccess();
            }
            public void onError(Throwable throwable, boolean b) {
                Log.i("mData","onError");
                callback.onError();
                for (int i = 0; i < throwable.getStackTrace().length; i++) {
                    Log.i("mData","onError:"+throwable.getStackTrace()[i]);
                }
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
    public void join(String roomId){
        Log.i("mData",roomId);
        EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {
            @Override
            public void onSuccess(EMChatRoom value) {
                String roomid=value.getId();
                Log.i("mData",value.getId());
                try {
                    EMChatRoom rootDetail=EMClient.getInstance().chatroomManager().fetchChatRoomFromServer(roomid);
                    Log.i("mData","name:"+rootDetail.getName()+"\n"
                            +"Owner:"+rootDetail.getOwner()+"\n"
                            +"Description:"+rootDetail.getDescription());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(int error, String errorMsg) {
                Log.i("mData",errorMsg);
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

    //拨打电话
    public void voiceCall(String username){
        try{
            EMClient.getInstance().callManager().makeVoiceCall(username);
         }catch(EMServiceNotReadyException e){
            e.printStackTrace();

        }
    }
    //监听呼入通话
    public void receiveListener(Context baseContext,Class jump){
        this.jump=jump;
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        CallReceiver callReceiver=new CallReceiver();
        baseContext.registerReceiver(callReceiver, callFilter);
    }
    //接听
    public void answerCall(){
        try {
            EMClient.getInstance().callManager().answerCall();
        } catch (EMNoActiveCallException e) {
            e.printStackTrace();
        }
    }
    //挂断
    public void endCall(){
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (EMNoActiveCallException e) {
            e.printStackTrace();
        }
    }
    //监听通话状态
    public void callStateListener(StateListenerCallback callback){
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, EMCallStateChangeListener.CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        Log.i("mData","正在连接对方");
                        break;
                    case CONNECTED: // 双方已经建立连接
                        Log.i("mData","双方已经建立连接");
                        break;
                    case ACCEPTED: // 电话接通成功
                        Log.i("mData","电话接通成功");
                        callback.accepted();
                        break;
                    case DISCONNECTED: // 电话断了
                        Log.i("mData","电话断了");
                        callback.disconnected();
                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                            Log.i("mData","网络不稳定");
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常
                        Log.i("mData","网络恢复正常");
                        break;
                    default:
                        break;
                }

            }
        });
    }
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
//            String type = intent.getStringExtra("type");

            //跳转到通话页面
            Intent i=new Intent(activity.getApplicationContext(),jump);
            i.putExtra("phone",from);
            activity.startActivity(i);
            jump=null;
        }

    }
    public interface IsLoginCallback{
        void isLogin(boolean isLogin,String message);
    }
    public interface RegisterCallback{
        void isRegister(boolean isRegister);
    }
    public interface StateListenerCallback{
        void accepted();
        void disconnected();
    }
    public interface AutoLoginCallback{
        void onSuccess();
        void onError();
    }
}
