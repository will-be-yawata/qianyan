package com.example.administrator.langues.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class squareFindFragment extends Fragment {
    ListView square_find_listview;
    GridView gridView;


    private List<Map<String,Object>> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_square_find, container, false);

        square_find_listview=view.findViewById(R.id.square_find_listview);
        gridView=view.findViewById(R.id.square_gridview);

        mData=getData();
        squareFindAdapter squareFindAdapter=new squareFindAdapter(getContext());
        square_find_listview.setAdapter(squareFindAdapter);
        square_find_listview.setOnItemClickListener((adapterView, view1, i, l) -> { });
        return view;
    }
    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list= new ArrayList<>();
            for(int i=0;i<10;i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("square_name", "渣渣辉");
                map.put("square_introduce", "有天才有地，有情才有意，有国才有家，有家才有你，有你我才有最好的朋友。国庆佳节，用真心将一份情意揉进金秋的阳光里，用思念把一句祝福装在多情的电波中：衷心祝你节日快乐，合家幸福，一生安康。");
                list.add(map);
            }
        return list;
    }


    public final class ViewHolder{
        public GridView square_find_photo;
        public TextView square_find_name;
        public TextView square_find_introduce;
    }
    public class squareFindAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public squareFindAdapter(Context context){
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
            ViewHolder holder;
            if (convertView==null){
                holder =new ViewHolder();
                convertView=mInflater.inflate(R.layout.fragment_square_find_item,null);
                holder.square_find_photo= convertView.findViewById(R.id.square_gridview);
                holder.square_find_name= convertView.findViewById(R.id.square_name);
                holder.square_find_introduce= convertView.findViewById(R.id.square_introduce);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
            for(int i = 1;i <5;i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("square_photo", R.mipmap.m);
                meumList.add(map);
            }
            SimpleAdapter squareFindItemAdapter = new SimpleAdapter(getContext(),
                    meumList, //数据源
                    R.layout.square_find_photo, //xml实现
                    new String[]{"square_photo"}, //对应map的Key
                    new int[]{R.id.square_photo});  //对应R的Id
            holder.square_find_photo.setAdapter(squareFindItemAdapter);
            holder.square_find_name.setText((String)mData.get(position).get("square_name"));
            holder.square_find_introduce.setText((String)mData.get(position).get("square_introduce"));
            return convertView;
        }
    }
}
