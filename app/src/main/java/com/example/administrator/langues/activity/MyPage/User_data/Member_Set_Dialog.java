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

public abstract class Member_Set_Dialog extends Dialog implements View.OnClickListener{
    private Activity activity;
    private RelativeLayout member_btn1, member_btn2;

    public Member_Set_Dialog(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_member_set_dialog);

        member_btn1 = (RelativeLayout) findViewById(R.id.member_btn1);
        member_btn2 = (RelativeLayout) findViewById(R.id.member_btn2);

        member_btn1.setOnClickListener(this);
        member_btn2.setOnClickListener(this);

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
            case R.id.member_btn1:
                btn1member();
                this.cancel();
                break;
            case R.id.member_btn2:
                btn2member();
                this.cancel();
                break;
        }
    }

    public abstract void btn1member();
    public abstract void btn2member();

}