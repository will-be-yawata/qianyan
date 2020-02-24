package util.core;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import entry.User;
import util.Url;

public class FriendStatus {
    //-------------------单例模式----------------------
    private static FriendStatus Instance;
    private FriendStatus(){}
    public static FriendStatus getInstance(){
        if(Instance==null)
            synchronized (FriendStatus.class) {
                if (Instance == null)
                    Instance = new FriendStatus();
            }
        return Instance;
    }
    //------------------------------------------------
    private ArrayList<String> invitedFriends=new ArrayList<>();
    public void getFriendStatus(){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_FRIEND_STATUS);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                if(!s.equals("false")) {
                    invitedFriends = JSON.parseObject(s, new TypeReference<ArrayList<String>>() {});
                    for (int i = 0; i < invitedFriends.size(); i++) {
                        Log.i("mData", invitedFriends.get(i));
                    }
//                    callback.onSuccess();
//                }else{
//                    callback.onError("服务器请求失败");
                }
            }
            public void onError(Throwable throwable, boolean b) {
//                callback.onError("请求失败："+throwable.getMessage());
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
    public ArrayList<String> getInvitedFriends(){
        return invitedFriends;
    }
    void addFriendStatus(String friend){
        for (int i = 0; i < invitedFriends.size(); i++) {
            if(invitedFriends.get(i).equals(friend)) return;
        }
        RequestParams params=new RequestParams(Url.ROOT+Url.ADD_FRIEND_STATUS);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("friend",friend);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                try {
                    if (Integer.parseInt(s) > 0)
                        invitedFriends.add(friend);
                }catch(NumberFormatException e){
                    Log.i("mData","字符转换数字失败");
                }
            }
            public void onError(Throwable throwable, boolean b) {
                Log.i("mData","addFriends.onError:"+throwable.getCause().getMessage());
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
    void deleteFriendStatus(String friend){
        int mFlag=-1;
        for (int i = 0; i < invitedFriends.size(); i++) {
            if(invitedFriends.get(i).equals(friend)) {
                mFlag=i;
                break;
            }
        }
        if(mFlag==-1) return;
        RequestParams params=new RequestParams(Url.ROOT+Url.DELETE_FRIEND_STATUS);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("friend",friend);
        int finalMFlag = mFlag;
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                try {
                    if (Integer.parseInt(s) > 0)
                        invitedFriends.remove(finalMFlag);
                }catch(NumberFormatException e){
                    Log.i("mData","字符转换数字失败");
                }
            }
            public void onError(Throwable throwable, boolean b) {
                Log.i("mData","addFriends.onError:"+throwable.getCause().getMessage());
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
//    public interface GetFriendStatusCallback{
//        void onSuccess();
//        void onError(String msg);
//    }
}