package com.example.administrator.langues;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    ListView setting_listview;
    List<Map<String,Object>> mData;
    ImageButton setting_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       /* setting_listview= (ListView) findViewById(R.id.setting_listview);
        mData=getData();
        SettingAdapter adapter=new SettingAdapter(this);
        setting_listview.setAdapter(adapter);
        setting_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"hello",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getBaseContext(),Deliver_detail_Activity.class);
                intent.putExtra("setting_content",String.valueOf(mData.get(position).get("setting_content")));
                startActivity(intent);
            }
        });*/

        //返回
        setting_return= (ImageButton) findViewById(R.id.setting_return);
        setting_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("setting_content","账号与安全");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("setting_content","消息与提醒");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("setting_content","隐私设置");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("setting_content","客服中心");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("setting_content","关于软件");
        list.add(map);


        return list;
    }
    public final class ViewHolder{
        public TextView setting_content;

    }
    public class SettingAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public SettingAdapter(Context context){
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
                convertView=mInflater.inflate(R.layout.setting_listview,null);
                holder.setting_content= (TextView) convertView.findViewById(R.id.setting_content);

                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.setting_content.setText((String)mData.get(position).get("setting_content"));
            return convertView;
        }
    }*/
}
