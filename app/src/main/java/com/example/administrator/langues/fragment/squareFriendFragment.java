package com.example.administrator.langues.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.GridView_Img_Adapter;
import com.example.administrator.langues.R;
import com.example.administrator.langues.view.RefreshListView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entry.Dynamic;
import entry.Friend;
import entry.User;
import util.Url;
import util.core.DynamicOperation;


/**
 * A simple {@link Fragment} subclass.
 */
public class squareFriendFragment extends Fragment {

    private static final int GET_DYNAMIC=0;

    private RefreshListView square_friend_listview;
    private GridView gridView;
    private GridView_Img_Adapter gridView_img_adapter;
    private ArrayList<String[]> datas;
    private List<Dynamic> dynamicData=new ArrayList<>();
    private squareFindAdapter squareFindAdapter;
    private Handler mHandler;

    private int limit=4;
    private int offset=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square_friend, container, false);
        init(view);
        listener();
        getData();
        return view;
    }
    @SuppressLint("HandlerLeak")
    private void init(View view){
        square_friend_listview=view.findViewById(R.id.square_friend_listview);
        gridView=view.findViewById(R.id.square_gridview);
        datas=new ArrayList<>();
        gridView_img_adapter=new GridView_Img_Adapter(getContext());

        squareFindAdapter=new squareFindAdapter(getContext());
        square_friend_listview.setAdapter(squareFindAdapter);

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GET_DYNAMIC:
                        squareFindAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }
    private void listener(){
        square_friend_listview.setOnRefreshAndMoreListener(new RefreshListView.OnRefreshAndMoreListener() {
            public void onRefresh() {
                limit=4;
                offset=0;
                getData();
            }
            public void onMore() {
                offset=offset+limit;
                getData();
            }
        });
    }
    private void getData(){
        User.getInstance().updateFriends(f -> {
            if(f==null){
                Log.i("cwk","完蛋");
                return ;
            }
            DynamicOperation dynamicOperation=new DynamicOperation();
            dynamicOperation.getDynamic(limit, offset, new DynamicOperation.DynamicGetCallback() {
                public void onSuccess(ArrayList<Dynamic> res) {
                    if(offset<=0) {
                        dynamicData.clear();
                        dynamicData=res;
                        square_friend_listview.onRefreshComplete();
                    }else{
                        if(res.size()<=0){
                            Toast.makeText(squareFriendFragment.this.getContext(),"没有更多了哦~",Toast.LENGTH_SHORT).show();
                            square_friend_listview.onMoreComplete();
                            return;
                        }
                        dynamicData.addAll(res);
                        square_friend_listview.onMoreComplete();
                    }
                    Message msg=mHandler.obtainMessage();
                    msg.what=GET_DYNAMIC;
                    mHandler.sendMessage(msg);
                }
                public void onError() {
                    if(offset<=0)
                        square_friend_listview.onRefreshComplete();
                    else
                        square_friend_listview.onMoreComplete();
                }
            });
        });
    }
    public final class ViewHolder{
        GridView square_friend_photo;
        TextView square_friend_name;
        TextView square_friend_introduce;
        ImageView square_friend_user_img;
    }
    public class squareFindAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        squareFindAdapter(Context context){
            this.mInflater=LayoutInflater.from(context);
        }
        public int getCount() {
            return dynamicData==null?0:dynamicData.size();
        }
        public Dynamic getItem(int position) {
            return dynamicData.get(position);
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("cwk","准备加载第"+position+"条动态");
            ViewHolder holder;
            if (convertView==null){
                holder =new ViewHolder();
                convertView=mInflater.inflate(R.layout.fragment_square_find_item,null);
                holder.square_friend_photo= convertView.findViewById(R.id.square_gridview);
                holder.square_friend_name= convertView.findViewById(R.id.square_name);
                holder.square_friend_introduce= convertView.findViewById(R.id.square_introduce);
                holder.square_friend_user_img=convertView.findViewById(R.id.square_pho);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            gridView_img_adapter=new GridView_Img_Adapter(getContext());
            gridView_img_adapter.setData(dynamicData.get(position).getImg());
            gridView_img_adapter.notifyDataSetChanged();

            holder.square_friend_photo.setAdapter(gridView_img_adapter);
            holder.square_friend_name.setText(dynamicData.get(position).getName());
            holder.square_friend_introduce.setText(dynamicData.get(position).getText());

            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setRadius(DensityUtil.dip2px(10))
                    .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                    .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
            x.image().bind(holder.square_friend_user_img, Url.USER_IMG+dynamicData.get(position).getUser_img(),imageOptions);
            return convertView;
        }
    }
}
