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

public class My_DeliverActivity extends AppCompatActivity {
    ListView my_deliver_listview;
    private List<Map<String,Object>> mData;
    ImageButton my_deliver_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__deliver);

        my_deliver_listview = (ListView)findViewById(R.id.my_deliver_listview);
        mData=getData();
        FondAdapter adapter=new FondAdapter(this);
        my_deliver_listview.setAdapter(adapter);

        //返回
        my_deliver_return= (ImageButton) findViewById(R.id.my_deliver_return);
        my_deliver_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("fond_user","路人甲");
        map.put("fond_introduce","南宁");
        map.put("fond_content","帅帅帅帅帅");
        map.put("fond_time","2019-08-15");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("fond_user","路人乙");
        map.put("fond_introduce","江西");
        map.put("fond_content","帅帅帅帅帅");
        map.put("fond_time","2019-05-15");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("fond_user","路人丙");
        map.put("fond_introduce","河南");
        map.put("fond_content","帅帅帅帅帅");
        map.put("fond_time","2019-02-15");
        list.add(map);
        return list;
    }
    public final class ViewHolder{
        public ImageView comment_photo;
        public TextView fond_user;
        public TextView fond_introduce;
        public TextView fond_content;
        public TextView fond_time;
    }
    public class FondAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public FondAdapter(Context context){
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
                convertView=mInflater.inflate(R.layout.my_deliver_listview,null);
                holder.fond_user= (TextView) convertView.findViewById(R.id.fond_user);
                holder.fond_introduce= (TextView) convertView.findViewById(R.id.fond_introduce);
                holder.fond_content= (TextView) convertView.findViewById(R.id.fond_content);
                holder.fond_time= (TextView) convertView.findViewById(R.id.fond_time);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.fond_user.setText((String)mData.get(position).get("fond_user"));
            holder.fond_introduce.setText((String)mData.get(position).get("fond_introduce"));
            holder.fond_content.setText((String)mData.get(position).get("fond_content"));
            holder.fond_time.setText((String)mData.get(position).get("fond_time"));

            return convertView;
        }
    }

}

