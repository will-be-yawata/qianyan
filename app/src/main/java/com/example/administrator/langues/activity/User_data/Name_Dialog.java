package com.example.administrator.langues.activity.User_data;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.langues.R;

public abstract class Name_Dialog extends Dialog  implements View.OnClickListener {
    private Activity activity;

    private Button btn_save;
    private Button btn_cancel;

    public EditText text_name;


    public Name_Dialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.exit_name_dialog);

        text_name = (EditText) findViewById(R.id.exit_name_text);

        Window dialogWindow = this.getWindow();

        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_save = (Button) findViewById(R.id.exit_name_submit);
        btn_cancel=(Button) findViewById(R.id.exit_name_cancel);
        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        this.setCancelable(true);
        setCanceledOnTouchOutside(true);//外部点击取消
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_name_cancel:
                btncancel();
                this.cancel();
                break;
            case R.id.exit_name_submit:
                getName();
                btnsave();
                this.cancel();
                break;
        }
    }
    public  String getName(){

        String name= String.valueOf(text_name.getText());//获取输入的昵称

        return name;
    }
    public  abstract  void btncancel();
    public  abstract  void btnsave();

}
