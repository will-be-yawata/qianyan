package com.example.administrator.langues.fragment;


import android.animation.ObjectAnimator;
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
    ImageView image1,image2,image3,image4,image5,image6,image7,image8,image9,image10,image11;
    private Animation animation1 = null;
    private Animation animation2 = null;
    private Animation animation3 = null;
    private Animation animation4 = null;
    private Animation animation5 = null;
    private Animation animation6 = null;
    private Animation animation7 = null;
    private Animation animation8 = null;
    private Animation animation9 = null;
    private Animation animation10 = null;
    private Animation animation11 = null;
    private ObjectAnimator ot=null;
    private  Timer timer;
    private SeekFragment that=this;
    private Handler mHandler=new Handler(){
        @Override
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
//                    Log.i("12","---------ags");
                //that.getActivity().runOnUiThread(()->Toast.makeText(getContext(),"123",Toast.LENGTH_SHORT).show());
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



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view=inflater.inflate(R.layout.fragment_seek, container, false);

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
            startTime();

            seek_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(),Seek_loadingActivity.class);
                    startActivity(i);
                }
            });



            return view;
        }
public void closeTime(){
            timer.cancel();

}
public  void startTime(){
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
