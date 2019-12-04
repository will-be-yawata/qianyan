package util.core;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

import entry.Dynamic;
import entry.User;
import util.Url;

public class DynamicOperation {
    public void getDynamic(int limit,int offset,DynamicGetCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_DYNAMIC);
        params.addBodyParameter("phone", User.getInstance().getPhone());
        params.addBodyParameter("limit", ""+limit);
        params.addBodyParameter("offset", ""+offset);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<Dynamic> res=JSON.parseObject(s,new TypeReference<ArrayList<Dynamic>>(){});
                for (int i = 0; i < res.size(); i++) {
                    Log.i("mData","success:"+res.get(i).toString());
                }
                callback.getDynamicData(res);

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
    /**
     * 只发文字
     * @param text
     */
    public void publishDynamic(String text,DynamicPublishCallback callback){
        sendPublishDynamic(text,null,callback);
    }
    /**
     * 只发图片
     * @param results
     */
    public void publishDynamic(ArrayList<String> results,DynamicPublishCallback callback){
        sendPublishDynamic(null,results,callback);
    }
    /**
     * 即发文字又图片
     * @param text
     * @param results
     */
    public void publishDynamic(String text,ArrayList<String> results,DynamicPublishCallback callback){
        sendPublishDynamic(text,results,callback);
    }
    public void sendPublishDynamic(String text,ArrayList<String> results,DynamicPublishCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.PUBLISH_DYNAMIC);
        if(text!=null && text.equals("")){
            params.addBodyParameter("text",text);
        }
        if(results!=null && results.size()>0){
            params.setMultipart(true);
            for (int i = 0; i < results.size(); i++) {
                params.addBodyParameter("img"+i,new File(results.get(i)),null);
            }

        }
        params.addBodyParameter("phone", User.getInstance().getPhone());
//        params.addBodyParameter("img",new File("url"),null,"文件名");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                callback.publishDynamicData(s);
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
    public interface DynamicGetCallback{
        //TODO 返回好友发表的动态，按时间倒序
        void getDynamicData(ArrayList<Dynamic> res);
    }
    public interface  DynamicPublishCallback{
        //TODO 返回影响条数 1表示成功，0表示失败
        void publishDynamicData(String s);
    }
}
