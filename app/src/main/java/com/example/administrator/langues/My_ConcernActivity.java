package com.example.administrator.langues;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_ConcernActivity extends AppCompatActivity {
    ListView my_concern_listview;
    List<Map<String,Object>> mData;

    ImageButton my_concern_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__concern);
        my_concern_listview= (ListView) findViewById(R.id.my_concern_listview);
        mData=getData();
        ConcernAdapter adapter=new ConcernAdapter(this);
        my_concern_listview.setAdapter(adapter);

        //返回
        my_concern_return= (ImageButton) findViewById(R.id.my_concern_return);
        my_concern_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("concern_name","路人甲");
        map.put("concern_introduce","帅哥一枚");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("concern_name","路人乙");
        map.put("concern_introduce","帅哥一枚");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("concern_name","路人丙");
        map.put("concern_introduce","帅哥一枚");
        list.add(map);
        return list;
    }
    public final class ViewHolder{
        public ImageView comment_photo;
        public TextView concern_name;
        public TextView concern_introduce;

    }
    public class ConcernAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ConcernAdapter(Context context){
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
                convertView=mInflater.inflate(R.layout.my_concern_listview,null);
                holder.concern_name= (TextView) convertView.findViewById(R.id.concern_name);
                holder.concern_introduce= (TextView) convertView.findViewById(R.id.concern_introduce);

                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.concern_name.setText((String)mData.get(position).get("concern_name"));
            holder.concern_introduce.setText((String)mData.get(position).get("concern_introduce"));

            return convertView;
        }
    }

}
