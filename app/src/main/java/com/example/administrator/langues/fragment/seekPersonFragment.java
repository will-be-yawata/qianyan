package com.example.administrator.langues.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class seekPersonFragment extends Fragment {
    ListView seekperson;
    private List<Map<String,Object>> mData;
    Button E_btn,K_btn,J_btn,T_btn,C_btn,I_btn,P_btn,S_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_seek_person, container, false);
        seekperson=(ListView)view.findViewById(R.id.seekperson_listview);
        E_btn=(Button)view.findViewById(R.id.E_btn);
        K_btn=(Button)view.findViewById(R.id.K_btn);
        J_btn=(Button)view.findViewById(R.id.J_btn);
        T_btn=(Button)view.findViewById(R.id.T_btn);
        C_btn=(Button)view.findViewById(R.id.C_btn);
        I_btn=(Button)view.findViewById(R.id.I_btn);
        P_btn=(Button)view.findViewById(R.id.P_btn);
        S_btn=(Button)view.findViewById(R.id.S_btn);


        mData=getData();
        seekPersonAdater seekPersonAdater=new seekPersonAdater(getContext());
        seekperson.setAdapter(seekPersonAdater);
        seekperson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        E_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(false);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        K_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(false);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        J_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(false);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        T_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(false);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        C_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(false);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        I_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(false);
                P_btn.setEnabled(true);
                S_btn.setEnabled(true);

            }
        });
        P_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(false);
                S_btn.setEnabled(true);

            }
        });
        S_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E_btn.setEnabled(true);
                K_btn.setEnabled(true);
                J_btn.setEnabled(true);
                T_btn.setEnabled(true);
                C_btn.setEnabled(true);
                I_btn.setEnabled(true);
                P_btn.setEnabled(true);
                S_btn.setEnabled(false);

            }
        });

        return view;

    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<100;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("room_Id", "123456");
            map.put("room_Name", "朵儿");
            map.put("room_Langues", "英语");

            list.add(map);
        }

        return list;
    }
    public final class ViewHolder{
        public ImageView room_image;
        public TextView room_Id;
        public TextView room_Name;
        public TextView room_Langues;

    }
    public class seekPersonAdater extends BaseAdapter {
        private LayoutInflater mInflater;
        public seekPersonAdater(Context context){
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
                convertView=mInflater.inflate(R.layout.seekperson_content,null);
                holder.room_Id= (TextView) convertView.findViewById(R.id.roomID);
                holder.room_Name= (TextView) convertView.findViewById(R.id.roomName);
                holder.room_Langues= (TextView) convertView.findViewById(R.id.roomLangues);

                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.room_Id.setText((String)mData.get(position).get("room_Id"));
            holder.room_Name.setText((String)mData.get(position).get("room_Name"));
            holder.room_Langues.setText((String)mData.get(position).get("room_Langues"));


            return convertView;
        }
    }
}
