package util.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;


public class ChatOperation {
    public final static int CHAT_TXT=0;//文本消息
    public final static int CHAT_VOICE=1;//语音消息
    public final static int CHAT_VIDEO=2;//视频消息
    public final static int CHAT_IMAGE=3;//图片消息

    private ReceiveCallback receiveCallback;
    private EMCallStateChangeListener callStateChangeListener=null;

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
    public void chat(HashMap<String,Object> content, String userId, EMMessage.ChatType chatType, int type){
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
    public void receiveListener(Context baseContext, ReceiveCallback callback){
        receiveCallback=callback;
        Log.i("zjq","receiveListener");
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
    public void addCallStateListener(StateListenerCallback callback){
        callStateChangeListener= (callState, error) -> {
            switch (callState) {
                case CONNECTING: // 正在连接对方
                    Log.i("zjq","正在连接对方");
                    break;
                case CONNECTED: // 双方已经建立连接
                    Log.i("zjq","双方已经建立连接");
                    break;
                case ACCEPTED: // 电话接通成功
                    Log.i("zjq","电话接通成功");
                    callback.accepted();
                    break;
                case DISCONNECTED: // 电话断了
                    Log.i("zjq","电话断了");
                    callback.disconnected();
                    break;
                case NETWORK_UNSTABLE: //网络不稳定
                    if(error == EMCallStateChangeListener.CallError.ERROR_NO_DATA){
                        //无通话数据
                        Log.i("zjq","网络不稳定");
                    }else{
                    }
                    break;
                case NETWORK_NORMAL: //网络恢复正常
                    Log.i("zjq","网络恢复正常");
                    break;
                default:
                    break;
            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateChangeListener);
    }
    public void closeCallStateListener(){
        if(callStateChangeListener!=null){
            EMClient.getInstance().callManager().removeCallStateChangeListener(callStateChangeListener);
            callStateChangeListener=null;
        }
    }
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            Log.i("zjq","在CallReceiver的Receive中");
            // call type
//            String type = intent.getStringExtra("type");
            receiveCallback.onReceive(from);
            //跳转到通话页面
//            Intent i=new Intent(activity.getApplicationContext(),jump);
//            i.putExtra("phone",from);
//            activity.startActivity(i);
//            jump=null;
        }

    }

    public interface StateListenerCallback{
        void accepted();
        void disconnected();
    }
    public interface ReceiveCallback{
        void onReceive(String from);
    }
}
