package com.example.administrator.langues.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.handmark.pulltorefresh.library.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class squareFriendFragment extends Fragment {
    private PullToRefreshListView square_friend_listview;
    private squareFindAdapter squareFriendAdapter;
    GridView gridView;
    List<Map<String,Object>> mData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_square_friend, container, false);
       square_friend_listview=view.findViewById(R.id.square_friend_listview);
        //设置可上拉刷新和下拉刷新
        square_friend_listview.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时显示的文本
        ILoadingLayout startLayout = square_friend_listview.getLoadingLayoutProxy(true,false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = square_friend_listview.getLoadingLayoutProxy(false,true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");

        square_friend_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(squareFriendFragment.this).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(squareFriendFragment.this).execute();
            }
        });


        gridView=view.findViewById(R.id.square_gridview);


        mData=getData();
        squareFriendAdapter=new squareFindAdapter(getContext());
        square_friend_listview.setAdapter(squareFriendAdapter);
        square_friend_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });




        return view;
    }
    /**
     * 异步下载任务
     */
    private static class LoadDataAsyncTask extends AsyncTask<Void,Void,String> {

        private squareFriendFragment mainActivity;

        public LoadDataAsyncTask(squareFriendFragment mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                mainActivity.getData();
                return "seccess";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("seccess")){
                mainActivity.squareFriendAdapter.notifyDataSetChanged();
                mainActivity.square_friend_listview.onRefreshComplete();//刷新完成
            }
        }
    }
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<10;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("square_name", "渣渣辉");
            map.put("square_introduce", "长亭外，古道边，芳草碧连天。晚风拂柳笛声残，夕阳山外山。天之涯，地之角，知交半零落。一壶浊酒尽余欢，今宵别梦寒。");
            list.add(map);
        }
        return list;
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
            return mData.size();
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


            SimpleAdapter squareFindItemAdapter = new SimpleAdapter(getContext(),
                    meumList, //数据源
                    R.layout.square_find_photo, //xml实现
                    new String[]{"square_photo"}, //对应map的Key
                    new int[]{R.id.square_photo});  //对应R的Id

            holder.square_friend_photo.setAdapter(squareFindItemAdapter);
            holder.square_friend_name.setText((String)mData.get(position).get("square_name"));
            holder.square_friend_introduce.setText((String)mData.get(position).get("square_introduce"));


            return convertView;
        }
    }

}
