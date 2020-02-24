package com.example.administrator.langues.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.langues.R;

import org.xutils.common.Callback;
import org.xutils.x;

public class BackgroundForSquare extends ListView {
    private View mSquareBg;
    private int mSquareBgHeight;
    private ImageView square_bg;
    private int fixed_height=38*2;
    public BackgroundForSquare(Context context) {
        this(context,null);
    }

    public BackgroundForSquare(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BackgroundForSquare(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mSquareBg=View.inflate(getContext(),R.layout.square_bg,null);
        x.image().bind(mSquareBg.findViewById(R.id.square_bg), "http://119.23.216.103/qianyan/public/static/images/bg.jpg", new Callback.CommonCallback<Drawable>() {
            public void onSuccess(Drawable result) {
                mSquareBg.measure(0,0);
                mSquareBgHeight=mSquareBg.getMeasuredHeight();
                mSquareBg.setPadding(0,fixed_height-mSquareBgHeight,0,0);
            }
            public void onError(Throwable ex, boolean isOnCallback) {}
            public void onCancelled(CancelledException cex) {}
            public void onFinished() {}
        });
        square_bg=mSquareBg.findViewById(R.id.square_bg);
        addHeaderView(mSquareBg);//给ListView添加头布局
        setAdapter(new NullAdapter());
    }
    private int startY=-1;
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endY=(int)ev.getY();
                int dy=endY-startY;
                int firstVisiblePosition=getFirstVisiblePosition();
                if(dy>= getScaleHeight()-fixed_height) break;
                if(dy>0 && firstVisiblePosition==0){
                    int padding = fixed_height-mSquareBgHeight+dy;
                    mSquareBg.setPadding(0,padding,0,0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mSquareBg.setPadding(0,fixed_height-mSquareBgHeight,0,0);
                break;
        }
        return super.onTouchEvent(ev);
    }
    private int getScaleHeight(){
        int imgWidth=square_bg.getMeasuredWidth();//图片的缩放宽度
        int realWidth=square_bg.getDrawable().getBounds().width();//图片的真实宽度
        int realHeight=square_bg.getDrawable().getBounds().height();//图片的真实高度
        return imgWidth*realHeight/realWidth;//图片的缩放高度
    }
    class NullAdapter extends BaseAdapter{
        public int getCount() {
            return 0;
        }
        public Object getItem(int i) {
            return null;
        }
        public long getItemId(int i) {
            return 0;
        }
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
