package com.example.administrator.langues;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    ImageButton insert;

    private static ImageLoader imageLoader;// 图片缓存器
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData1 = new ArrayList<HashMap<String, Object>>();

    private String[] datas = {"选项1", "选项2", "选项3", "选项4", "选项5"};
    private List<Map<String,Object>> mData;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_news, container, false);

        insert= (ImageButton) view.findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //定义一个ArrayList类型的动态list ，里面存放的是HashMap键值对形式
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<40;i++){
            //定义一个临时的hashMap
            HashMap<String,String> hashMap = new HashMap<String, String>();
            //存入姓名键值对
            hashMap.put("contacts_name","用户"+ i);
            //存入ID
            hashMap.put("contacts_content","Hello"+i);
            //讲hashMap存入List
            list.add(hashMap);
        }
        SimpleAdapter listAdpter = new SimpleAdapter(
                getContext(),
                list,
                R.layout.contacts_listview,
                new String[] {"contacts_name","contacts_content"},
                new int[] {R.id.contacts_name,R.id.contacts_content}
        );
        /*这个方法是ListAcrivity里面继承过来的*/
        ListView listView= (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(listAdpter);

        ///发表下拉菜单
        insert= (ImageButton) view.findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 2016/5/17 构建一个popupwindow的布局
                View popupView = LayoutInflater.from(getContext()).inflate(R.layout.deliver_popupwindow, null);


                ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
                //LsMoreAdapter lsMoreAdapter=new LsMoreAdapter(getContext());
                lsvMore.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,datas));

                // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                PopupWindow window = new PopupWindow(popupView, 300, 600);
                // TODO: 2016/5/17 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);
                // TODO: 2016/5/17 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                // TODO: 2016/5/17 设置可以获取焦点
                window.setFocusable(true);
                // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // TODO：更新popupwindow的状态
                window.update();
                // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                window.showAsDropDown(insert, 0, 20);

            }
        });





        return view;
    }

    /*private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("deliver","发表");

        list.add(map);

        return list;
    }
    public final class ViewHolder{
        public ImageView deliver_photo;
        public TextView deliver_text;

    }

    public class LsMoreAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public LsMoreAdapter(Context context){
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
                convertView=mInflater.inflate(R.layout.deliver_list,null);
                holder.deliver_text= (TextView) convertView.findViewById(R.id.deliver_text);


                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.deliver_text.setText((String)mData.get(position).get("deliver_text"));


            return convertView;
        }
    }*/




}
