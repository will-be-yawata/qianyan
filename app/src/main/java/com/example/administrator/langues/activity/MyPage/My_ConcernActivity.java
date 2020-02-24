package com.example.administrator.langues.activity.MyPage;

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

import com.example.administrator.langues.R;

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
        my_concern_listview= findViewById(R.id.my_concern_listview);
        mData=getData();
        ConcernAdapter adapter=new ConcernAdapter(this);
        my_concern_listview.setAdapter(adapter);
        //返回
        my_concern_return= findViewById(R.id.my_concern_return);
        my_concern_return.setOnClickListener(v -> finish());
    }
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list= new ArrayList<>();
        Map<String,Object> map= new HashMap<>();
        map.put("concern_name","路人甲");
        map.put("concern_introduce","帅哥一枚");
        list.add(map);
        map= new HashMap<>();
        map.put("concern_name","路人乙");
        map.put("concern_introduce","帅哥一枚");
        list.add(map);
        map= new HashMap<>();
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
        public int getCount() {
            return mData.size();
        }
        public Object getItem(int position) {
            return null;
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                holder =new ViewHolder();
                convertView=mInflater.inflate(R.layout.my_concern_listview,null);
                holder.concern_name= convertView.findViewById(R.id.concern_name);
                holder.concern_introduce= convertView.findViewById(R.id.concern_introduce);
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
