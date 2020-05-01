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
//import com.example.administrator.langues.activity.Square.ReleaseActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFragment extends Fragment {
    private static final int PUBLISH_DYNAMIC=1572;
    private TabLayout square_tab;
    private ViewPager square_viewpager;
    private List<String> titles;
    private List<Fragment> fragments;
    private ImageButton deliver;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square, container, false);
        init(view);
        listener();
        return view;
    }
    private void init(View view){
        deliver=view.findViewById(R.id.deliver);
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(getContext(),ReleaseActivity.class);
                Intent intent=new Intent(getContext(), Deliver_textActivity.class);
                startActivity(intent);

            }
        });
        square_tab=view.findViewById(R.id.square_tab);
        square_viewpager=view.findViewById(R.id.square_viewpager);
        initFragments();
    }

    private void initFragments(){
        fragments = new ArrayList<>();

        SquareFriendFragment squareFriendFragment =new SquareFriendFragment();
        fragments.add(squareFriendFragment);

        SquareFindFragment squareFindFragment =new SquareFindFragment();
        fragments.add(squareFindFragment);
        titles = new ArrayList<>();
        titles.add("动态");
        titles.add("广场");
        squareFindAdapter adapter = new squareFindAdapter(getChildFragmentManager(), fragments, titles);
        square_viewpager.setAdapter(adapter);
        square_tab.setupWithViewPager(square_viewpager);
        square_viewpager.setCurrentItem(0);
    }
    private void listener(){
//        deliver.setOnClickListener(v -> {
//            Intent intent=new Intent(getContext(),Deliver_textActivity.class);
//            startActivityForResult(intent,PUBLISH_DYNAMIC);
//        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PUBLISH_DYNAMIC && resultCode == RESULT_OK) {
            square_viewpager.setCurrentItem(0);
        }
    }
    public class squareFindAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;
        public squareFindAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
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
