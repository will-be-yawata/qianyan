package com.example.administrator.langues;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import util.Url;
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
    private Context context;
    private LayoutInflater inflater;
    private ImageView imageView;
    public final class ViewHolder{
        public ImageView imageView_holder;
    }
    public GridView_Img_Adapter(Context context){
        this.context=context;
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

        Log.i("cwk","这一条动态一共要加载"+datas.length+"张图片,当前是第"+i+"张图片");
        GridView_Img_Adapter.ViewHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.square_find_photo,null);
            holder =new GridView_Img_Adapter.ViewHolder();
            holder.imageView_holder=view.findViewById(R.id.square_photo);
            ImageOptions imageOptions=new ImageOptions.Builder()
                    //.setLoadingDrawable()
                    //        .setFailureDrawable()
                    .setUseMemCache(true)
                    //                .setCircular()
                    //        .setIgnoreGif()
                    .build();
            x.image().bind(holder.imageView_holder,Url.UPLOAD+datas[i],imageOptions);
            view.setTag(holder);
        }else{
            holder= (GridView_Img_Adapter.ViewHolder) view.getTag();
        }

        Log.i("cwk","这一条动态的第"+datas.length+"张图片加载完成,该图片的Url为"+Url.UPLOAD+datas[i]);
        return view;
    }
}
