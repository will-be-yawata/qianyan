package com.example.administrator.langues.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.administrator.langues.R;
import com.example.administrator.langues.SignDate;

public class Sign_In_Activity extends AppCompatActivity {
    private SignDate signDate;
    ImageButton sign_in_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in_);
        signDate = findViewById(R.id.signDate);
        signDate.setOnSignedSuccess(() -> Log.e("wqf","Success"));

        sign_in_return= findViewById(R.id.sign_in_return);
        sign_in_return.setOnClickListener(v -> finish());
    }
}
