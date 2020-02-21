package com.example.administrator.langues.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Chat.Friends_chatActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friend, container, false);

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
        //*这个方法是ListAcrivity里面继承过来的*//*
        ListView listView= (ListView) view.findViewById(R.id.friend_listview);
        listView.setAdapter(listAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent=new Intent(getActivity(),Friends_chatActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
