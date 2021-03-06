package com.example.administrator.langues.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.Matching_Dialog;
import com.example.administrator.langues.activity.MyPage.MyCourse.CourseActivity;
import com.example.administrator.langues.activity.MyPage.User_data.AccountActivity;

import com.example.administrator.langues.activity.MyPage.FeedbackActivity;
import com.example.administrator.langues.activity.MyPage.My_ConcernActivity;


import com.example.administrator.langues.activity.Square.Deliver_detail_Activity;

import com.example.administrator.langues.activity.Square.My_DeliverActivity;
import com.example.administrator.langues.activity.MyPage.SettingActivity;
import com.example.administrator.langues.activity.Sign_In_Activity;
import com.example.administrator.langues.activity.MyPage.User_data.Member_DataActivity;
import com.example.administrator.langues.activity.MyPage.User_data.User_DataActivity;

import org.xutils.x;

import entry.User;
import util.Url;


/**
 * A simple {@link Fragment} subclass.
 */
/*

----------------个人界面-----------------------

 */
public class PersonalFragment extends Fragment {
    ImageButton report;//签到按钮
    TextView reportText;//文字“签到”
    TextView memberText;//用户属性
    ImageButton dope;//消息按钮
    ImageButton user_data;//资料按钮
    RelativeLayout account;//我的账户
    RelativeLayout course;//我的课程
    RelativeLayout sign_in;//我的签到
    RelativeLayout conver;//兑换中心
    RelativeLayout my_deliver;//我的发布
    RelativeLayout my_concern;//我的关注
    RelativeLayout setting;//设置
    RelativeLayout feedback;//意见反馈

    ImageView userimg;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal, container, false);
        userimg=view.findViewById(R.id.Per_user_pho);
        x.image().bind(userimg, Url.USER_IMG+ User.getInstance().getImg());
        //个人资料
        user_data=view.findViewById(R.id.Per_user_pho);
        user_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),User_DataActivity.class);
                startActivity(intent);
                            }
        });
        //用户属性
        memberText=view.findViewById(R.id.member);
        memberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),Member_DataActivity.class);
                startActivity(intent);
            }
        });
        //签到
        report= view.findViewById(R.id.report);
        reportText= view.findViewById(R.id.reportText);
        report.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){//当按钮按下时
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.report));
                reportText.setText("已签到");
            }
            /*else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.report1));
            }*/
            return false;
        });
        //我的课程
        course=view.findViewById(R.id.my_course);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CourseActivity.class);
                startActivity(intent);


            }
        });
        //消息
        dope= view.findViewById(R.id.dope);
        dope.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){//当按钮按下时
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.dope));
            }
            /*else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.dope1));
            }*/
            return false;
        });
        //我的账户
        account= view.findViewById(R.id.account);
        account.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),AccountActivity.class);
            startActivity(intent);
            //Toast.makeText(getActivity(),"功能尚未完善",Toast.LENGTH_SHORT).show();
        });
        //我的签到
        sign_in= view.findViewById(R.id.sign_in);
        sign_in.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),Sign_In_Activity.class);
            startActivity(intent);
        });
        //兑换中心
        conver= view.findViewById(R.id.conver);
        conver.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),Deliver_detail_Activity.class);
            startActivity(intent);
        });
        //我的发布
        my_deliver= view.findViewById(R.id.my_deliver);
        my_deliver.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),My_DeliverActivity.class);
            startActivity(intent);
        });
        //我的关注
        my_concern= view.findViewById(R.id.my_concern);
        my_concern.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),My_ConcernActivity.class);
            startActivity(intent);
        });
        //设置
        setting= view.findViewById(R.id.setting);
        setting.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),SettingActivity.class);
            startActivity(intent);
        });
        //意见反馈
        feedback= view.findViewById(R.id.feedback);
        feedback.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),FeedbackActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
