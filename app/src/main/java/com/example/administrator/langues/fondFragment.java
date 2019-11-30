package com.example.administrator.langues;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class fondFragment extends Fragment {
    ImageButton concern;
    ListView fond_listview;
    private List<Map<String,Object>> mData;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fond, container, false);


        //点击关注按钮
      /*  concern= (ImageButton) view.findViewById(R.id.concern);
       concern.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//当按钮按下时
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.concern1));
                }

                return false;
            }
        });*/

       fond_listview= (ListView) view.findViewById(R.id.fond_listview);
       mData=getData();
       FondAdapter fondAdapter=new FondAdapter(getActivity());
       fond_listview.setAdapter(fondAdapter);

       fond_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getContext(),"点开我的->兑换中心查看动态详情",Toast.LENGTH_SHORT).show();
               /*Intent i=new Intent(getActivity(),Deliver_detail_Activity.class);
               i.putExtra("id",String.valueOf(mData.get(position).get("id")));
               startActivity(i);*/
           }
       });

      return view;
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
                convertView=mInflater.inflate(R.layout.published_content,null);
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
