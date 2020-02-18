package com.example.administrator.langues.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Seek_loadingActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeekFragment extends Fragment {
    private static int START_ANIMATION=0;
    private Button seek_btn;
    private ImageView image1,image2,image3,image4,image5,image6,image7,image8,image9,image10,image11;
    private Animation animation1,animation2,animation3,animation4, animation5,
            animation6,animation7,animation8,animation9,animation10,animation11;
    private  Timer timer;
    private Handler mHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_seek, container, false);

        init(view);
        startTime();
        listener();

        return view;
    }

    private void listener() {
        seek_btn.setOnClickListener(view1 -> {
            closeTime();
            Intent i=new Intent(getContext(),Seek_loadingActivity.class);
            startActivity(i);
        });
    }
    @SuppressLint("HandlerLeak")
    private void init(View view){
        seek_btn=view.findViewById(R.id.seek_btn);

        image1=view.findViewById(R.id.image1);
        image2=view.findViewById(R.id.image2);
        image3=view.findViewById(R.id.image3);
        image4=view.findViewById(R.id.image4);
        image5=view.findViewById(R.id.image5);
        image6=view.findViewById(R.id.image6);
        image7=view.findViewById(R.id.image7);
        image8=view.findViewById(R.id.image8);
        image9=view.findViewById(R.id.image9);
        image10=view.findViewById(R.id.image10);
        image11=view.findViewById(R.id.image11);

        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        animation3 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale);
        animation4 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
        animation5 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_right);
        animation6 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
        animation7 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        animation8 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale);
        animation9 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
        animation10 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        animation11 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_bounce);
        timer=new Timer();

        mHandler=new Handler(){
            public void handleMessage(Message msg) {
                if(msg.what==START_ANIMATION){
                    image1.clearAnimation();
                    image2.clearAnimation();
                    image3.clearAnimation();
                    image4.clearAnimation();
                    image5.clearAnimation();
                    image6.clearAnimation();
                    image7.clearAnimation();
                    image8.clearAnimation();
                    image9.clearAnimation();
                    image10.clearAnimation();
                    image11.clearAnimation();

                    image1.startAnimation(animation1);
                    image2.startAnimation(animation2);
                    image3.startAnimation(animation3);
                    image4.startAnimation(animation4);
                    image5.startAnimation(animation5);
                    image6.startAnimation(animation6);
                    image7.startAnimation(animation7);
                    image8.startAnimation(animation8);
                    image9.startAnimation(animation9);
                    image10.startAnimation(animation10);
                    image11.startAnimation(animation11);
                }
            }
        };
    }
    public void closeTime(){ timer.cancel(); }
    private  void startTime(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message m=new Message();
                m.what=START_ANIMATION;
                mHandler.sendMessage(m);
            }
        },0,4000);
    }
}
