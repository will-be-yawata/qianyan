package com.example.administrator.langues.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.User_data.Top_up_Amount;


import java.util.ArrayList;

public class AdapterAmount extends BaseAdapter {

    private ArrayList<Top_up_Amount> amountList;
    private LayoutInflater layoutInflater;

    public AdapterAmount(ArrayList<Top_up_Amount> list, Context context){
        amountList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return amountList.size();
    }

    @Override
    public Object getItem(int position) {
        return amountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            //加载布局
            convertView =layoutInflater.inflate(R.layout.top_up_amount_grid_item,null);

            holder = new ViewHolder();
            //holder.imgChannel = (ImageView)convertView.findViewById(R.id.channel_img);
            holder.decChannel = (TextView)convertView.findViewById(R.id.channel_dec);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        //设置图标和文字
        Top_up_Amount channel = amountList.get(position);
        if(channel != null){
            holder.decChannel.setText(channel.getDec());

        }
        return convertView;
    }

    class ViewHolder{
        //ImageView imgChannel;
        TextView decChannel;
    }
}

