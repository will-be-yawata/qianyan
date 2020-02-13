package com.example.administrator.langues.activity.User_data;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.administrator.langues.activity.AccountActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class User_DataActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton return_btn;//返回按钮
    //弹出框
    RelativeLayout sex_dialog;
    RelativeLayout name_dialog;
    RelativeLayout date_dialog;
    RelativeLayout status_dialog;
    RelativeLayout address_dialog;
    RelativeLayout attribute_dialog;
    RelativeLayout integral_dialog;


    TextView sex_Text;//性别
    TextView integral_Text;//性别
    TextView name_Text;//昵称
    TextView birthday_Text;//生日
    TextView address_Text;//地区
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
        return_btn.setOnClickListener(this);
        //修改昵称
        name_dialog.setOnClickListener(this);
        //修改属性
        attribute_dialog.setOnClickListener(this);
        //修改积分
        integral_dialog.setOnClickListener(this);
        //修改性别
        sex_dialog.setOnClickListener(this);
        date_dialog.setOnClickListener(this);
        //地区选择
        address_dialog.setOnClickListener(this);
        //修改感情状况
        status_dialog.setOnClickListener(this);


    }
    private void init() {
        return_btn=findViewById(R.id.user_data_return);
        /*
        *TEXT
        * */
        sex_Text=findViewById(R.id.user_data_sex);
        name_Text=findViewById(R.id.user_data_name);
        birthday_Text=findViewById(R.id.user_data_birthday);
        status_Text=findViewById(R.id.user_data_status);
        address_Text=findViewById(R.id.user_data_address);
        integral_Text=findViewById(R.id.integral_Text);
        /*
        *DIALOG
        * */
        sex_dialog=findViewById(R.id.sex_dialog);
        name_dialog=findViewById(R.id.name_dialog);
        date_dialog=findViewById(R.id.date_dialog);
        status_dialog=findViewById(R.id.status_dialog);
        address_dialog=findViewById(R.id.address_dialog);
        attribute_dialog=findViewById(R.id.attribute_dialog);
        integral_dialog=findViewById(R.id.integral_dialog);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_data_return://返回
                finish();
                break;
            case R.id.name_dialog://修改昵称
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
                break;
            case R.id.sex_dialog://修改性别
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
                break;
            case R.id.date_dialog://修改生日
                calendar = Calendar.getInstance();
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
                                        .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.address_dialog://修改地区

                break;
            case R.id.status_dialog://修改感情状况
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
                break;
            case R.id.attribute_dialog://修改属性
                Intent att=new Intent(getBaseContext(),Member_DataActivity.class);
                startActivity(att);
                break;
            case R.id.integral_dialog:
                Intent inte=new Intent(getBaseContext(),AccountActivity.class);
                startActivity(inte);
                break;
        }

    }


}
