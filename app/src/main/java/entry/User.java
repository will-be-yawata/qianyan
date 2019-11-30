package entry;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

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
    private String img;
    private ArrayList<Friend> friends=null;

    public static User Instance;
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
    public boolean addFriend(Friend f){
        if(friends.add(f))
            return true;
        return false;
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
    public void updateFriends(){
        ArrayList<Friend> friends;
        RequestParams params=new RequestParams(Url.ROOT+Url.FRIEND);
        params.addBodyParameter("username",phone);
        try {
            friends=JSON.parseObject(x.http().postSync(params,String.class),new TypeReference<ArrayList<Friend>>(){});
            System.out.println(friends.toString());
            if(friends.size()!=0)
                this.friends=friends;
        } catch (Throwable throwable) {
            Log.i("mData","出错了 in User.updateFriends()\t,"+throwable.getMessage());
        }
    }
    public ArrayList<Friend> getFriends(){
        if(friends==null)
            updateFriends();
        return friends;
    }
    public void release(){Instance=null;}

}
