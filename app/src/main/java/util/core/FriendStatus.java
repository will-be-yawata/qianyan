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
                        Log.i("zjq", invitedFriends.get(i));
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
    /**
     * 工作：收到好友邀请时，将该状态存入数据库和invitedFriends数组中
     * 过程：   首先判断是否已存入数组中，
     *          有则结束，否则将数据存入数据库并返回，"1" 代表存入成功或之前已经存入，"0" 代表失败
     * @param friend
     */
    void addFriendStatus(String friend){
        for (int i = 0; i < invitedFriends.size(); i++) {
            if(invitedFriends.get(i).equals(friend)) return;
        }
        RequestParams params=new RequestParams(Url.ROOT+Url.ADD_FRIEND_STATUS);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("friend",friend);
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                if(s.equals("1")) invitedFriends.add(friend);
            }
            public void onError(Throwable throwable, boolean b) {
                Log.i("zjq","addFriends.onError:"+throwable.getCause().getMessage());
            }
            public void onCancelled(CancelledException e) {}
            public void onFinished() {}
        });
    }
    /**
     * 工作：好友同意时，将该状态从数组中删除并将数据库中的数据删除
     * 过程：   首先判断数组中是否存在，
     *          不存在则结束，存在则删除数据库中的数据并从数组中删除，
     *          "1" 代表删除成功或数据库中没有该数据，"0" 代表失败
     * @param friend
     */
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
        int finaMFlag = mFlag;
        x.http().post(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String s) {
                if(s.equals("1")) invitedFriends.remove(finaMFlag);
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