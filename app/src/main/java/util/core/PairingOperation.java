package util.core;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import entry.User;
import util.Url;

public class PairingOperation {
    public static int WAIT=0;
    public static int PAIRING=1;

    /**
     *
     * @param phone     对手的手机号
     * @param callback  成功：参数包含data.phone(对手手机号)、data.name(对手昵称)、data.img(对手头像)、data.dan(对手段位)
     *                   失败：msg说明了一切
     */
    public void getEnemy(String phone,GetEnemyCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_ENEMY);
        params.addBodyParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (!s.equals("false")){
                    HashMap<String,String> res=JSON.parseObject(s,new TypeReference<HashMap<String,String>>(){});
                    callback.onSuccess(res);
                }else{
                    callback.onError("找不到对手信息");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.onError(throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
    public CancelPairing pairing(PairingCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.PAIRING);
        params.addBodyParameter("phone",User.getInstance().getPhone());
        Callback.Cancelable cancelable=x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                HashMap<String,String> result;
                result=JSON.parseObject(s,new TypeReference<HashMap<String,String>>(){});
                if(result.get("success").equals("true")){
                    Log.i("mData",result.toString());
                    Log.i("mData","type:"+result.get("type"));
                    String type=result.get("type");
                    if(type!=null && type.equals("join")){
                        HashMap<String,String> res;//=(HashMap<String,String>)result.get("data");
                        res=JSON.parseObject(result.get("data"),new TypeReference<HashMap<String,String>>(){});
                        Log.i("mData",res.get("id"));
                        Log.i("mData",res.get("name"));
                        Log.i("mData",res.get("owner"));
                        Log.i("mData",res.get("img"));
                        callback.onSuccess(PAIRING,res);
                    }else if(type!=null && type.equals("create")){
                        Log.i("mData",result.get("data"));
                        HashMap<String,String> res=new HashMap<>();
                        res.put("id",result.get("data"));
                        callback.onSuccess(WAIT,res);
                    }else{
                        callback.onError("网络不行或服务器出错");
                    }
                }else{
                    callback.onError("网络不行或服务器出错");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callback.onError(throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                callback.onCancelled();
            }

            @Override
            public void onFinished() {
            }
        });
        return new CancelPairing(cancelable);
    }
    public interface PairingCallback{
        void onSuccess(int status,HashMap<String,String> data);
        void onCancelled();
        void onError(String msg);
    }
    public interface GetEnemyCallback{
        void onSuccess(HashMap<String,String> data);
        void onError(String msg);
    }
    class CancelPairing{
        Callback.Cancelable request;
        public CancelPairing(Callback.Cancelable request){
            this.request=request;
        }
        void cancel(Callback.Cancelable request){
            if(!request.isCancelled())
                request.cancel();
        }
    }
}
