package com.example.administrator.langues;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deliver_detail_Activity extends AppCompatActivity {
    ListView comment_listview;
    private List<Map<String,Object>> mData;
    ImageButton detail_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_detail_);

         comment_listview = (ListView)findViewById(R.id.comment_listview);
         mData=getData();
         CommentAdapter adapter=new CommentAdapter(this);
         comment_listview.setAdapter(adapter);

         //返回
        detail_return= (ImageButton) findViewById(R.id.detail_return);
        detail_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("comment_user","路人甲");
        map.put("comment_introduce","广东");
        map.put("comment_user_comment","帅帅帅帅帅");
        map.put("comment_time","2019-05-15");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("comment_user","路人乙");
        map.put("comment_introduce","西安");
        map.put("comment_user_comment","帅帅帅帅帅");
        map.put("comment_time","2019-05-18");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("comment_user","路人丁");
        map.put("comment_introduce","福建");
        map.put("comment_user_comment","帅帅帅帅帅");
        map.put("comment_time","2019-08-15");
        list.add(map);
        return list;
    }
    public final class ViewHolder{
        public ImageView comment_photo;
        public TextView comment_user;
        public TextView comment_introduce;
        public TextView comment_user_comment;
        public TextView comment_time;
    }
    public class CommentAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public CommentAdapter(Context context){
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
                convertView=mInflater.inflate(R.layout.comment_listview,null);
                holder.comment_user= (TextView) convertView.findViewById(R.id.comment_user);
                holder.comment_introduce= (TextView) convertView.findViewById(R.id.comment_introduce);
                holder.comment_user_comment= (TextView) convertView.findViewById(R.id.comment_user_comment);
                holder.comment_time= (TextView) convertView.findViewById(R.id.comment_time);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.comment_user.setText((String)mData.get(position).get("comment_user"));
            holder.comment_introduce.setText((String)mData.get(position).get("comment_introduce"));
            holder.comment_user_comment.setText((String)mData.get(position).get("comment_user_comment"));
            holder.comment_time.setText((String)mData.get(position).get("comment_time"));
            return convertView;
        }
    }
}
