package com.example.administrator.langues.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
    private TabLayout tabLayout;
    private FragmentManager fm;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_tab, container, false);
        tabLayout = view.findViewById(R.id.tabs);

        fragments = new ArrayList<>();
        SeekFragment seekPersonFragment=new SeekFragment();
        fragments.add(seekPersonFragment);
        //squareFindItemFragment squareFindItemFragment=new squareFindItemFragment();
        //fragments.add(squareFindItemFragment);

        SquareFragment squareFragment=new SquareFragment();
        fragments.add(squareFragment);

        NewsFragment newsFragment=new NewsFragment();
        fragments.add(newsFragment);
        PersonalFragment personalFragment=new PersonalFragment();
        fragments.add(personalFragment);

        fm=super.getChildFragmentManager();

        tabLayout.addTab(tabLayout.newTab().setText("匹配").setIcon(R.mipmap.find1));
        tabLayout.addTab(tabLayout.newTab().setText("广场").setIcon(R.mipmap.trends));
        tabLayout.addTab(tabLayout.newTab().setText("通讯").setIcon(R.mipmap.news));
        tabLayout.addTab(tabLayout.newTab().setText("个人").setIcon(R.mipmap.personal));

        setFragments(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragments(tab.getPosition());
               if(tab.getPosition()==0){
                   tabLayout.getTabAt(0).setIcon(R.mipmap.find1);
                   tabLayout.getTabAt(1).setIcon(R.mipmap.trends);
                   tabLayout.getTabAt(2).setIcon(R.mipmap.news);
                   tabLayout.getTabAt(3).setIcon(R.mipmap.personal);
                }
                if(tab.getPosition()==1){
                    tabLayout.getTabAt(0).setIcon(R.mipmap.find);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.trends1);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.news);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.personal);
                    seekPersonFragment.closeTime();
                }
                if(tab.getPosition()==2){
                    tabLayout.getTabAt(0).setIcon(R.mipmap.find);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.trends);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.news1);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.personal);
                    seekPersonFragment.closeTime();
                }
                if(tab.getPosition()==3){
                    tabLayout.getTabAt(0).setIcon(R.mipmap.find);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.trends);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.news);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.personal1);
                    seekPersonFragment.closeTime();
                }
            }
            public void onTabUnselected(TabLayout.Tab tab) { }
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        return view;
    }
    private void setFragments(int position){
        fm.beginTransaction().replace(R.id.tablayout_frame,
                fragments.get(position),"t"+position).commit();
    }
}
