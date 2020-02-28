package com.example.administrator.langues.activity.User_data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.langues.R;

public class Member_DataActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout return_btn;
    ImageButton member_set_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_data);
        init();
        return_btn.setOnClickListener(this);
        member_set_btn.setOnClickListener(this);
    }

    private void init() {
        return_btn=findViewById(R.id.member_return);
        member_set_btn=findViewById(R.id.member_set_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.member_return:
                finish();
                break;
            case R.id.member_set_btn:
                 new Member_Set_Dialog(Member_DataActivity.this) {
                     @Override
                     public void btn1member() {//续费会员
                         Toast toast=Toast.makeText(getContext(),"你点击了续费会员",Toast.LENGTH_SHORT);
                         toast.show();
                     }
                     @Override
                     public void btn2member() {//会员特权
                         Toast toast=Toast.makeText(getContext(),"你点击了会员特权",Toast.LENGTH_SHORT);
                         toast.show();
                     }
                 }.show();
                break;
        }

    }
}
