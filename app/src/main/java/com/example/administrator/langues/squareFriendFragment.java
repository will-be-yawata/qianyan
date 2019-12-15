package com.example.administrator.langues;


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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entry.Dynamic;
import entry.Friend;
import entry.User;
import util.core.DynamicOperation;


/**
 * A simple {@link Fragment} subclass.
 */
public class squareFriendFragment extends Fragment {
    ListView square_friend_listview;
    GridView gridView;
    private GridView_Img_Adapter gridView_img_adapter;
    private ArrayList<String[]> datas;
    List<Map<String,Object>> mData=new ArrayList<>();
    squareFindAdapter squareFindAdapter;
    Handler mHandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 2:
                break;
            case 1:
                squareFindAdapter=new squareFindAdapter(getContext());
                square_friend_listview.setAdapter(squareFindAdapter);
                square_friend_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
                mData.addAll((List<Map<String,Object>>)msg.obj);
                squareFindAdapter.notifyDataSetChanged();
                break;
        }
    }
};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_square_friend, container, false);
       square_friend_listview=view.findViewById(R.id.square_friend_listview);
        gridView=view.findViewById(R.id.square_gridview);
        datas=new ArrayList<>();
        gridView_img_adapter=new GridView_Img_Adapter(getContext());
        getData();
        return view;
    }
    private void getData(){
        User.getInstance().setPhone("15919698189");
        User.getInstance().updateFriends(new User.UpdateFriendsCallback() {
            @Override
            public void updateFriends(ArrayList<Friend> f) {
                if(f==null){
                    Log.i("cwk","完蛋");
                }
                DynamicOperation dynamicOperation=new DynamicOperation();
                dynamicOperation.getDynamic(0, 10, new DynamicOperation.DynamicGetCallback() {
                    @Override
                    public void getDynamicData(ArrayList<Dynamic> res) {
                        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
                        for(Dynamic dynamic:res) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("square_name", dynamic.getName());
                            map.put("square_introduce", dynamic.getText());
                            datas.add(dynamic.getImg());
                            list.add(map);
                        }
                        Message tmp=mHandler.obtainMessage();
                        tmp.what=1;
                        tmp.obj=list;
                        mHandler.sendMessage(tmp);
                    }
                });

            }
        });
    }


    public final class ViewHolder{
        public GridView square_friend_photo;
        public TextView square_friend_name;
        public TextView square_friend_introduce;
    }
    public class squareFindAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public squareFindAdapter(Context context){
            this.mInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
//            return mData.size();
            return mData==null?0:mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("cwk","准备加载第"+position+"条动态");
            ViewHolder holder=null;
            if (convertView==null){
                holder =new ViewHolder();
                convertView=mInflater.inflate(R.layout.fragment_square_find_item,null);
                holder.square_friend_photo= (GridView) convertView.findViewById(R.id.square_gridview);
                holder.square_friend_name= (TextView) convertView.findViewById(R.id.square_name);
                holder.square_friend_introduce= (TextView) convertView.findViewById(R.id.square_introduce);

                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
            for(int i = 1;i <6;i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("square_photo", R.mipmap.view);
                meumList.add(map);
            }

            gridView_img_adapter.setData(datas.get(position));
            gridView_img_adapter.notifyDataSetChanged();
//            SimpleAdapter squareFindItemAdapter = new SimpleAdapter(getContext(),
//                    meumList, //数据源
//                    R.layout.square_find_photo, //xml实现
//                    new String[]{"square_photo"}, //对应map的Key
//                    new int[]{R.id.square_photo});  //对应R的Id

            holder.square_friend_photo.setAdapter(gridView_img_adapter);
            holder.square_friend_name.setText((String)mData.get(position).get("square_name"));
            holder.square_friend_introduce.setText((String)mData.get(position).get("square_introduce"));


            return convertView;
        }
    }

}
