package com.example.administrator.langues.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.langues.R;
//import com.example.administrator.langues.activity.Chat.Friends_chatActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    ImageButton insert;
    private TabLayout news_tab;
    private ViewPager news_viewpager;
    private List<String> titles;
    private List<Fragment> fragments;

    private static ImageLoader imageLoader;// 图片缓存器
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData1 = new ArrayList<HashMap<String, Object>>();

    private String[] datas = {"选项1", "选项2", "选项3", "选项4", "选项5"};
    private List<Map<String,Object>> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_news, container, false);

        news_tab=view.findViewById(R.id.news_tab);
        news_viewpager=view.findViewById(R.id.news_viewpager);

        fragments=new ArrayList<>();
        Friend_strangerFragment friend_strangerFragment=new Friend_strangerFragment();
        fragments.add(friend_strangerFragment);
        FriendFragment friendFragment=new FriendFragment();
        fragments.add(friendFragment);
        titles=new ArrayList<>();
        titles.add("最近聊天");
        titles.add("好友");

        FriendAdapter friendAdapter=new FriendAdapter(getChildFragmentManager(),fragments,titles);
        news_viewpager.setAdapter(friendAdapter);
        news_tab.setupWithViewPager(news_viewpager);
        news_viewpager.setCurrentItem(0);

        ///下拉菜单
        insert= (ImageButton) view.findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 2016/5/17 构建一个popupwindow的布局
                View popupView = LayoutInflater.from(getContext()).inflate(R.layout.deliver_popupwindow, null);


                ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
                //LsMoreAdapter lsMoreAdapter=new LsMoreAdapter(getContext());
                lsvMore.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,datas));

                // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                PopupWindow window = new PopupWindow(popupView, 300, 600);
                // TODO: 2016/5/17 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);
                // TODO: 2016/5/17 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                // TODO: 2016/5/17 设置可以获取焦点
                window.setFocusable(true);
                // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // TODO：更新popupwindow的状态
                window.update();
                // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                window.showAsDropDown(insert, 0, 20);

            }
        });





        return view;
    }
    public class FriendAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        private List<String> titles;
        public FriendAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
            super(manager);
            this.fragments = fragments;
            this.titles = titles;

        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }






}
