package com.example.administrator.langues.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.langues.R;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AdapterWeek extends BaseAdapter {

    private String[] week = {"日","一","二","三","四","五","六"};
    private Context context;

    public AdapterWeek(Context context) {
        this.context = context;
    }
    public int getCount() {
        return week.length;
    }
    public Object getItem(int i) {
        return week[i];
    }
    public long getItemId(int i) {
        return i;
    }
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.sign_item,null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv = view.findViewById(R.id.tvWeek);
        viewHolder.tv.setText(week[i]);
        return view;
    }
    class ViewHolder{
        TextView tv;
    }
}
