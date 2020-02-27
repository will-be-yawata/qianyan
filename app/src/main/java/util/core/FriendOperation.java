package util.core;

import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import entry.Friend;
import entry.User;
import util.Url;

public class FriendOperation {
    //----------------单例模式------------------------
    private static FriendOperation Instance;
    private FriendOperation(){}
    public static FriendOperation getInstance(){
        if(Instance==null)
            synchronized (FriendOperation.class) {
                if (Instance == null)
                    Instance = new FriendOperation();
            }
        return Instance;
    }
    //------------------------------------------------
    private EMContactListener contactListener;
    private FriendStatus friendStatus=FriendStatus.getInstance();
    public void setContactListener(ContactVallback listener){
        contactListener=new EMContactListener() {
            //收到好友邀请
            public void onContactInvited(String username, String reason) {
                friendStatus.addFriendStatus(username);
                listener.onBeInvited();
            }
            //好友请求被同意
            public void onFriendRequestAccepted(String username) {
                friendStatus.deleteFriendStatus(username);
                listener.onBeAccepted();
            }


            //好友请求被拒绝
            public void onFriendRequestDeclined(String username) {

                listener.onBeDeclined();
            }
            //增加了联系人时回调此方法
            public void onContactAdded(String username) {
                addFriend(username, new AddOrDeleteFriendCallback() {
                    public void onSuccess() {
                        User.getInstance().updateFriends(f->{});
                    }
                    public void onError(String msg) {}
                });
                listener.onBeAdded();
            }
            //被删除时回调
            public void onContactDeleted(String username) {
                User.getInstance().updateFriends(f->{});
                listener.onBeDeleted();
            }
        };
    }
    //好友请求
    public void addContact(String phone,String reason){
        try {
            EMClient.getInstance().contactManager().addContact(phone,reason);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    //删除好友
    public void deleteFriend(String phone,AddOrDeleteFriendCallback callback){
        try {
            EMClient.getInstance().contactManager().deleteContact(phone);
            RequestParams params=new RequestParams(Url.ROOT+Url.DELETE_FRIEND);
            params.addBodyParameter("phone", User.getInstance().getPhone());
            params.addBodyParameter("friend",phone);
            x.http().post(params, new Callback.CommonCallback<String>() {
                public void onSuccess(String s) {
                    if(s.equals("1")) {
                        User.getInstance().updateFriends(f -> {
                            callback.onSuccess();
                        });
                    }
                    else if(s.equals("0")) callback.onError("删除好友失败");
                    else callback.onError("发生未知错误");
                }
                public void onError(Throwable throwable, boolean b) {
                    callback.onError("网络请求错误");
                }
                public void onCancelled(CancelledException e) {}
                public void onFinished() {}
            });
        } catch (HyphenateException e) {
            callback.onError("删除好友失败,"+e.getDescription());
        }
    }
    //同意好友请求
    public void acceptFriend(String friend){
        try {
            EMClient.getInstance().contactManager().acceptInvitation(friend);
            FriendStatus.getInstance().deleteFriendStatus(friend);
            addFriend(friend, new AddOrDeleteFriendCallback() {
                public void onSuccess() {}
                public void onError(String msg) {}
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
            Log.i("mData",e.getDescription());
        }
    }
    //拒绝好友请求
    public void declineFriend(String friend){
        try {
            EMClient.getInstance().contactManager().declineInvitation(friend);
            FriendStatus.getInstance().deleteFriendStatus(friend);
        } catch (HyphenateException e) {
            e.printStackTrace();
            Log.i("mData",e.getDescription());
        }
    }
    //好友请求被同意后服务器数据库增加好友
    private void addFriend(String phone,AddOrDeleteFriendCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.ADD_FRIEND);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("friend",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                if(s.equals("成功")) callback.onSuccess();
                else if(s.equals("失败")) callback.onError("好友已添加");
                else callback.onError("发生未知错误");
//                    int num = Integer.parseInt(s);
//                    if (num > 0) callback.onSuccess();
//                    else if (num == 0) callback.onError("添加好友失败");
//                    else callback.onError("发生未知错误");

            }
            public void onError(Throwable throwable, boolean b) {
                callback.onError("网络请求错误");
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
    public void friendListener(){
        EMClient.getInstance().contactManager().setContactListener(this.contactListener);
    }
    public interface AddOrDeleteFriendCallback{
        void onSuccess();
        void onError(String msg);
    }
    public interface ContactVallback{
        void onBeInvited();//收到好友邀请时回调
        void onBeAccepted();//请求被同意时回调
        void onBeDeclined();//请求被拒绝时回调
        void onBeAdded();//被添加成功时回调
        void onBeDeleted();//被删除时回调
    }
}