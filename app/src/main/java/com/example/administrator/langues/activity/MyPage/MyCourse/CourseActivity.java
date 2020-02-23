package com.example.administrator.langues.activity.MyPage.MyCourse;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.administrator.langues.R;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout course_tab;
    private ViewPager course_viewpager;
    private List<String> titles;
    private List<Fragment> fragments;

    private ImageButton return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        init();
        createTab();
        return_btn.setOnClickListener(this);

    }

    private void createTab() {
        fragments=new ArrayList<>();
        Recently_readFragment recently_readFragment=new Recently_readFragment();
        fragments.add(recently_readFragment);
        Additional_coursesFragment additional_coursesFragment=new Additional_coursesFragment();
        fragments.add(additional_coursesFragment);
        Downloaded_coursesFragment downloaded_coursesFragment=new Downloaded_coursesFragment();
        fragments.add(downloaded_coursesFragment);
        titles=new ArrayList<>();
        titles.add("最近阅读课程");
        titles.add("添加的课程");
        titles.add("下载的课程");
        CourseAdapter courseAdapter=new CourseAdapter(getSupportFragmentManager(),fragments,titles);
        course_viewpager.setAdapter(courseAdapter);
        course_tab.setupWithViewPager(course_viewpager);
        course_viewpager.setCurrentItem(0);
    }

    private void init() {
        course_tab=findViewById(R.id.course_tab);
        course_viewpager=findViewById(R.id.course_viewpager);
        return_btn=findViewById(R.id.course_return);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.course_return:
                finish();
                break;
        }
    }


    public class CourseAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;
        public CourseAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
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
