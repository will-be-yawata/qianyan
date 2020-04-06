package com.example.administrator.langues.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.adapter.SquareFriendAdapter;
import com.example.administrator.langues.view.RefreshListView;

import java.util.ArrayList;

import entry.Dynamic;
import util.core.DynamicOperation;


/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFindFragment extends Fragment {
    private static final int GET_SQUARE=0;
    private SquareFriendAdapter adapter;
    private RefreshListView listView;
    private Handler mHandler;
    int limit=4;
    int offset=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square_find, container, false);
        init(view);
        listener();
        getData();
        return view;
    }
    @SuppressLint("HandlerLeak")
    private void init(View view){
        listView=view.findViewById(R.id.square_find_listview);
        adapter=new SquareFriendAdapter(this.getContext());
        listView.setAdapter(adapter);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GET_SQUARE:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }
    private void listener(){
        listView.setOnRefreshAndMoreListener(new RefreshListView.OnRefreshAndMoreListener() {
            public void onRefresh() {
                offset=0;
                getData();
            }
            public void onMore() {
                offset+=limit;
                getData();
            }
        });
    }
    private void getData(){
        DynamicOperation dynamicOperation=new DynamicOperation();
        dynamicOperation.getSquare(limit, offset, new DynamicOperation.DynamicGetCallback() {
            public void onSuccess(ArrayList<Dynamic> res) {
                if(offset<=0) {
                    adapter.setData(res);
                    listView.onRefreshComplete();
                }else{
                    if(res.size()<=0){
                        Toast.makeText(SquareFindFragment.this.getContext(),"没有更多了哦~",Toast.LENGTH_SHORT).show();
                        listView.onMoreComplete();
                        return;
                    }
                    adapter.addData(res);
                    listView.onMoreComplete();
                }
                Message msg=mHandler.obtainMessage();
                msg.what=GET_SQUARE;
                mHandler.sendMessage(msg);
            }
            public void onError() {
                if(offset<=0)
                    listView.onRefreshComplete();
                else
                    listView.onMoreComplete();}
        });
    }
}
