package com.example.administrator.langues.activity.MyPage.User_data;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.administrator.langues.R;

public abstract class Status_Dialog extends Dialog implements View.OnClickListener{

    private Activity activity;
    private RelativeLayout unmarried, married,none;

    public Status_Dialog(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_status_dialog);

        unmarried = (RelativeLayout) findViewById(R.id.unmarried);
        married = (RelativeLayout) findViewById(R.id.married);
        none = (RelativeLayout) findViewById(R.id.reject);

        unmarried.setOnClickListener(this);
        married.setOnClickListener(this);
        none.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unmarried:
                btnunmarried();
                this.cancel();
                break;
            case R.id.married:
                btnmarried();
                this.cancel();
                break;
            case R.id.reject:
                btnnone();
                this.cancel();
                break;
        }
    }

    public abstract void btnunmarried();
    public abstract void btnmarried();
    public abstract void btnnone();

}

