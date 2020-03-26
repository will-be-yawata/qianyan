package com.example.administrator.langues.activity.Matching;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.adapter.dialogueAdapter;
import com.example.administrator.langues.fragment.SeekFragment;

import java.util.ArrayList;
import java.util.List;

public class Dialogue_listActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView dialogue_list;
    private ArrayList<ListItem> mlist;
    private String[] list_names;
    private int[] images;
    private String[] nums;
    private ImageButton dialogue_return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_list);
        init();
        initListView();
        clickListener();
    }

    private void clickListener() {
        dialogue_return.setOnClickListener(this);
    }

    private void initListView() {
        list_names =new String[]{
                "鲁滨逊漂流",
                "大头儿子小头爸爸",
                "喜羊羊与灰太狼",
                "小羊肖恩",
                "熊出没",
                "红猫蓝兔七侠传"
        };
        images=new int[]{
          R.mipmap.lu,
          R.mipmap.datou,
          R.mipmap.xiyangyang,
          R.mipmap.xiaoyang,
          R.mipmap.xiongchumo,
          R.mipmap.hongmao
        };
        nums =new String[]{
          "121312",
          "45451",
          "46515",
          "64114",
          "8561",
          "7841",
        };
        mlist=new ArrayList<>();
        for(int i=0;i<list_names.length;i++){
            ListItem litem = new ListItem();
            litem.setList_name(list_names[i]);
            litem.setListImageResId(images[i]);
            litem.setListNum(nums[i]);
            mlist.add(litem);

        }
        dialogue_list.setAdapter(new dialogueAdapter(this,mlist));
        dialogue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }

    private void init() {
        dialogue_list=findViewById(R.id.dialogue_list);
        dialogue_return=findViewById(R.id.dialogue_return);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialogue_return:
                finish();
                break;
        }
    }
}
