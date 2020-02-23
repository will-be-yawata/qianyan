package com.example.administrator.langues.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.langues.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import util.Url;

public class GridView_Img_Adapter extends BaseAdapter {
    private String [] datas;
    private LayoutInflater inflater;
    public GridView_Img_Adapter(Context context){
        inflater=LayoutInflater.from(context);
    }
    public void setData(String[] datas){
        this.datas=datas;
    }
    public int getCount() {
        return datas.length;
    }
    public String getItem(int i) {
        return datas[i];
    }
    public long getItemId(int i) {
        return 0;
    }
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("cwk","这一条动态一共要加载"+datas.length+"张图片,当前是第"+i+"张图片");
        GridView_Img_Adapter.ViewHolder holder;
        if (view==null){
            view=inflater.inflate(R.layout.square_find_photo,null);
            holder =new GridView_Img_Adapter.ViewHolder();
            holder.imageView_holder=view.findViewById(R.id.square_photo);
            ImageOptions imageOptions=new ImageOptions.Builder()
                    .setUseMemCache(true)
                    .build();
            x.image().bind(holder.imageView_holder,Url.UPLOAD+datas[i],imageOptions);
            view.setTag(holder);
        }else{
            holder= (GridView_Img_Adapter.ViewHolder) view.getTag();
        }
        return view;
    }
    public class ViewHolder{
        public ImageView imageView_holder;
    }
}
