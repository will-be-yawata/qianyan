package com.example.administrator.langues.activity.Square;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.administrator.langues.R;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton add_pho_btn;
    private ImageButton release_return;
    private ImageButton take_pho_btn;

    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;
    private int GRIDVIEW_SIZE;

    //需要的权限数组 读/写/相机
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };

    private static int REQUEST_CAMERA_2 = 101;
    private String mFilePath;


    private List<String> results;
    /**显示图片的GridView*/
    private GridView gridview;
    /**文件夹下所有图片的bitmap*/
    private  List<Bitmap> listpath;
    /**文件夹下图片的真实路径*/
    private String scanpath;
    /**显示图片的适配器*/
    private Releaseadapter adapter;

    private int mScreenHeight;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        init();
        getWindowWH();
        add_pho_btn.setOnClickListener(this);
        take_pho_btn.setOnClickListener(this);
        release_return.setOnClickListener(this);
    }

    private void init() {
        listpath = new ArrayList<>();
        add_pho_btn=findViewById(R.id.add_pho_btn);
        take_pho_btn=findViewById(R.id.take_pho_btn);
        release_return=findViewById(R.id.release_return);

        gridview=findViewById(R.id.release_gridview);
    }
    public void getWindowWH(){//得到屏幕的宽高
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.release_return://返回
                finish();
                break;
            case R.id.add_pho_btn://打开相册
               if(listpath.size() >= 9){//判断是否满9张图片
                   Toast.makeText(ReleaseActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
               }else{
                   ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
               }
                break;
            case R.id.take_pho_btn://打开相机
                //Toast.makeText(getBaseContext(),"dianji了相机",Toast.LENGTH_SHORT).show();
                if(verifyPermissions(ReleaseActivity.this,PERMISSIONS_STORAGE[2]) == 0){
                    ActivityCompat.requestPermissions(ReleaseActivity.this, PERMISSIONS_STORAGE, 3);
                }else{
                    //已经有权限
                    if(listpath.size() >= 9){//判断是否满9张图片
                        Toast.makeText(ReleaseActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                    }else {
                        openCamera();  //打开相机
                    }
                }

                break;

        }
    }
    //获取相机权限
    public int verifyPermissions(Activity activity, java.lang.String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity,permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            return 1;
        }else{

            return 0;
        }
    }
    private void openCamera() {//打开相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //跳转到 ACTION_IMAGE_CAPTURE
        startActivityForResult(intent,REQUEST_CAMERA_2); // 101: 相机的返回码参数（随便一个值就行，只要不冲突就好）

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){//回调函数
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==REQUEST_CODE_SELECT_IMG) {
                showContent(data);//获取图片路径
                return;
            }
               if(requestCode==REQUEST_CAMERA_2) { //相机返回的数据（相机的返回码）
                   Bundle extras = data.getExtras();
                   if (extras != null) {
                       Bitmap bitmap = extras.getParcelable("data");
                       /* 将Bitmap设定到listpath */
                       listpath.add(bitmap);
                   }
                   showView();
               }

            }
    private void showView(){//显示到GridView上
        if(listpath.size()>9) {
            //设置GridView只能显示9张图片
            GRIDVIEW_SIZE=listpath.size();
            GRIDVIEW_SIZE=GRIDVIEW_SIZE-9;
            listpath.remove(listpath.size()-GRIDVIEW_SIZE);
        }
        adapter = new Releaseadapter(listpath, this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialog(position);//删除所选图片
            }
        });

        if (results == null) {
            return;
        }
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (paths.isEmpty()) {
            //mContentTv.setText(R.string.image_selector_select_none);
            Toast.makeText(getBaseContext(),"尚未选择图片",Toast.LENGTH_SHORT).show();
            return;
        }else {
            results = ImageSelector.getImagePaths(data);
            for (int i = 0; i < results.size(); i++) {
                scanpath = results.get(i);
                Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
                //Log.i("bitmap","bitmap="+results.get(i));
                if (bitmap != null) {
                    listpath.add(bitmap);
                }
            }
           showView();

        }
    }

    public void dialog(final int position) {//点击删除图片
        AlertDialog.Builder builder = new AlertDialog.Builder(ReleaseActivity.this);
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

    class VIewHolder {
        ImageView iv;
    }
    public class Releaseadapter extends BaseAdapter {//GridView适配器
        private List<Bitmap> mlist;
        private Context mcontext;
        private LayoutInflater minflater;

        public Releaseadapter(List<Bitmap> list, Context context) {
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

}
