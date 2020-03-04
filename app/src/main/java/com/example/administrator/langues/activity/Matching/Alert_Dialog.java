package com.example.administrator.langues.activity.Matching;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.langues.R;

public abstract class Alert_Dialog extends Dialog implements View.OnClickListener  {
    private Activity activity;

    private Button btn_confirm;
    private Button btn_cancel;

    public Alert_Dialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.alert_dialog);


        Window dialogWindow = this.getWindow();

        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_confirm = (Button) findViewById(R.id.alert_submit);
        btn_cancel=(Button) findViewById(R.id.alert_cancel);
        // 为按钮绑定点击事件监听器
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        this.setCancelable(true);
        setCanceledOnTouchOutside(true);//外部点击取消
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alert_cancel:
                btncancel();
                this.cancel();
                break;
            case R.id.alert_submit:
                btnconfirm();
                this.cancel();
                break;
        }
    }

    public  abstract  void btncancel();
    public  abstract  void btnconfirm();

}