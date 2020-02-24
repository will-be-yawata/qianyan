package com.example.administrator.langues.activity.Matching;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.langues.R;

public abstract class Matching_Dialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button situational_btn;
    private Button seek_btn;

    public Matching_Dialog(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 指定布局
            this.setContentView(R.layout.matching_dialog);
            initView();

            //设置弹出窗口背景透明
            this.getWindow().setBackgroundDrawableResource(R.color.transparent);

            Window dialogWindow = this.getWindow();
            WindowManager m = activity.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
            p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.8
            dialogWindow.setAttributes(p);

            // 根据id在布局中找到控件对象
            situational_btn = (Button) findViewById(R.id.matching_situational_btn);
            seek_btn=(Button) findViewById(R.id.matching_seek_btn);
            // 为按钮绑定点击事件监听器
            situational_btn.setOnClickListener(this);
            seek_btn.setOnClickListener(this);

            this.setCancelable(true);
            setCanceledOnTouchOutside(true);//外部点击取消

    }

    public void initView(){
        seek_btn=findViewById(R.id.matching_seek_btn);
        situational_btn=findViewById(R.id.matching_situational_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.matching_seek_btn:
                btnseek();
                this.cancel();
                break;
            case R.id.matching_situational_btn:
                btnsituational();
                this.cancel();
                break;
        }
    }

    protected abstract void btnsituational();

    protected abstract void btnseek();
}
