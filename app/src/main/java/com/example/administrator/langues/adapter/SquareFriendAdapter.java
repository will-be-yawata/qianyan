package com.example.administrator.langues.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import entry.Dynamic;
import util.Url;

public class SquareFriendAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Dynamic> dynamicData=new ArrayList<>();

    public SquareFriendAdapter(Context context){
        this.mInflater=LayoutInflater.from(context);
    }
    public void setData(List<Dynamic> datas){
        dynamicData.clear();
        dynamicData.addAll(datas);
    }
    public void addData(List<Dynamic> datas){
        dynamicData.addAll(datas);
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
            holder.photo= convertView.findViewById(R.id.square_gridview);
            holder.name= convertView.findViewById(R.id.square_name);
            holder.introduce= convertView.findViewById(R.id.square_introduce);
            holder.user_img=convertView.findViewById(R.id.square_pho);
            holder.tv_appreciate=convertView.findViewById(R.id.square_tv_appreciate);
            holder.ib_appreciate=convertView.findViewById(R.id.square_ib_appreciate);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        int countImg=dynamicData.get(position).getImg().length;
        GridView_Img_Adapter gridView_img_adapter = new GridView_Img_Adapter(mInflater.getContext());
        gridView_img_adapter.setData(dynamicData.get(position).getImg());
        gridView_img_adapter.notifyDataSetChanged();

        if(countImg==1||countImg==2||countImg==4)
            holder.photo.setNumColumns(2);
        else
            holder.photo.setNumColumns(3);
        holder.photo.setAdapter(gridView_img_adapter);
        holder.name.setText(dynamicData.get(position).getName());
        holder.introduce.setText(dynamicData.get(position).getText());
//        int agreeCount=Integer.parseInt(dynamicData.get(position).getAgree());
        holder.tv_appreciate.setText(dynamicData.get(position).getAgree());
        String like=dynamicData.get(position).getLike();
        if(like!=null && !like.equals(""))
            holder.ib_appreciate.setImageResource(R.mipmap.appreciated);
        else
            holder.ib_appreciate.setImageResource(R.mipmap.appreciate);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setRadius(DensityUtil.dip2px(10))
                .setLoadingDrawableId(R.mipmap.ic_launcher)//正在加载时的图片
                .setFailureDrawableId(R.mipmap.ic_launcher).build();//加载失败时的图片
        x.image().bind(holder.user_img, Url.USER_IMG+dynamicData.get(position).getUser_img(),imageOptions);
        return convertView;
    }
    public class ViewHolder{
        GridView photo;
        TextView name;
        TextView introduce;
        ImageView user_img;
        ImageButton ib_appreciate;
        TextView tv_appreciate;
    }
}