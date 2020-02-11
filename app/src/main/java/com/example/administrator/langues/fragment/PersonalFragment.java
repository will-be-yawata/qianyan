package com.example.administrator.langues.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.AccountActivity;
import com.example.administrator.langues.activity.Deliver_detail_Activity;
import com.example.administrator.langues.activity.FeedbackActivity;
import com.example.administrator.langues.activity.My_ConcernActivity;
import com.example.administrator.langues.activity.My_DeliverActivity;
import com.example.administrator.langues.activity.SettingActivity;
import com.example.administrator.langues.activity.Sign_In_Activity;


/**
 * A simple {@link Fragment} subclass.
 */
/*

----------------个人界面-----------------------

 */
public class PersonalFragment extends Fragment {
    ImageButton report;//签到按钮
    TextView reportText;//文字“签到”
    ImageButton dope;//消息按钮
    RelativeLayout account;//我的账户
    RelativeLayout sign_in;//我的签到
    RelativeLayout conver;//兑换中心
    RelativeLayout my_deliver;//我的发布
    RelativeLayout my_concern;//我的关注
    RelativeLayout setting;//设置
    RelativeLayout feedback;//意见反馈



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_personal, container, false);

        //签到
        report= (ImageButton) view.findViewById(R.id.report);
        reportText= (TextView) view.findViewById(R.id.reportText);
        report.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//当按钮按下时
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.report));
                    reportText.setText("已签到");

                }
                /*else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.report1));
                }*/
                return false;
            }
        });

        //消息
        dope= (ImageButton) view.findViewById(R.id.dope);
        dope.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//当按钮按下时
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.dope));

                }
                /*else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.dope1));
                }*/
                return false;
            }
        });

        //我的账户
        account= (RelativeLayout) view.findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AccountActivity.class);
                startActivity(intent);
                //Toast.makeText(getActivity(),"功能尚未完善",Toast.LENGTH_SHORT).show();
            }
        });

        //我的签到
        sign_in= (RelativeLayout) view.findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Sign_In_Activity.class);
                startActivity(intent);
            }
        });
        //兑换中心
        conver= (RelativeLayout) view.findViewById(R.id.conver);
        conver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Deliver_detail_Activity.class);
                startActivity(intent);
            }
        });
        //我的发布
        my_deliver= (RelativeLayout) view.findViewById(R.id.my_deliver);
        my_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),My_DeliverActivity.class);
                startActivity(intent);
            }
        });
        //我的关注
        my_concern= (RelativeLayout) view.findViewById(R.id.my_concern);
        my_concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),My_ConcernActivity.class);
                startActivity(intent);
            }
        });
        //设置
        setting= (RelativeLayout) view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });
        //意见反馈
        feedback= (RelativeLayout) view.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FeedbackActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


}
