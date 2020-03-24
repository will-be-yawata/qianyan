package com.example.administrator.langues.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.langues.R;
import com.example.administrator.langues.fragment.SquareFragment;
import com.jay.ui.PhotoPickerActivity;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.BaseResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.activity.MediaActivity;
import cn.finalteam.rxgalleryfinal.utils.PermissionCheckUtils;
import entry.User;
import util.core.DynamicOperation;

public class Deliver_textActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private ImageButton btn2;
    private Button send_btn;
    private ImageButton deliver_return;
    private List<MediaBean> selected_item;
    private ArrayList ready_to_publish;//准备发布的图片集合
    private EditText editText_content;
    private boolean isMultiSelect;
    private String kind;//动态的类型
    private int defaultMaxCount = 9;
    private int current_select_count;
    private ArrayList<String> results;
    private RxGalleryFinal rxGalleryFinal;
    private RadioGroup group;

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
        setContentView(R.layout.activity_release);

        selected_item=new ArrayList<>();
//        rxGalleryFinal=RxGalleryFinal.with(Deliver_textActivity.this);
//        rxGalleryFinal.maxSize(defaultMaxCount).multiple()
//                .selected(selected_item)
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(rxBusResultDisposable);
        initViews();


    }

    public void initViews(){
        hidBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText_content=(EditText)findViewById(R.id.EditText02);
        ready_to_publish=new ArrayList();
        group=(RadioGroup)findViewById(R.id.area_btn);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.friend_area:
                        kind="friend";
                        break;
                    case R.id.square_area:
                        kind="square";
                        break;
                }
            }
        });
        send_btn=(Button)findViewById(R.id.release_btn);
        send_btn.setOnClickListener(this);
        setSupportActionBar(toolbar);
        current_select_count=0;
        //返回按钮
        deliver_return=findViewById(R.id.release_return);
        deliver_return.setOnClickListener(v -> finish());
        btn2 = findViewById(R.id.add_pho_btn);
        btn2.setOnClickListener(this);
        gridview=findViewById(R.id.release_gridview);
        listpath = new ArrayList<>();
        /*获取屏幕宽度*/
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();
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
                vh.iv = convertView.findViewById(R.id.square_photo);
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
            case R.id.add_pho_btn://多选
                openMulti();
                break;
            case R.id.release_btn:
                DynamicOperation dynamicOperation=new DynamicOperation();
                String user_text=editText_content.getText().toString();
                ArrayList<String> result=new ArrayList<>();
                for(int i=0;i<selected_item.size();++i){
                    Log.i("send",selected_item.get(i).getOriginalPath());
                    result.add(selected_item.get(i).getOriginalPath());
                }


                dynamicOperation.publishDynamic(user_text, result, new DynamicOperation.DynamicPublishCallback() {
                    @Override
                    public void publishDynamicData(String s) {
                        if(s.equals("1")){
                            //成功
                            Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"发布失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Toast.makeText(getApplicationContext(),"发布中,请稍后",Toast.LENGTH_LONG).show();
                finish();
                break;


        }

    }


    private void openMulti() {
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(Deliver_textActivity.this)
                .image()
                .multiple();
        if (selected_item != null && !selected_item.isEmpty()) {
            rxGalleryFinal
                    .selected(selected_item);
        }

        rxGalleryFinal.maxSize(9)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {

                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {

                        selected_item=imageMultipleResultEvent.getResult();
                        process_select();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }


    private void process_select(){
        listpath.clear();
        for (int i = 0; i < selected_item.size(); i++) {
            scanpath = selected_item.get(i).getThumbnailSmallPath();
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
//                Log.i("result_of_picker", "bitmap=" + results.get(i));
                if (bitmap != null) {
                    listpath.add(bitmap);
                    Log.i("result_of_picker", "listpath=" + listpath);
                }
            }catch (OutOfMemoryError err){
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(scanpath, opts);
                if (bmp != null) {
                    listpath.add(bmp);
//                    Log.i("result_of_picker", "listpath=" + listpath);
                }
            }

        }

        adapter = new Photodaapter(listpath, this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (listpath.size() >= 10) { //第一张为默认图片
                    Toast.makeText(Deliver_textActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();


                } else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //      Toast.LENGTH_SHORT).show();
                }
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selected_item.size(); i++) {
            sb.append(i + 1).append('：').append(selected_item.get(i).getThumbnailSmallPath()).append("\n");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            if (isMultiSelect) {
                //多选
                results = data.getStringArrayListExtra(PhotoPickerActivity.SELECT_RESULTS_ARRAY);
                ready_to_publish.addAll(results);
                current_select_count+=results.size();

                adapter = new Photodaapter(listpath, this);
                gridview.setAdapter(adapter);
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        if (listpath.size() >= 10) { //第一张为默认图片
                            Toast.makeText(Deliver_textActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();


                        } else {
                            dialog(position);
                        }
                    }
                });


                if (results == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < results.size(); i++) {
                    sb.append(i + 1).append('：').append(results.get(i)).append("\n");
                }
            }
        }
    }
        catch(Exception e){
            e.printStackTrace();
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
                selected_item.remove(position);
//                ready_to_publish.remove(position);
//                current_select_count--;
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
