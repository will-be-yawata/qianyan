package com.example.administrator.langues.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.HashMap;


public class SquareFindAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public SquareFindAdapter(Context context){
        this.mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 0;
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
        ViewHolder holder;
//        if (convertView==null){
//            holder =new ViewHolder();
//            convertView=mInflater.inflate(R.layout.fragment_square_find_item,null);
//            holder.square_find_photo= convertView.findViewById(R.id.square_gridview);
//            holder.square_find_name= convertView.findViewById(R.id.square_name);
//            holder.square_find_introduce= convertView.findViewById(R.id.square_introduce);
//            convertView.setTag(holder);
//        }else{
//            holder= (ViewHolder) convertView.getTag();
//        }
//        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
//        for(int i = 1;i <5;i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("square_photo", R.mipmap.m);
//            meumList.add(map);
//        }
//        holder.square_find_photo.setAdapter();
//        holder.square_find_name.setText((String)mData.get(position).get("square_name"));
//        holder.square_find_introduce.setText((String)mData.get(position).get("square_introduce"));
        return convertView;
    }
    class ViewHolder{
        public GridView square_find_photo;
        public TextView square_find_name;
        public TextView square_find_introduce;
    }
}