package com.example.administrator.langues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import util.Url;

public class GridView_Img_Adapter extends BaseAdapter {
    private String [] datas;
    private LayoutInflater inflater;
    private ImageView imageView;
    public final class ViewHolder{
        public ImageView imageView_holder;
    }
    public GridView_Img_Adapter(Context context){
        inflater=LayoutInflater.from(context);
    }
    public void setData(String[] datas){
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridView_Img_Adapter.ViewHolder holder=null;
        if (view==null){
            holder =new GridView_Img_Adapter.ViewHolder();
            view=inflater.inflate(R.layout.square_find_photo,null);
            this.imageView=view.findViewById(R.id.square_photo);
            view.setTag(holder);
        }else{
            holder= (GridView_Img_Adapter.ViewHolder) view.getTag();
        }
        ImageOptions imageOptions=new ImageOptions.Builder()
                //.setLoadingDrawable()
//        .setFailureDrawable()
        .setUseMemCache(true)
//                .setCircular()
//        .setIgnoreGif()
        .build();
        x.image().bind(this.imageView,Url.UPLOAD+datas[i],imageOptions);
        return view;
    }
}
