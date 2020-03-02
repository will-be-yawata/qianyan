package com.example.administrator.langues.activity.Chat;

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

public abstract class Delete_friend_dialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private RelativeLayout delete_friend_btn, delete_cancel;

    public Delete_friend_dialog(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_delete_friend_dialog);

        delete_friend_btn = (RelativeLayout) findViewById(R.id.delete_friend_btn);
        delete_cancel = (RelativeLayout) findViewById(R.id.delete_cancel);

        delete_friend_btn.setOnClickListener(this);
        delete_cancel.setOnClickListener(this);

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
            case R.id.delete_friend_btn:
                deletefriend();
                this.cancel();
                break;
            case R.id.delete_cancel:
                btncancel();
                this.cancel();
                break;
        }
    }

    public abstract void deletefriend();
    public abstract void btncancel();



}
