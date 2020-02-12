package com.example.administrator.langues.activity.User_data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.langues.R;

public class Member_DataActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_data);
        init();
        return_btn.setOnClickListener(this);
    }

    private void init() {
        return_btn=findViewById(R.id.member_return);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.member_return:
                finish();
                break;
        }

    }
}
