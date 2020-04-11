package com.example.administrator.langues.activity.Matching.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.Dialogue_listActivity;
import com.example.administrator.langues.activity.Matching.ListItem;
import com.example.administrator.langues.activity.Matching.Situational_dialogueActivity;


import java.util.ArrayList;
import java.util.List;

public class dialogueAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//布局装载器
    private ArrayList<ListItem> list;
    private Context context;
    private int mposition = -1;
    private ViewHolder holder;

    public dialogueAdapter(Context context, ArrayList<ListItem> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       if (convertView==null){
           convertView=mInflater.inflate(R.layout.dialogue_list_items,null);
            holder =new ViewHolder();
            holder.list_name= convertView.findViewById(R.id.list_name);
            holder.list_pho= convertView.findViewById(R.id.list_pho);
            holder.people_online= convertView.findViewById(R.id.people_online);
            holder.global_relative=convertView.findViewById(R.id.global_relative);
           holder.list_relative=convertView.findViewById(R.id.list_relative);

           holder.list_confirm=convertView.findViewById(R.id.list_confirm);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //获取相应索引的ListItem对象
        ListItem item=list.get(position);
        holder.list_pho.setImageResource(item.getListImageResId());
        holder.list_name.setText(item.getList_name());
        holder.people_online.setText(item.getListNum());
        holder.global_relative.setOnClickListener(new onClickListener(position));//点击整个布局，显示匹配安按钮
        holder.list_confirm.setOnClickListener(new clickButtonListener());//开始匹配
        if(mposition == position){
            holder.list_relative.setVisibility(View.VISIBLE);//显示开始匹配
        }else{
            holder.list_relative.setVisibility(View.GONE);//隐藏开始匹配
        }
        return convertView;

    }
    class ViewHolder {
        public TextView list_name;
        public ImageView list_pho;
        public TextView people_online;
        public Button list_confirm;
        public RelativeLayout list_relative;
        public RelativeLayout global_relative;

    }
    class onClickListener implements View.OnClickListener{//判断点击那个item，显示开始匹配按钮
                private int position;

        public onClickListener(int position) {
            this.position=position;
        }

        @Override
        public void onClick(View view) {

            if(mposition == position){
                mposition = -1;
            }else{
                mposition = position;
            }
            notifyDataSetChanged();
        }
    }
    class clickButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.list_confirm://点击开始匹配

                    Intent intent= new Intent(context,Situational_dialogueActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("MID",list.get(mposition).getListNum());
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                    //Toast.makeText(context,"1111",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    }
