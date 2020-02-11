package com.example.administrator.langues.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Deliver_textActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFragment extends Fragment {
    private TabLayout square_tab;
    private ViewPager square_viewpager;
    private List<String> titles;
    private List<Fragment> fragments;
    private ImageButton deliver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_square, container, false);
        deliver=view.findViewById(R.id.deliver);
        deliver.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(),Deliver_textActivity.class);
            startActivity(intent);
        });
        square_tab=view.findViewById(R.id.square_tab);
        square_viewpager=view.findViewById(R.id.square_viewpager);

        fragments = new ArrayList<>();
        squareFindFragment squareFindFragment=new squareFindFragment();
        fragments.add(squareFindFragment);
        squareFriendFragment squareFriendFragment=new squareFriendFragment();
        fragments.add(squareFriendFragment);
        titles = new ArrayList<>();
        titles.add("广场");
        titles.add("动态");
        squareFindAdapter adapter = new squareFindAdapter(getChildFragmentManager(), fragments, titles);
        square_viewpager.setAdapter(adapter);
        square_tab.setupWithViewPager(square_viewpager);
        square_viewpager.setCurrentItem(0);
        return view;
    }
    public class squareFindAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;
        public squareFindAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
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
