package com.example.administrator.langues.activity.User_data;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;

import java.util.Calendar;

public class User_DataActivity extends AppCompatActivity {

    ImageButton return_btn;//返回按钮
    //弹出框
    RelativeLayout sex_dialog;
    RelativeLayout name_dialog;
    RelativeLayout date_dialog;
    RelativeLayout status_dialog;


    TextView sex_Text;//性别
    TextView name_Text;//昵称
    TextView birthday_Text;//生日
    TextView status_Text;//感情状况

    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;


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
        //修改生日
        calendar = Calendar.getInstance();
        date_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(User_DataActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                birthday_Text.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //修改感情状况
        status_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Status_Dialog(User_DataActivity.this){

                    @Override
                    public void btnunmarried() {
                        status_Text.setText("单身");
                    }

                    @Override
                    public void btnmarried() {
                        status_Text.setText("已婚");

                    }

                    @Override
                    public void btnnone() {
                        status_Text.setText("保密");

                    }
                }.show();

            }
        });

    }
    private void init() {
        return_btn=findViewById(R.id.user_data_return);

        sex_Text=findViewById(R.id.user_data_sex);
        name_Text=findViewById(R.id.user_data_name);
        birthday_Text=findViewById(R.id.user_data_birthday);
        status_Text=findViewById(R.id.user_data_status);


        sex_dialog=findViewById(R.id.sex_dialog);
        name_dialog=findViewById(R.id.name_dialog);
        date_dialog=findViewById(R.id.date_dialog);
        status_dialog=findViewById(R.id.status_dialog);

    }


}
