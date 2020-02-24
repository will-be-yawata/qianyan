package com.example.administrator.langues.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.langues.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefreshListView extends ListView {
    private View mHeaderView;
    private int mHeaderViewHeight;
    private View mFooterView;
    private int mFooterViewHeight;
    private static final int STATE_PULL_TO_REFRESH=0;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH=1;//松开刷新
    private static final int STATE_REFRESHING=2;//正在刷新
    int startY=-1;
    private int mCurrentState=STATE_PULL_TO_REFRESH;//当前状态，默认下拉刷新

    private TextView refresh_state,refresh_time;
    private ImageView refresh_arrow;
    private ProgressBar refresh_loading;
    private ProgressBar more_loading;

    private RotateAnimation animUp,animDown;

    private OnRefreshAndMoreListener onRefreshAndMoreListener;
    private OnScrollListener onScrollListener;

    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
        initView();
        listener();
    }

    private void initFooterView() {
        mFooterView=View.inflate(getContext(),R.layout.square_friend_more,null);
        addFooterView(mFooterView);
        mFooterView.measure(0,0);
        mFooterViewHeight=mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0,-mFooterViewHeight,0,0);
    }

    private void initHeaderView(){
        mHeaderView = View.inflate(getContext(), R.layout.square_friend_refresh, null);
        addHeaderView(mHeaderView);//给ListView添加头布局
        initArrowAnim();
        //隐藏头布局
        //获取当前头布局高度，然后设置负paddingTop，布局就会向上走
        mHeaderView.measure(0,0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY=(int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //如果正在刷新，什么都不做
                if(mCurrentState==STATE_REFRESHING) break;
                if(startY==-1){
                    startY=(int)ev.getY();
                }
                int endY=(int)ev.getY();
                int dy=endY-startY;
                int firstVisiblePosition=getFirstVisiblePosition();//当前显示的第一个item的位置
                if(dy>0&&firstVisiblePosition==0){
                    //下拉动作&当前在ListView的顶部
                    int padding = -mHeaderViewHeight+dy;
                    mHeaderView.setPadding(0,padding,0,0);
                    if(padding>0 && mCurrentState!=STATE_RELEASE_TO_REFRESH){
                        //切换到松开刷新状态
                        mCurrentState=STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    }else if(padding <=0 && mCurrentState!=STATE_PULL_TO_REFRESH){
                        //切换到下拉刷新状态
                        mCurrentState=STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    //通过修改padding来设置当前刷新控件的最新位置
                    mHeaderView.setPadding(0,padding,0,0);
                    return true;//消费此事件，处理下拉刷新控件的滑动，不需要ListView原生效果参与
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;//起始坐标归0
                if(mCurrentState==STATE_RELEASE_TO_REFRESH){
                    //切换成正在刷新
                    mCurrentState=STATE_REFRESHING;
                    mHeaderView.setPadding(0,0,0,0);
                    refreshState();
                }else if(mCurrentState == STATE_PULL_TO_REFRESH){
                    //隐藏刷新控件
                    mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void initView(){
        refresh_state=mHeaderView.findViewById(R.id.refresh_state);
        refresh_arrow=mHeaderView.findViewById(R.id.refresh_arrow);
        refresh_loading=mHeaderView.findViewById(R.id.refresh_loading);
        refresh_time=mHeaderView.findViewById(R.id.refresh_time);
        more_loading=mFooterView.findViewById(R.id.more_loading);
        more_loading.setVisibility(View.VISIBLE);
    }
    private void initArrowAnim(){
        animUp=new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);//保持住动画结束的状态

        animDown=new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);//保持住动画结束的状态
    }
    //根据当前状态刷新界面
    private void refreshState(){
        switch(mCurrentState){
            case STATE_PULL_TO_REFRESH:
                refresh_state.setText("下拉刷新");
                refresh_loading.setVisibility(View.INVISIBLE);
                refresh_arrow.setVisibility(View.VISIBLE);

                refresh_arrow.startAnimation(animDown);
                break;
            case STATE_RELEASE_TO_REFRESH:
                refresh_state.setText("松开刷新");
                refresh_loading.setVisibility(View.INVISIBLE);
                refresh_arrow.setVisibility(View.VISIBLE);

                refresh_arrow.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                refresh_state.setText("正在刷新...");
                refresh_arrow.clearAnimation();
                refresh_loading.setVisibility(View.VISIBLE);
                refresh_arrow.setVisibility(View.INVISIBLE);
                if(onRefreshAndMoreListener!=null) onRefreshAndMoreListener.onRefresh();
                break;
        }
    }
    //刷新结束，隐藏控件
    public void onRefreshComplete(){
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
        mCurrentState=STATE_PULL_TO_REFRESH;
        refreshState();
        setRefreshTime();
    }
    //加载结束，隐藏控件
    public void onMoreComplete(){
        mFooterView.setPadding(0,-mFooterViewHeight,0,0);
    }
    private void listener(){
        onScrollListener=new OnScrollListener() {
            //滑动状态发生变化时调用
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {//空闲状态
                    int lastVisiblePosition=getLastVisiblePosition();//当前显示的最后一个item的位置
                    if(lastVisiblePosition==getCount()-1){
                        mFooterView.setPadding(0,0,0,0);
                        setSelection(getCount()-1);//显示在最后一个item的位置(展示加载中布局)
                        onRefreshAndMoreListener.onMore();
                    }
                }
            }
            //每滑动一像素就会调用
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {}
        };
        setOnScrollListener(onScrollListener);
    }
    private void setRefreshTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(new Date());
        refresh_time.setText(time);
    }
    //设置刷新回调监听
    public void setOnRefreshAndMoreListener(OnRefreshAndMoreListener listener){
        onRefreshAndMoreListener=listener;
    }
    public interface OnRefreshAndMoreListener{
        //下拉刷新的回调
        void onRefresh();
        //上拉加载的回调
        void onMore();
    }
}