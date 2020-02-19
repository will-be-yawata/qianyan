package com.example.administrator.langues.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    ImageButton insert;
    private TabLayout news_tab;
    private ViewPager news_viewpager;
    private List<String> titles;
    private List<Fragment> fragments;

    private String[] datas = {"选项1", "选项2", "选项3", "选项4", "选项5"};
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        insert= view.findViewById(R.id.insert);
        insert.setOnClickListener(v -> {
            // TODO: 2016/5/17 构建一个popupwindow的布局
            View popupView = LayoutInflater.from(getContext()).inflate(R.layout.deliver_popupwindow, null);
            ListView lsvMore = popupView.findViewById(R.id.lsvMore);
            //LsMoreAdapter lsMoreAdapter=new LsMoreAdapter(getContext());
            lsvMore.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, datas));
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
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        public int getCount() {
            return fragments.size();
        }
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
