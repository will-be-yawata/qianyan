package com.example.administrator.langues.activity.MyPage.User_data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.adapter.AdapterAmount;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView mGridView;  //九宫格
    private String[] channelDec;
    private ArrayList<Top_up_Amount> channelList;
    private TextView amount_text;
    private TextView integral_text;
    private Button amount_btn;
    private int anum;//购买积分数


    ImageButton account_return;//返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();//初始化
        initChannelView(); //实现九宫格
    account_return.setOnClickListener(this);
    amount_btn.setOnClickListener(this);

    }
    private void init(){
        //返回按钮
        account_return=  findViewById(R.id.account_return);
        //显示金额
        amount_text=findViewById(R.id.amount_text);
        //确认支付
        amount_btn=findViewById(R.id.amount_btn);
        //积分数
        integral_text=findViewById(R.id.integral_Text);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.account_return://返回按钮
                finish();
                break;
            case R.id.amount_btn://确认支付
                String nums= (String) integral_text.getText();//原来积分数
                int num1=Integer.parseInt(nums) ;
                int num=num1+anum;
                String intenum=String.valueOf(num);
                integral_text.setText(intenum);

                Toast toast=Toast.makeText(getBaseContext(),"购买成功",Toast.LENGTH_SHORT);
                toast.show();

                break;

        }
    }

    private void initChannelView() {
        mGridView = (GridView)findViewById(R.id.integral_gridview);

        channelDec = new String[]{
                "50积分",
                "100积分",
                "150积分",
                "200积分",
                "300积分",
                "500积分",
        };

        channelList = new ArrayList<>();
        for(int i=0;i<channelDec.length;i++){
            Top_up_Amount channel = new Top_up_Amount();
            channel.setDec(channelDec[i]);
            channelList.add(channel);
        }

        mGridView.setAdapter(new AdapterAmount(channelList,this));
        //给九宫格设置监听器
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        amount_text.setText("5");
                        anum=50;
                        Toast.makeText(AccountActivity.this,"50积分",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        amount_text.setText("10");
                        anum=100;
                        Toast.makeText(AccountActivity.this,"100积分",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        amount_text.setText("15");
                        anum=150;
                        Toast.makeText(AccountActivity.this,"150积分",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        amount_text.setText("20");
                        anum=200;
                        Toast.makeText(AccountActivity.this,"200积分",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        amount_text.setText("30");
                        anum=300;
                        Toast.makeText(AccountActivity.this,"300积分",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        amount_text.setText("50");
                        anum=500;
                        Toast.makeText(AccountActivity.this,"500积分",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


}

