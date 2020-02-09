package com.example.administrator.langues.activity.User_data;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.langues.R;

public class User_DataActivity extends AppCompatActivity {

    ImageButton return_btn;
    RelativeLayout sex_dialog;
    TextView sex_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        init();
        //返回
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //修改性别
        sex_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new User_Dialog(User_DataActivity.this){

                   @Override
                   public void btnPickBySelect() {
                       sex_Text.setText("女");

                   }

                   @Override
                   public void btnPickByTake() {
                       sex_Text.setText("男");

                   }
               }.show();
            }
        });

    }
    private void init() {
        return_btn=findViewById(R.id.user_data_return);
        sex_dialog=findViewById(R.id.sex_dialog);
        sex_Text=findViewById(R.id.user_data_sex);

    }


}
