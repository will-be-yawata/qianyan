package com.example.administrator.langues.activity.MyPage.User_data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.administrator.langues.R;

public class Personal_cardActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton personal_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_card);
        initView();
        clickListener();

    }

    private void clickListener() {
        personal_return.setOnClickListener(this);
    }

    private void initView() {
        personal_return=findViewById(R.id.personal_return);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.personal_return:
                finish();
                break;

        }

    }
}
