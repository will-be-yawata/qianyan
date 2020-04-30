package util.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entry.Message;
import entry.User;
import util.Url;


public class ChatOperation {
    private ReceiveCallback receiveCallback;
    private EMCallStateChangeListener callStateChangeListener=null;
    public void join(String roomId){
        EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {
            @Override
            public void onSuccess(EMChatRoom value) {
                String roomid=value.getId();
                try {
                    EMChatRoom rootDetail=EMClient.getInstance().chatroomManager().fetchChatRoomFromServer(roomid);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(int error, String errorMsg) {
//                Log.i("mData",errorMsg);
            }
        });
    }
    public void sendMessage(Message m){
        EMMessage message=null;
        switch(m.getType()){
            case Message.TYPE_TEXT:
                message=EMMessage.createTxtSendMessage(m.getContent(),m.getTo());
                break;
            case Message.TYPE_IMAGE:
                message=EMMessage.createImageSendMessage(m.getContent(),m.isOriginal(),m.getTo());
                break;
            case Message.TYPE_VOICE://音频
                message=EMMessage.createVoiceSendMessage(m.getContent(),(int)m.getLength(),m.getTo());
                break;
            case Message.TYPE_VIDEO:
                message=EMMessage.createVideoSendMessage(m.getContent(),m.getThumbPath(),(int)m.getLength(),m.getTo());
                break;
        }
        saveMessage(m);
        if(message!=null){
            EMClient.getInstance().chatManager().sendMessage(message);
        }
    }
    private void saveMessage(Message m){
        RequestParams params=new RequestParams(Url.ROOT+Url.SAVE_MESSAGE);
        switch (m.getType()){
            case Message.TYPE_TEXT: break;
            case Message.TYPE_VOICE:
                params.setMultipart(true);
                params.addBodyParameter("voice",new File(m.getContent()));
                break;
        }
        params.addBodyParameter("message", JSON.toJSON(m));
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
//                Log.i("zjq","result:"+result);
            }
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
//                Log.i("zjq","ex:"+ex.getMessage());
            }
            public void onCancelled(CancelledException cex) {}
            public void onFinished() {}
        });
    }
    public void getMessage(String from,String to,int limit,int offset,GetMessageCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_MESSAGE);
        params.addBodyParameter("from",from);
        params.addBodyParameter("to",to);
        params.addBodyParameter("limit",limit);
        params.addBodyParameter("offset",offset);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                ArrayList<Message> data=JSON.parseObject(s,new TypeReference<ArrayList<Message>>(){});
                for(int i=0;i<data.size();i++){
                    if(data.get(i).getType()==Message.TYPE_VOICE)
                        data.get(i).setContent(Url.UPLOAD_CHAT_VOICE+data.get(i).getContent());
                }
                callback.onSuccess(data);
            }
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                callback.onError(ex.getMessage());
            }
            public void onCancelled(CancelledException cex) {}
            public void onFinished() {}
        });
    }
    public EMMessageListener addOnMessageListenerCall(OnMessageListenerCall listener){
        EMMessageListener emMessageListener=new EMMessageListener() {
            public void onMessageReceived(List<EMMessage> messages) {
                ArrayList<Message> ms = new ArrayList<>();
                for (int i = 0; i < messages.size(); i++) {
                    EMMessage emMessage = messages.get(i);
                    Message message = new Message();
                    if (!emMessage.getFrom().equals(listener.getFriendPhone()))
                        continue;
                    message.setFrom(emMessage.getFrom());
                    message.setTo(User.getInstance().getPhone());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    message.setTime(format.format(emMessage.getMsgTime()));
                    switch (emMessage.getType()) {
                        case TXT:
                            message.setType(Message.TYPE_TEXT);
                            message.setContent(((EMTextMessageBody) emMessage.getBody()).getMessage());
                            break;
                        case IMAGE:
                            EMImageMessageBody imageMessageBody = (EMImageMessageBody) emMessage.getBody();
                            message.setType(Message.TYPE_IMAGE);
                            message.setContent(imageMessageBody.getRemoteUrl());
                            break;
                        case VOICE://音频
                            EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) emMessage.getBody();
                            message.setType(Message.TYPE_VOICE);
                            message.setContent(voiceMessageBody.getRemoteUrl());
                            message.setLength(voiceMessageBody.getLength());
                            break;
                        case VIDEO://视频
                            EMVideoMessageBody emVideoMessageBody = (EMVideoMessageBody) emMessage.getBody();
                            message.setType(Message.TYPE_VIDEO);
                            message.setContent(emVideoMessageBody.getRemoteUrl());
                            message.setLength((int) emVideoMessageBody.getVideoFileLength());
                            message.setThumbPath(emVideoMessageBody.getThumbnailUrl());
                            break;
                    }
                    ms.add(message);
                }
                listener.onReceived(ms);
            }
            public void onCmdMessageReceived(List<EMMessage> messages) {}
            public void onMessageRead(List<EMMessage> messages) {}
            public void onMessageDelivered(List<EMMessage> messages) {}
            public void onMessageRecalled(List<EMMessage> messages) {}
            public void onMessageChanged(EMMessage message, Object change) {}
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        return emMessageListener;
    }
    public EMMessageListener addOnCountReceivedListener(OnCountReceivedListener listener){
        EMMessageListener emMessageListener = new EMMessageListener() {
            public void onMessageReceived(List<EMMessage> messages) {
                Map<String,Integer> map= new HashMap<>();
                List<Map<String,Object>> list=new ArrayList<>();
                for(int i=0;i<messages.size();i++){
                    EMMessage message =messages.get(i);
                    String from=message.getFrom();
                    if(map.get(from)!=null){
                        map.put(from,map.get(from)+1);
                    }else{
                        map.put(from,1);
                    }
                }
                for(String key : map.keySet()){
                    HashMap<String,Object> data=new HashMap<>();
                    data.put("phone",key);
                    data.put("num",map.get(key));
                    list.add(data);
                }
                listener.onCountReceived(list);
            }
            public void onCmdMessageReceived(List<EMMessage> messages) {}
            public void onMessageRead(List<EMMessage> messages) {}
            public void onMessageDelivered(List<EMMessage> messages) {}
            public void onMessageRecalled(List<EMMessage> messages) {}
            public void onMessageChanged(EMMessage message, Object change) {}
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        return emMessageListener;
    }
    public void removeMessageListener(EMMessageListener eMMessageListener){
        EMClient.getInstance().chatManager().removeMessageListener(eMMessageListener);
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
                    break;
                case CONNECTED: // 双方已经建立连接
                    break;
                case ACCEPTED: // 电话接通成功
                    callback.accepted();
                    break;
                case DISCONNECTED: // 电话断了
                    callback.disconnected();
                    break;
                case NETWORK_UNSTABLE: //网络不稳定
                    if(error == EMCallStateChangeListener.CallError.ERROR_NO_DATA){
                        //无通话数据
                    }else{
                    }
                    break;
                case NETWORK_NORMAL: //网络恢复正常
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
        public void onReceive(Context context, Intent intent) {
            String from = intent.getStringExtra("from");
            receiveCallback.onReceive(from);
        }
    }

    public interface StateListenerCallback{
        void accepted();
        void disconnected();
    }
    public interface ReceiveCallback{
        void onReceive(String from);
    }
    public interface GetMessageCallback{
        void onSuccess(ArrayList<Message> data);
        void onError(String msg);
    }
    public interface OnCountReceivedListener{
        void onCountReceived(List<Map<String,Object>> list);
    }
    public interface OnMessageListenerCall{
        void onReceived(ArrayList<Message> ms);
        String getFriendPhone();
    }
}
