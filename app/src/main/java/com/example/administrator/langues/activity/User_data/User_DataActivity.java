package com.example.administrator.langues.activity.User_data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;

public class User_DataActivity extends AppCompatActivity {

    ImageButton return_btn;
    RelativeLayout sex_dialog;
    RelativeLayout name_dialog;
    TextView sex_Text;//性别
    TextView name_Text;//昵称
    EditText exit_name_text;//修改的昵称


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
        //修改昵称
        name_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Name_Dialog(User_DataActivity.this){

                    @Override
                    public void btncancel() {
                       cancel();
                    }

                    @Override
                    public void btnsave() {
                        name_Text.setText(getName());
                    }


                }.show();
            }
        });
        //修改性别
        sex_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Sex_Dialog(User_DataActivity.this){

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
        name_dialog=findViewById(R.id.name_dialog);
        sex_Text=findViewById(R.id.user_data_sex);
        name_Text=findViewById(R.id.user_data_name);
        exit_name_text=findViewById(R.id.exit_name_text);


    }


}
