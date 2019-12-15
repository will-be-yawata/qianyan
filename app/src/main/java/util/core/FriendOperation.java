package util.core;

import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    private EMContactListener contactListener=new EMContactListener() {
        //收到好友邀请
        public void onContactInvited(String username, String reason) {
        Log.i("mData","收到好友邀请:"+username);
        FriendStatus.getInstance().addFriendStatus(username);
        }
        //好友请求被同意
        public void onFriendRequestAccepted(String username) {
            Log.i("mData","好友请求被同意:"+username);
            addFriend(username, new AddOrDeleteFriendCallback() {
                public void onSuccess() {
                    User.getInstance().updateFriends(f->{});
                }
                public void onError(String msg) {}
            });
        }
        //好友请求被拒绝
        public void onFriendRequestDeclined(String username) {
            Log.i("mData","好友请求被拒绝:"+username);
        }
        //增加了联系人时回调此方法
        public void onContactAdded(String username) {
            Log.i("mData","增加了联系人时回调此方法:"+username);
            addFriend(username, new AddOrDeleteFriendCallback() {
                public void onSuccess() {
                    User.getInstance().updateFriends(f->{});
                }
                public void onError(String msg) {}
            });
        }
        //被删除时回调
        public void onContactDeleted(String username) {
            Log.i("mData","被删除时回调:"+username);
            User.getInstance().updateFriends(f->{});
        }
    };
    //好友请求
    public void addContact(String phone,String reason){
        try {
            EMClient.getInstance().contactManager().addContact(phone,reason);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    //好友请求被同意后服务器数据库增加好友
    private void addFriend(String phone,AddOrDeleteFriendCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.ADD_FRIEND);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("friend",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                try {
                    int num = Integer.parseInt(s);
                    if (num > 0) callback.onSuccess();
                    else if (num == 0) callback.onError("添加好友失败");
                    else callback.onError("发生未知错误");
                }catch(NumberFormatException e){
                    callback.onError("删除好友失败,服务器出错");
                }
            }
            public void onError(Throwable throwable, boolean b) {
                callback.onError("网络请求错误");
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
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
                    try {
                        int num = Integer.parseInt(s);
                        if (num > 0) callback.onSuccess();
                        else if (num == 0) callback.onError("删除好友失败");
                        else callback.onError("发生未知错误");
                    }catch(NumberFormatException e){
                        callback.onError("删除好友失败,服务器出错");
                    }
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
    public void friendListener(){
        EMClient.getInstance().contactManager().setContactListener(this.contactListener);
    }
    public interface AddOrDeleteFriendCallback{
        void onSuccess();
        void onError(String msg);
    }
}