package com.example.administrator.langues.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ChatRecyclerView extends RecyclerView {
    private int startY=-1;
    private OnScrollListener onScrollListener;
    private static final int STATUS_REFRESHING=0;
    private static final int STATUS_READY=1;
    private int mCurrent=STATUS_READY;
    public ChatRecyclerView(@NonNull Context context) {
        this(context,null);
    }
    public ChatRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public ChatRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void refreshComplete(){
        mCurrent=STATUS_READY;
    }
    public boolean onTouchEvent(MotionEvent e) {
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY= (int) e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(mCurrent==STATUS_READY && onScrollListener.onTop()) {
                    int endY = (int) e.getY();
                    int dy = endY - startY;
                    if (dy > 0) {
                        mCurrent=STATUS_REFRESHING;
                        onScrollListener.doRefresh();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(e);
    }
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
    public interface OnScrollListener{
        //达到顶端
        boolean onTop();
        //执行刷新
        void doRefresh();
    }
}
