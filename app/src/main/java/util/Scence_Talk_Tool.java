package util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import entry.Scence;
import entry.Stop_points;

public class Scence_Talk_Tool {
    public void getScence(int limit, int offset, Scence_Talk_Tool.getScenceCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.SCENCE_LIST);
        params.addBodyParameter("limit", ""+limit);
        params.addBodyParameter("offset", ""+offset);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i("mData","Scence:"+s);
                ArrayList<Scence> res= JSON.parseObject(s,new TypeReference<ArrayList<Scence>>(){});
                for (int i = 0; i < res.size(); i++) {
                    Log.i("cwk","success:"+res.get(i).toString());
                }
                callback.onSuccess(res);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("cwk","onError:"+throwable.getMessage());
                callback.onError();
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i("cwk","onCancelled:"+e.getMessage());
            }

            @Override
            public void onFinished() {
                Log.i("cwk","onFinished");
            }
        });

    };

    public void getScenceById(int id,getScenceByIdCallback callback){
        RequestParams params=new RequestParams(Url.ROOT+Url.GET_SCENCE);
        params.addBodyParameter("id", ""+id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i("mData","Scence:"+s);
                ArrayList<Scence> scences=JSON.parseObject(s,new TypeReference<ArrayList<Scence>>(){});
                Scence res=scences.get(0);

                ArrayList<Stop_points> stopPoints=JSON.parseObject(s,new TypeReference<ArrayList<Stop_points>>(){});
                res.setStop_points(stopPoints);
                callback.onSuccess(res);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("cwk","onError:"+throwable.getMessage());
                callback.onError();
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i("cwk","onCancelled:"+e.getMessage());
            }

            @Override
            public void onFinished() {
                Log.i("cwk","onFinished");
            }
        });

    }

    public interface getScenceCallback{
        void onSuccess(ArrayList<Scence> scences);
        void onError();
    }

    public interface getScenceByIdCallback{
        void onSuccess(Scence s);
        void onError();
    }


}
