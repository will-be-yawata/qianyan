package entry;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import util.Url;

/**
 * Created by up on 2019/8/18.
 */

public class User {
    private String phone;
    private String name;
    private String pwd;
    private String sex;
    private String dan;
    private String img;
    private ArrayList<Friend> friends=null;

    private static User Instance;
    private User(){}

    public static User getInstance(){
        if(Instance==null) synchronized (User.class) {if (Instance == null) Instance = new User();}
        return Instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public boolean addFriend(Friend f){
        return friends.add(f);
    }
    public boolean removeFriend(Friend f){
        String phone=f.getPhone();
        for(int i=0;i<friends.size();i++){
            if(phone.equals(friends.get(i).getPhone())) {
                friends.remove(i);
                return true;
            }
        }
        return false;
    }
    public void updateFriends(UpdateFriendsCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_FRIEND);
        params.addBodyParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<Friend> fs=JSON.parseObject(s,new TypeReference<ArrayList<Friend>>(){});
                if(fs.size()!=0)
                    friends=fs;
                callback.updateFriends(friends);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("zjq",throwable.getMessage());
                callback.updateFriends(null);
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
    public ArrayList<Friend> getFriends(){
        if(friends==null)
            friends=new ArrayList<>();
        return friends;
    }
    public void release(){Instance=null;}

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                ", img='" + img + '\'' +
                ", friends=" + friends +
                '}';
    }

    public interface UpdateFriendsCallback{
        void updateFriends(ArrayList<Friend> f);
    }
}
