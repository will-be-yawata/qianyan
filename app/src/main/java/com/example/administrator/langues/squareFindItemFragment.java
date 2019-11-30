package com.example.administrator.langues;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class squareFindItemFragment extends Fragment {
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_square_find_item, container, false);
      /*   ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
        for(int i = 1;i < 9;i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("square_photo", R.mipmap.user);
            meumList.add(map);
        }

        gridView=view.findViewById(R.id.square_gridview);

        SimpleAdapter squareFindItemAdapter = new SimpleAdapter(getContext(),
                meumList, //数据源
                R.layout.square_find_photo, //xml实现
                new String[]{"square_photo"}, //对应map的Key
                new int[]{R.id.square_photo});  //对应R的Id
        gridView.setAdapter(squareFindItemAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


*/


        return view;
    }




}
