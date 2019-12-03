package util.core;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import entry.Dynamic;
import entry.User;
import util.Url;

public class DynamicOperation {
    public void getDynamic(){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_DYNAMIC);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<Dynamic> res=JSON.parseObject(s,new TypeReference<ArrayList<Dynamic>>(){});
                for (int i = 0; i < res.size(); i++) {
                    Log.i("mData",res.get(i).toString());
                }
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("mData","onError:"+throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i("mData","onCancelled:"+e.getMessage());
            }

            @Override
            public void onFinished() {
                Log.i("mData","onFinished");
            }
        });
    }
    public void publishDynamic(){

    }
}
