package com.example.administrator.langues.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.jay.ui.PhotoPickerActivity;

import java.util.ArrayList;
import java.util.List;

import entry.User;
import util.core.DynamicOperation;

public class Deliver_textActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btn2;
    private ImageButton deliver_return;
    private boolean isMultiSelect;
    private int defaultMaxCount = 5;
    private ArrayList<String> results;
    /**显示图片的GridView*/
    private GridView gridview;
    /**文件夹下所有图片的bitmap*/
    private List<Bitmap> listpath;
    /**文件夹下图片的真实路径*/
    private String scanpath;
    /**显示图片的适配器*/
    private Photodaapter adapter;

    private int mScreenHeight;
    private int mScreenWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_text);
        hidBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //返回按钮
        deliver_return=findViewById(R.id.deliver_return);
        deliver_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn2 = (FloatingActionButton) findViewById(R.id.btn2);

        btn2.setOnClickListener(this);
        gridview=findViewById(R.id.gridview);
        listpath = new ArrayList<>();




/*获取屏幕宽度*/
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();


        //以下均为测试代码，可删除
        Button publish=findViewById(R.id.deliver_btn);
        AutoCompleteTextView text=findViewById(R.id.autoCompleteTextView2);
        publish.setOnClickListener(v->{
            for (int i = 0; i < results.size(); i++) {
                Log.i("mData",results.get(i));
            }
            User.getInstance().setPhone("15728283804");
            (new DynamicOperation()).publishDynamic(text.getText().toString(), results, s -> {
                if(s.equals("1")){
                    Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG).show();
                }
            });
        });


    }
    public  void hidBar(){
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }





    class VIewHolder {
        ImageView iv;
    }
    public class Photodaapter extends BaseAdapter {
        private List<Bitmap> mlist;
        private Context mcontext;
        private LayoutInflater minflater;

        public Photodaapter(List<Bitmap> list, Context context) {
            super();
            this.mlist = list;
            this.mcontext = context;
            this.minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            VIewHolder vh;
            if (convertView == null) {
                vh = new VIewHolder();
                convertView = minflater.inflate(R.layout.square_find_photo, null);
                vh.iv = (ImageView) convertView.findViewById(R.id.square_photo);
                convertView.setTag(vh);
            } else {
                vh = (VIewHolder) convertView.getTag();
            }
            ViewGroup.LayoutParams para = vh.iv.getLayoutParams();
            para.width = (mScreenWidth-20)/3;//一屏显示3列
            para.height=para.width;
            vh.iv.setLayoutParams(para);
            vh.iv.setImageBitmap(mlist.get(position));
            return convertView;
        }
    }






    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Deliver_textActivity.this, PhotoPickerActivity.class);

        switch (v.getId()) {

            case R.id.btn2://多选
                isMultiSelect = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean(PhotoPickerActivity.IS_MULTI_SELECT, true);


                defaultMaxCount = 9;

                bundle.putInt(PhotoPickerActivity.MAX_SELECT_SIZE, defaultMaxCount);
                intent.putExtras(bundle);
                break;
        }
        startActivityForResult(intent,1001);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            if (isMultiSelect) {
                //多选
                results = data.getStringArrayListExtra(PhotoPickerActivity.SELECT_RESULTS_ARRAY);

                for(int i=0;i<results.size();i++){
                    scanpath = results.get(i);
                    Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
                    //Log.i("bitmap","bitmap="+results.get(i));
                    if(bitmap!=null) {
                        listpath.add(bitmap);
                       // Log.i("listpath","listpath="+listpath);
                    }
                }
                adapter = new Photodaapter(listpath, this);
                gridview.setAdapter(adapter);
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                    {
                        if( listpath.size() >= 9) { //第一张为默认图片
                            Toast.makeText(Deliver_textActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();


                        }

                        else {
                            dialog(position);
                            //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                            //      Toast.LENGTH_SHORT).show();
                        }
                    }
                });




                if (results == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < results.size(); i++) {
                    sb.append(i+1).append('：').append(results.get(i)).append("\n");
                }


            }
        }
    }
    public void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Deliver_textActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listpath.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
