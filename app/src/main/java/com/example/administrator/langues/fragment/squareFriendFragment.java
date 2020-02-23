package com.example.administrator.langues.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.adapter.SquareFriendAdapter;
import com.example.administrator.langues.view.RefreshListView;

import java.util.ArrayList;

import entry.Dynamic;
import entry.User;
import util.core.DynamicOperation;


/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFriendFragment extends Fragment {

    private static final int GET_DYNAMIC=0;

    private RefreshListView listView;
    private SquareFriendAdapter squareFriendAdapter;
    private Handler mHandler;

    private int limit=4;
    private int offset=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square_friend, container, false);
        init(view);
        listener();
        getData();
        return view;
    }
    @SuppressLint("HandlerLeak")
    private void init(View view){
        listView=view.findViewById(R.id.square_friend_listview);

        squareFriendAdapter =new SquareFriendAdapter(getContext());
        listView.setAdapter(squareFriendAdapter);

        mHandler=new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GET_DYNAMIC:
                        squareFriendAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }
    private void listener(){
        listView.setOnRefreshAndMoreListener(new RefreshListView.OnRefreshAndMoreListener() {
            public void onRefresh() {
                limit=4;
                offset=0;
                getData();
            }
            public void onMore() {
                offset=offset+limit;
                getData();
            }
        });
    }
    private void getData(){
        User.getInstance().updateFriends(f -> {
            if(f==null){
                Log.i("cwk","完蛋");
                return ;
            }
            DynamicOperation dynamicOperation=new DynamicOperation();
            dynamicOperation.getDynamic(limit, offset, new DynamicOperation.DynamicGetCallback() {
                public void onSuccess(ArrayList<Dynamic> res) {
                    if(offset<=0) {
                        squareFriendAdapter.setData(res);
                        listView.onRefreshComplete();
                    }else{
                        if(res.size()<=0){
                            Toast.makeText(SquareFriendFragment.this.getContext(),"没有更多了哦~",Toast.LENGTH_SHORT).show();
                            listView.onMoreComplete();
                            return;
                        }
                        squareFriendAdapter.addData(res);
                        listView.onMoreComplete();
                    }
                    Message msg=mHandler.obtainMessage();
                    msg.what=GET_DYNAMIC;
                    mHandler.sendMessage(msg);
                }
                public void onError() {
                    if(offset<=0)
                        listView.onRefreshComplete();
                    else
                        listView.onMoreComplete();
                }
            });
        });
    }
}
