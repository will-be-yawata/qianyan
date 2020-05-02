package com.example.administrator.langues.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.langues.R;

public class FriendListView extends ListView {
    private View mHeaderView;
    private int mHeaderViewHeight;
    private TextView friend_message;
    private TextView friend_invited;
    private static final int STATE_VISIBLE=0;
    private static final int STATE_GONE=1;
    private int mCurrentState=STATE_GONE;
    private int startY=-1;
    private int padding=-1;
    public FriendListView(Context context) {
        this(context,null);
    }

    public FriendListView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public FriendListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initHeaderView();
    }
//    private void initHeaderView() {
//        mHeaderView=View.inflate(getContext(),R.layout.friend_listview,null);
//        addHeaderView(mHeaderView);
//        mHeaderView.measure(0,0);
//        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
//        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
//        friend_message=mHeaderView.findViewById(R.id.friend_message);
//        friend_invited=mHeaderView.findViewById(R.id.friend_invited);
//    }
//    public void setBubbles(int invited,int message){
//        if(message>0)
//            friend_message.setText(message);
//        else
//            friend_message.setVisibility(View.INVISIBLE);
//        if(invited>0)
//            friend_invited.setText(invited);
//        else
//            friend_invited.setVisibility(View.INVISIBLE);
//    }
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startY=(int)ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if(startY==-1){
//                    startY=(int)ev.getY();
//                }
//                int endY=(int)ev.getY();
//                int dy=endY-startY;
//                int firstVisiblePosition=getFirstVisiblePosition();
//                if(firstVisiblePosition==0){
//                    if(dy>0 && mCurrentState==STATE_GONE) padding=-mHeaderViewHeight;
//                    else if(dy>0 && mCurrentState==STATE_VISIBLE) padding=0;
//                    else if(dy<0&&mCurrentState==STATE_VISIBLE) padding=0;
//                    padding +=dy;
//                    mHeaderView.setPadding(0,padding,0,0);
//                    if(padding>0){
//                        mCurrentState=STATE_VISIBLE;
//                    }else{
//                        mCurrentState=STATE_GONE;
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                startY=-1;
//                if(mCurrentState==STATE_GONE) {
//                    padding=-mHeaderViewHeight;
//                }else {
//                    padding=0;
//                }
//                mHeaderView.setPadding(0, padding, 0, 0);
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }
}
