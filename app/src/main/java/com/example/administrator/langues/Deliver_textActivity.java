package com.example.administrator.langues;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.jay.ui.PhotoPickerActivity;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entry.User;
import util.core.DynamicOperation;

public class Deliver_textActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private FloatingActionButton btn2;
    private Button send_btn;
    private ImageButton deliver_return;
    private ArrayList ready_to_publish;//准备发布的图片集合
    private AutoCompleteTextView autoCompleteTextView;
    private boolean isMultiSelect;
    private int defaultMaxCount = 9;
    private int current_select_count;
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
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        ready_to_publish=new ArrayList();
        send_btn=(Button)findViewById(R.id.deliver_btn);
        send_btn.setOnClickListener(this);
        setSupportActionBar(toolbar);
        current_select_count=0;
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
//                EasyPermission.build().requestPermission(Deliver_textActivity.this, Manifest.permission.CALL_PHONE);
                EasyPermission.build()
                        .mRequestCode(2)
                        .mContext(Deliver_textActivity.this)
                        .mPerms(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .mPerms(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .mResult(new EasyPermissionResult() {
                            @Override
                            public void onPermissionsAccess(int requestCode) {
                                super.onPermissionsAccess(requestCode);
                                Log.i("testex","获取成功");
                            }

                            @Override
                            public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                                super.onPermissionsDismiss(requestCode, permissions);
                                Log.i("testex","f");
                            }
                        }).requestPermission();

                isMultiSelect = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean(PhotoPickerActivity.IS_MULTI_SELECT, true);
//
//
                defaultMaxCount = 9;
//
                bundle.putInt(PhotoPickerActivity.MAX_SELECT_SIZE, defaultMaxCount);
                bundle.putInt("current_select_count",current_select_count);
                intent.putExtras(bundle);
                startActivityForResult(intent,1001);
                break;
            case R.id.deliver_btn:
                DynamicOperation dynamicOperation=new DynamicOperation();
                String user_text=autoCompleteTextView.getText().toString();

                dynamicOperation.publishDynamic(user_text, ready_to_publish, new DynamicOperation.DynamicPublishCallback() {
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
//                startActivity(new Intent(getApplicationContext(),My_DeliverActivity.class));
//                getSupportFragmentManager().beginTransaction().replace(R.id.tablayout_frame,new squareFriendFragment()).commit();
                finish();
                break;


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

                for (int i = 0; i < results.size(); i++) {
                    scanpath = results.get(i);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(scanpath);
                        Log.i("result_of_picker", "bitmap=" + results.get(i));
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
                            Log.i("result_of_picker", "listpath=" + listpath);
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
                ready_to_publish.remove(position);
                current_select_count--;
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
