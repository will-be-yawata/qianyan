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


import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Matching.adapter.dialogueAdapter;
import com.example.administrator.langues.fragment.SeekFragment;

import java.util.ArrayList;
import java.util.List;

import entry.Scence;
import util.Scence_Talk_Tool;

public class Dialogue_listActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView dialogue_list;
    private ArrayList<ListItem> mlist;
    private ArrayList<String> list_names=new ArrayList<>();
    private int[] images;
    private ArrayList<String> nums=new ArrayList<>();
    private ImageButton dialogue_return;
    private Scence_Talk_Tool scence_talk_tool;
    private ArrayList<Scence> scences_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        scence_talk_tool=new Scence_Talk_Tool();

        scence_talk_tool.getScence(0, 10, new Scence_Talk_Tool.getScenceCallback() {
            @Override
            public void onSuccess(ArrayList<Scence> scences) {
                scences_list=scences;
                init();
                initListView();
                clickListener();
            }

            @Override
            public void onError() {

            }
        });
        setContentView(R.layout.activity_dialogue_list);

    }

    private void clickListener() {
        dialogue_return.setOnClickListener(this);
    }

    private void initListView() {

//        list_names =new String[]{
//                "鲁滨逊漂流",
//                "大头儿子小头爸爸",
//                "喜羊羊与灰太狼",
//                "小羊肖恩",
//                "熊出没",
//                "红猫蓝兔七侠传"
//        };

        for(Scence s:scences_list){
            list_names.add(s.getMedia_name());
            nums.add(s.getMid()+"");
        }

        images=new int[]{
          R.mipmap.lu,
          R.mipmap.datou,
          R.mipmap.xiyangyang,
          R.mipmap.xiaoyang,
          R.mipmap.xiongchumo,
          R.mipmap.hongmao
        };


        mlist=new ArrayList<>();
        for(int i=0;i<list_names.size();i++){
            ListItem litem = new ListItem();
            litem.setList_name(list_names.get(i));
            litem.setListImageResId(images[i]);
            litem.setListNum(nums.get(i));
            mlist.add(litem);

        }
        dialogue_list.setAdapter(new dialogueAdapter(this,mlist));
        dialogue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("scence_test",position+"");
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
