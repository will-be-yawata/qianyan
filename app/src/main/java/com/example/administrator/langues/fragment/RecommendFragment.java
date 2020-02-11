package com.example.administrator.langues.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.langues.PullDownView;
import com.example.administrator.langues.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment implements PullDownView.OnPullDownListener {

    private static ImageLoader imageLoader;// 图片缓存器

    private List<HashMap<String, Object>> list = new ArrayList<>();
    private List<HashMap<String, Object>> mData1 = new ArrayList<>();
    int pageSize=10;
    //load more
    private static final int WHAT_DID_LOAD_DATA = 0;
    private static final int WHAT_DID_REFRESH = 1;
    private static final int WHAT_DID_MORE = 2;
    private PullDownView mPullDownView;
    private ListView mListView;
    private MyAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommend, container, false);
       /*
      -------------下拉刷新上拉加载更多-------------
      -------------显示发表内容---------------------
      -------------显示内容样式和关注界面样式一样-----------
       */
      //View view=inflater.inflate(R.layout.fragment_recommend, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity()); // 请求队列
        imageLoader = new ImageLoader(queue, new VolleyBitmapLruCache(getActivity()));
        mPullDownView = view.findViewById(R.id.pull_down_view);
        mPullDownView.setOnPullDownListener(this);
        //fetchFData();
        mListView = mPullDownView.getListView();
        mAdapter=new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((parent, view1, position, id) -> {
           /* Intent i=new Intent(getActivity(),Activity.class);
            i.putExtra("id",String.valueOf(mData1.get(position).get("id")));
            startActivity(i);*/
        });
        mPullDownView.enableAutoFetchMore(true, 1);
        return view;
    }
    public final class ViewHolder{
        public NetworkImageView img;
        public TextView title;
        public TextView info;
        public TextView value;
    }
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        public int getCount() { return mData1.size(); }
        public Object getItem(int arg0) { return null; }
        public long getItemId(int arg0) { return 0; }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder=new ViewHolder();
               convertView = mInflater.inflate(R.layout.published_content, null);
                /*holder.img = (NetworkImageView)convertView.findViewById(R.id.Shirt_more_pho1);
                holder.title = (TextView)convertView.findViewById(R.id.Shirt_more_title);
                holder.info = (TextView)convertView.findViewById(R.id.Shirt_more_info);
                holder.value=(TextView)convertView.findViewById(R.id.Shirt_more_value);
*/
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText((String)mData1.get(position).get("name"));
            holder.info.setText((String)mData1.get(position).get("info"));
            holder.value.setText((String)mData1.get(position).get("value"));
            holder.img.setImageUrl(mData1.get(position).get("photo").toString(), imageLoader);
            return convertView;
        }
    }
    private void loadData(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
            msg.sendToTarget();
        }).start();
    }
    public void onRefresh() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
            msg.sendToTarget();
        }).start();
    }
    public void onMore() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
            msg.sendToTarget();
        }).start();
    }
    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DID_LOAD_DATA:
                    fetchFData();
                    break;
                case WHAT_DID_REFRESH :{
                    fetchRData();
                    break;
                }
                case WHAT_DID_MORE:{
                    fetchMData();
                    break;
                }
            }
        }
    };
    public void fetchMData(){
        String url = "http://172.16.245.96:88/json1/listjsonpage.php?offset=";
        url=url+mData1.size();
        url=url+"&pagesize=";
        url=url+pageSize;
        Utf8StringRequest request=new Utf8StringRequest(Request.Method.GET, url, resultString -> {
            Log.i("RecycleViewFragment", resultString);
            list = JSON.parseObject(resultString,
                    new TypeReference<List<HashMap<String, Object>>>() {
                    });
            mData1.addAll(list);
            mAdapter.notifyDataSetChanged();
            mPullDownView.notifyDidMore();
            int record=list.size();
            if(record< pageSize){
                mPullDownView.enableAutoFetchMore(false, 1);
                Toast.makeText(getActivity(),"No More",Toast.LENGTH_SHORT).show();
            }
        }, arg0 -> {
        });
        Volley.newRequestQueue(getContext()).add(request);
    }
    public void fetchFData(){
        String url = "http://172.16.245.96:88/json1/listjsonpage.php?offset=";
        url=url+0;
        url=url+"&pagesize=";
        url=url+pageSize;
        //System.out.println("here-----------------------------------");
        Utf8StringRequest request=new Utf8StringRequest(Request.Method.GET, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String resultString) {
                //Toast.makeText(getContext(),resultString,Toast.LENGTH_LONG).show();
                Log.i("name", resultString);
                //System.out.println("OK-----------------------------------");
                list = JSON.parseObject(resultString,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData1.clear();
                mData1.addAll(list);
                Log.i("mData1",mData1.toString());
                mPullDownView.notifyDidLoad();
            }
        }, arg0 -> Toast.makeText(getContext(),arg0.toString(),Toast.LENGTH_LONG).show());
        Volley.newRequestQueue(getContext()).add(request);
    }
    public void fetchRData(){
        String url = "http://172.16.245.96:88/json1/listjsonpage.php?offset=";
        url=url+0;
        url=url+"&pagesize=";
        url=url+pageSize;
        Utf8StringRequest request=new Utf8StringRequest(Request.Method.GET, url, resultString -> {
            Log.i("RecycleViewFragment", resultString);
            list = JSON.parseObject(resultString,
                    new TypeReference<List<HashMap<String, Object>>>() {
                    });
            mData1.clear();
            mData1.addAll(list);
            mPullDownView.notifyDidLoad();
        }, arg0 -> {});
        Volley.newRequestQueue(getContext()).add(request);
    }
    /*
     * 自定义request类 目的修改volley编码为utf-8 默认为Latin1 中文显示乱码
     */
    public static class Utf8StringRequest extends StringRequest {
        public Utf8StringRequest(int method, String url,
                                 Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String parsed = null;
            try {
                parsed = new String(response.data,
                        "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return Response.success(parsed,HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}




