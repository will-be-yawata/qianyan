package com.example.administrator.langues.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import entry.Friend;
import entry.User;
import util.Url;

public class FriendAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Friend> data=new ArrayList<>();
    public FriendAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        User.getInstance().updateFriends(friends -> {
            data=friends;
            notifyDataSetChanged();
        });
    }
    public Friend getData(int position){
        if(data.size()>0)
            return data.get(position);
        return null;
    }
    public boolean setBubbles(String phone,int num){
        for(int i=0;i<data.size();i++){
            if(data.get(i).getPhone().equals(phone)){
                data.get(i).setMsgNum(data.get(i).getMsgNum()+num);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }
    public boolean resetBubbles(String phone){
        for(int i=0;i<data.size();i++){
            if(data.get(i).getPhone().equals(phone)){
                data.get(i).setMsgNum(0);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }
    public int getCount() {
        return data.size();
    }
    public Friend getItem(int position) {
        return data.get(position);
    }
    public long getItemId(int i) {
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.contacts_listview,null);
            holder.photo=convertView.findViewById(R.id.contacts_photo);
            holder.name=convertView.findViewById(R.id.contacts_name);
            holder.bubbles= convertView.findViewById(R.id.contacts_bubbles);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ImageOptions options=new ImageOptions.Builder()
                .setRadius(DensityUtil.dip2px(6))
                .build();
        x.image().bind(holder.photo, Url.USER_IMG+data.get(position).getImg(),options);
        if(data.get(position).getName()==null || data.get(position).getName().equals(""))
            holder.name.setText(data.get(position).getPhone());
        else
            holder.name.setText(data.get(position).getName());
        int msgNum=data.get(position).getMsgNum();
        if(msgNum>0){
            holder.bubbles.setText(""+msgNum);
            holder.bubbles.setVisibility(View.VISIBLE);
        }else {
            holder.bubbles.setText("0");
            holder.bubbles.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView photo;
        TextView name;
        TextView bubbles;
    }
}
